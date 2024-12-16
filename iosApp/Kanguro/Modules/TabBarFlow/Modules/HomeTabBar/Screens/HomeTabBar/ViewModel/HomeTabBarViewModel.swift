import UIKit
import KanguroRentersDomain
import Combine
import Resolver
import KanguroSharedDomain
import KanguroSharedData
import KanguroStorageDomain
import KanguroUserDomain
import KanguroPetDomain
import KanguroFeatureFlagDomain

public class HomeTabBarViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var keychain: SecureStorage
    @LazyInjected var getPoliciesService: GetPoliciesUseCaseProtocol
    @LazyInjected var getRentersPoliciesService: GetRenterPoliciesUseCaseProtocol
    @LazyInjected var userDefaults: Storage
    @LazyInjected var languageHandler: LanguageHandlerProtocol
    @LazyInjected var versionService: GetBackendVersionUseCaseProtocol
    @LazyInjected var getPetService: GetPetUseCaseProtocol
    @LazyInjected var getPetsService: GetPetsUseCaseProtocol
    @LazyInjected var getLocalUserService: GetLocalUser
    @LazyInjected var isUserAccessBlocked: GetIsUserAccessOkUseCaseProtocol
    @LazyInjected var updateLocalUserService: UpdateLocalUser
    @LazyInjected var getRemoteUserService: GetRemoteUser
    @LazyInjected var environment: EnvironmentProtocol
    @LazyInjected var shouldShowRenters: GetFeatureFlagBoolUseCaseProtocol
    
    // MARK: - Published Properties
    @Published var viewState: HomeViewState = .started
    @Published var rentersViewState: RentersViewState = .started
    @Published var petsViewState: PetsViewState = .started
    
    // MARK: - Stored Properties
    var menus: [TabBarMenuItem] = []
    var selected: TabBarMenuItem?
    var hasIncompletedAdditionalInfo = false
    var didSetupTabBar: Bool = false
    var requestError: String = ""
    var pets: [Pet] = []
    var incompletedPets: [Pet] = []
    var policies: [Policy] = []
    var petPolicies: [PetPolicy] = []
    var rentersPolicies: [RenterPolicy] = []
    var deepLinkType: DeepLink?
    
    // MARK: - Computed Properties
    var compatibleBackendVersionValue: String {
        return environment.compatibleBackendVersion.getPrefixBeforeDot ?? "0"
    }
    var user: User? {
        guard let user: User = try? getLocalUserService.execute().get() else { return nil }
        return user
    }
    var isPoliciesValid: Bool {
        return !policies.contains { $0.petId == nil }
    }
    var petPoliciesSortedByPetId: [PetPolicy] {
        petPolicies.sorted(by: { $0.pet.id > $1.pet.id })
    }
    var isPetDataFetched: Bool {
        return (petsViewState == .didSetPetPolicies || petsViewState == .petProductNotFound)
    }
    var isRentersDataFetched: Bool {
        return (rentersViewState == .getRenterPoliciesSucceeded || rentersViewState == .renterProductNotFound)
    }
    var isAllDataFetched: Bool {
        isPetDataFetched && isRentersDataFetched
    }

    let concurrentQueue = DispatchQueue(label: "Network", attributes: .concurrent)
}

// MARK: - TabBar Methods
extension HomeTabBarViewModel {
    
    func tapMenu(menu: TabBarMenuItem?) {
        
        if menu == selected {
            selected?.popToRoot()
        } else {
            selected?.deactive()
            selected = menu
            selected?.activate()
            
            guard let isLoaded = menu?.isLoaded else { return }
            if !isLoaded {
                menu?.coordinator.start()
                menu?.isLoaded = true
            }
        }
    }
    
    func addTabBarMenuItem(_ tabBarMenuItem: TabBarMenuItem) {
        menus.append(tabBarMenuItem)
    }
    
    func deactiveItems() {
        menus.forEach { $0.deactive() }
    }
}

// MARK: - Public Methods
extension HomeTabBarViewModel {
    
    func addPetPolicy(pet: Pet, policy: Policy) {
        
        let petPolicy = PetPolicy(pet: pet)
        
        petPolicy.id = policy.id
        petPolicy.startDate = policy.startDate
        petPolicy.waitingPeriod = policy.waitingPeriod
        petPolicy.waitingPeriodRemainingDays = policy.waitingPeriodRemainingDays
        petPolicy.endDate = policy.endDate
        petPolicy.reimbursment = policy.reimbursment
        petPolicy.sumInsured = policy.sumInsured
        petPolicy.status = policy.status
        petPolicy.feedbackRate = policy.feedbackRate
        petPolicy.deductable = policy.deductable
        petPolicy.payment = policy.payment
        petPolicy.preventive = policy.preventive
        petPolicy.policyExternalId = policy.policyExternalId
        petPolicy.policyOfferId = policy.policyOfferId
        petPolicy.isFuture = policy.isFuture
        petPolicies.append(petPolicy)
        
        if petPolicies.count == policies.count {
            petsViewState = .didSetPetPolicies
        }
    }
    
