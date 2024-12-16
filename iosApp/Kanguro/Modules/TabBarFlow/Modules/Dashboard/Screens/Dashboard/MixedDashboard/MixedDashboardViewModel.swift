import UIKit
import KanguroFeatureFlagDomain
import KanguroRentersDomain
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroSharedData
import KanguroStorageDomain
import KanguroUserDomain
import KanguroPetDomain

enum MixedDashboardViewState {
    
    case started
    case loading
    case requestFailed
    case getRemindersSucceeded
    case partnerRedirectionSucceeded(_ url: String)
    case notificationPermissionFailed
    case notificationPermissionSucceeded
    case blockedUser
    case reloadApp
}

class MixedDashboardViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var userDefaults: Storage
    @LazyInjected var keychain: SecureStorage
    @LazyInjected var languageHandler: LanguageHandlerProtocol
    @LazyInjected var getRemindersService: GetRemindersUseCaseProtocol
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var getLocalUserService: GetLocalUser
    @LazyInjected var isUserBlockedService: GetIsUserAccessOkUseCaseProtocol
    @LazyInjected var updateFirebaseTokenService: UpdateFirebaseTokenUseCaseProtocol
    @LazyInjected var getRemoteUserService: GetRemoteUser
    @LazyInjected var updateLocalUserService: UpdateLocalUser
    @LazyInjected var redirectPartnerService: RedirectPartnerWebpageUseCaseProtocol
    @LazyInjected var askNotificationService: AskForNotificationsUseCaseProtocol
    @LazyInjected var getFeatureFlag: GetFeatureFlagBoolUseCaseProtocol


    // MARK: - Published Properties
    @Published var state: MixedDashboardViewState = .started
    
    // MARK: - Stored Properties
    var remindersFlag: Bool = false
    var petPolicies: [PetPolicy]
    var renterPolicies: [RenterPolicy]
    var reminders: [Reminder] = []
    var requestError = ""

    let concurrentQueue = DispatchQueue(label: "Network",attributes: .concurrent)

    // MARK: - Computed Properties
    var activePolicyType: UserPolicyType {
        if !petPolicies.isEmpty && !renterPolicies.isEmpty {
            return .all
        } else if !petPolicies.isEmpty {
            return .pet
        } else if !renterPolicies.isEmpty {
            return .renters
        } else {
            return .none
        }
    }
    var user: User? {
        guard let user: User = try? getLocalUserService.execute().get() else { return nil }
        return user
    }
    var appLanguage: String {
        guard let language = user?.language else { return "en" }
        return language.rawValue
    }
    var fcmToken: String? {
        guard let token: String = keychain.get(key: KeychainStorageKey.fcmToken.rawValue) else { return nil }
        return token
    }
    var getQuoteUrlRenters: URL? {
        guard let user else { return nil }
        return URL(string: AppURLs.getQuoteLoggedUrl(user: user, quoteType: .renters))
    }
    
    var getQuoteUrlPet: URL? {
        guard let user else { return nil }
        return URL(string: AppURLs.getQuoteLoggedUrl(user: user, quoteType: .pet))
    }
    
    var pets: [Pet] {
        return petPolicies.map { $0.pet }.sorted(by: { $0.id > $1.id })
    }
    var petNames: [String] {
        return pets.compactMap { $0.name }
    }
    var username: String? {
        return user?.givenName
    }
    var blogURL: URL? {
        guard let user: User = try? getLocalUserService.execute().get() else { return nil }
        return URL(string: AppURLs.blogUrl(language: user.language ?? .Spanish)) ?? nil
    }
    var medicalHistoryReminder: [Reminder] {
        let medicalReminders = reminders.filter({ reminder in
            reminder.type == .MedicalHistory
        })
        return medicalReminders
    }
    var isMedicalRemindersPopUpNeeded: Bool {
        return !medicalHistoryReminder.isEmpty && remindersFlag
    }

    var shouldShowLiveVet: Bool {
        guard let shouldShowLiveVet: Bool = try? getFeatureFlag.execute(
            key: KanguroBoolFeatureFlagKeys.shouldShowLiveVet
        ) else { return false }
        
        return shouldShowLiveVet
    }

    // MARK: - Initializers
    init(petPolicies: [PetPolicy], renterPolicies: [RenterPolicy]) {
        self.petPolicies = petPolicies
        self.renterPolicies = renterPolicies
    }
}

