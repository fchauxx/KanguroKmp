import Foundation
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroSharedData
import KanguroPetDomain

enum PreventiveCoveredViewState {
    
    case started
    case loading
    case requestFailed
    case getPetFailed
    case getPolicySucceeded
    case getPetSucceeded
    case getCoverageSucceeded
    case fallbackCoverageRequest
}

enum PreventiveCoveredType {
    
    case regular
    case editable
}

class PreventiveCoveredViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var getPolicyService: GetPolicyUseCaseProtocol
    @LazyInjected var getCoveragesService: GetCoveragesUseCaseProtocol
    @LazyInjected var getPetService: GetPetUseCaseProtocol
    
    // MARK: - Published Properties
    @Published var state: PreventiveCoveredViewState = .started
    
    // MARK: - Stored Properties
    var policyId: String
    var type: PreventiveCoveredType
    var pet: Pet?
    var policy: KanguroSharedDomain.Policy?
    var coverageDataList: [KanguroSharedDomain.CoverageData]?
    var requestError = ""
    var coverageFallbackRequestUsed: Bool = false
    
    // MARK: - Computed Properties
    var reimbursement: Double? {
        guard let amount = policy?.reimbursment?.amount else { return nil }
        return amount/100
    }
    var offerId: Int? {
        guard let id = policy?.policyOfferId else { return nil }
        return id
    }
    var isEditable: Bool {
        return type == .editable
    }
    var isCoverageRequestFallbackNeeded: Bool {
        return (offerId != nil) && !coverageFallbackRequestUsed
    }
    
    // MARK: - Initializers
    init(policyId: String, type: PreventiveCoveredType = .regular) {
        self.policyId = policyId
        self.type = type
    }
}

// MARK: - Analytics
extension PreventiveCoveredViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .PreventiveWhatsCovered)
    }
}

// MARK: - Public Methods
extension PreventiveCoveredViewModel {
    
    func handleEditableCase() {
        isEditable ? getPet() : getCoverages()
    }
}

// MARK: - Private Methods
extension PreventiveCoveredViewModel {
    
    private func handleCoverageList(_ coverages: [KanguroSharedDomain.CoverageData]) -> [KanguroSharedDomain.CoverageData] {
        let sortedCoverages = coverages.sorted {
            !$0.isCompleted && $1.isCompleted
        }
        return sortedCoverages.filter({ coverage in
            return !(coverage.varName?.contains("num_") == true)
        })
    }
}

// MARK: - Network
extension PreventiveCoveredViewModel {
    
    func getPetPolicy() {
        state = .loading
        let parameters = KanguroSharedDomain.PolicyParameters(id: policyId)
        getPolicyService.execute(parameters) { [weak self] response in
            guard let self = self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let policy):
                self.policy = policy
                self.state = .getPolicySucceeded
            }
        }
    }
    
    func getPet() {
        state = .loading
        guard let petId = policy?.petId else { return }
        let parameters = GetPetParameters(id: petId)
        getPetService.execute(parameters: parameters) { [weak self] response in
            guard let self = self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .getPetFailed
            case .success(let pet):
                self.pet = pet
                self.state = .getPetSucceeded
            }
        }
    }
    
    func getCoverages() {
        state = .loading
        guard let reimbursement = reimbursement else { return }
        let parameters = KanguroSharedDomain.PolicyCoverageParameters(id: policyId, offerId: coverageFallbackRequestUsed ? nil : offerId, reimbursement: reimbursement)
        getCoveragesService.execute(parameters) { [weak self] response in
            guard let self = self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = self.isCoverageRequestFallbackNeeded ? .fallbackCoverageRequest : .requestFailed
            case .success(let coverages):
                self.coverageDataList = self.handleCoverageList(coverages)
                if self.coverageFallbackRequestUsed {
                    self.coverageFallbackRequestUsed = false
                }
                self.state = .getCoverageSucceeded
            }
        }
    }
}