    func setupPetPolicies(policies: [Policy]) {
        self.policies = policies
        isPoliciesValid ? createPetPolicies() : (petsViewState = .didSetPetPolicies)
    }

    func createPetPolicies() {
        policies.forEach {
            getPetPolicies(policy: $0)
        }
    }
    func setupPets(pets: [Pet]) {
        self.pets = pets
        for pet in pets {
            guard let hasAdditionalInfo = pet.hasAdditionalInfo else { return }
            if !hasAdditionalInfo {
                incompletedPets.append(pet)
                hasIncompletedAdditionalInfo = true
            }
        }
        if hasIncompletedAdditionalInfo {
            deepLinkType = nil
            petsViewState = .goToPetAdditionalInfoFlow
        }
    }
    
    func handleBlockedUserResponse(isBlocked: Bool) {
        if isBlocked {
            viewState = .blockedUser
        } else if viewState == .loading {
            viewState = .allowedUser
        }
    }
}

extension HomeTabBarViewModel {

    func setLanguage() {
        let userLanguage = user?.language
        userDefaults.save(value: userLanguage?.rawValue ?? Language.English.rawValue, key: "preferredLanguage")
    }

    func startDataRequests() {
        fetchPetsData()
        fetchRentersData()
    }

    func fetchPetsData(isPetPictureUpdated: Bool = false) {
        if isPetPictureUpdated {
            petsViewState = .loadingPetPicture
        }

        concurrentQueue.async { [weak self] in
            guard let self else { return }
            self.getPets()
        }

        concurrentQueue.async { [weak self] in
            guard let self else { return }
            self.getAllPetPolicies()
        }
    }

    func fetchRentersData() {
        concurrentQueue.async { [weak self] in
            guard let self else { return }
            self.getRentersPolicies()
        }
    }

    func checkIfUserExists() {
        viewState = (user != nil) ? .goToHome : .userNotFound
    }

    func showNoPoliciesInAccountError() {
        requestError = "No policies found at your account."
        viewState = .noPoliciesFound
    }
}

// MARK: - Network
extension HomeTabBarViewModel {

    func getBackendVersion() {
        versionService.execute(maxVersion: compatibleBackendVersionValue) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                if error == .invalidVersion {
                    self.viewState = .invalidVersion
                } else {
                    self.requestError = "serverError.default".localized
                    self.viewState = .requestFailed
                }
            case .success:
                self.viewState = .validVersion
            }
        }
    }

    func getPetPolicies(policy: Policy) {
        guard let petId = policy.petId else { return }
        let parameters = GetPetParameters(id: petId)
        self.petPolicies.removeAll()
        self.getPetService.execute(parameters: parameters) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.viewState = .requestFailed
            case .success(let pet):
                self.addPetPolicy(pet: pet, policy: policy)
            }
        }
    }

    func getPets(showLoading: Bool = false) {
        if showLoading {
            self.petsViewState = .loadingPetPicture
        }
        getPetsService.execute { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.viewState = .requestFailed
            case .success(let pets):
                !pets.isEmpty ? self.setupPets(pets: pets) : (self.petsViewState = .petProductNotFound)
            }
        }
    }

    func getAllPetPolicies() {
        getPoliciesService.execute() { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.viewState = .requestFailed
            case .success(let policies):
                !policies.isEmpty ? self.setupPetPolicies(policies: policies) : (self.petsViewState = .petProductNotFound)
            }
        }
    }

    func getRentersPolicies() {
        getRentersPoliciesService.execute() { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.viewState = .requestFailed
            case .success(let policies):
                self.rentersPolicies = policies
                if !policies.isEmpty {
                    self.rentersViewState = .getRenterPoliciesSucceeded
                } else {
                    self.rentersViewState = .renterProductNotFound
                }
            }
        }
    }

    func checkUserLanguageAndStatus(showLoading: Bool = false) {
        if showLoading {
            viewState = .loading
        }
        getRemoteUserService.execute { [weak self] response in
            guard let self else { return }
            switch response {
            case .success(let userData):
                guard let userLanguage = userData.language,
                      let blockedStatus = userData.hasAccessBlocked else { return }

                languageHandler.updateLanguageIfNeeded(with: userLanguage) { result in
                    if let _ = try? result.get() {
                        #if DEBUG
                        print("Language updated according to user preferences")
                        #endif
                    }
                }
                self.setLanguage()
                self.handleBlockedUserResponse(isBlocked: blockedStatus)
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.viewState = .requestFailed
            }
        }
    }
}