// MARK: - Analytics
extension MixedDashboardViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .Dashboard)
    }
}

// MARK: Network
extension MixedDashboardViewModel {
    
    func getReminders() {
        remindersFlag = true
        concurrentQueue.async { [weak self] in
            guard let self else { return }
            self.getRemindersService.execute { [weak self] response in
                guard let self = self else { return }
                switch response {
                case .failure(let error):
                    self.requestError = error.errorMessage ?? "serverError.default".localized
                    self.state = .requestFailed
                case .success(let reminders):
                    self.reminders = reminders
                    self.state = .getRemindersSucceeded
                }
            }
        }
    }
    
    func checkIfUserIsBlocked() {
        guard let user = try? getLocalUserService.execute().get() else {
            assertionFailure("User should not be nil")
            self.state = .requestFailed
            return
        }
        isUserBlockedService.execute(userId: user.id) { [weak self] result in
            guard let self else { return }
            switch result {
            case .success: break
            case .failure(let error):
                if error.errorType == .notAllowed {
                    self.state = .blockedUser
                } else {
                    self.requestError = error.errorMessage ?? "serverError.default".localized
                    self.state = .requestFailed
                }
            }
        }
    }

    func askForNotificationPermissionIfNeeded() {
        askNotificationService.execute { [weak self] response in
            guard let self = self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .notificationPermissionFailed
            case .success:
                self.state = .notificationPermissionSucceeded
            }
        }
    }
    
    func shouldAskForUserReview() -> Bool {
        let savedCount: Int? = userDefaults.get(key: "reviewProcessCompletedCount")
        var count: Int = savedCount ?? 0
        count += 1
        userDefaults.save(value: count, key: "reviewProcessCompletedCount")

        let savedReview: String? = userDefaults.get(key: "lastVersionPromptedForReview")
        let lastVersionPromptedForReview = savedReview ?? ""
        let infoDictionaryKey = kCFBundleVersionKey as String
        guard let currentVersion = Bundle.main.object(forInfoDictionaryKey: infoDictionaryKey) as? String
            else { return false }
        
         if count >= 4 && currentVersion != lastVersionPromptedForReview {
             userDefaults.save(value: currentVersion, key: "lastVersionPromptedForReview")
             return true
         }
        return false
    }
    
    func putNotificationsToken() {
        guard let fcmToken else { return }
        guard let uuid = UIDevice.current.identifierForVendor?.uuidString else { return }
        let parameters = FirebaseTokenParameters(firebaseToken: fcmToken, uuid: uuid)
        updateFirebaseTokenService.execute(parameters) { result in
            switch result {
            case .failure(let error):
                debugPrint(error)
                assertionFailure("Request should not fail")
                break
            default: break
            }
        }
    }
    
    func checkIfUserLanguageChanged() {
        getRemoteUserService.execute { [weak self] response in
            guard let self else { return }
            switch response {
            case .success(let userData):
                guard let userLanguage = userData.language else { return }

                languageHandler.updateLanguageIfNeeded(with: userLanguage) { result in
                    if let _ = try? result.get() {
                        self.state = .reloadApp
                    }
                }
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            }
        }
    }
    
    func redirectToPartnerWebpage(partnerName: String) {
        guard let userId = user?.id else { return }
        let parameters = KanguroSharedDomain.UserIdParameters(id: userId)

        redirectPartnerService.execute(partnerName: partnerName, parameters: parameters) { [weak self] response in
            guard let self else { return }
            switch response {
            case .success(let url):
                self.state = .partnerRedirectionSucceeded(url.redirectTo)
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            }
        }
    }
}
