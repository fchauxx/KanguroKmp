import Foundation
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroPetDomain

class CoverageOptionsViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var getCoveragesService: GetCoveragesUseCaseProtocol
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    
    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    var policy: PetPolicy?
    var coverageDataList: [KanguroSharedDomain.CoverageData]?
    var requestError = ""
    
    // MARK: - Computed Properties
    var reimbursement: Double? {
        guard let policy = policy,
              let amount = policy.reimbursment?.amount else { return nil }
        return amount/100
    }
    var petName: String? {
        guard let petName = policy?.pet.name else { return nil }
        return petName
    }
}

// MARK: - Analytics
extension CoverageOptionsViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .CoverageWhatsCovered)
    }
}

extension CoverageOptionsViewModel {
    
    //will be integrated ASAP
    func getPolicy() {
        //success -> dataChanged
    }
    
    func getCoverages() {
        state = .loading
        guard let policy = policy,
              let policyId = policy.id,
              let offerId = policy.policyOfferId,
              let reimbursement = reimbursement else { return }
        let parameters = KanguroSharedDomain.PolicyCoverageParameters(id: policyId, offerId: offerId, reimbursement: reimbursement)
        getCoveragesService.execute(parameters) { [weak self] response in
            guard let self = self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let coverages):
                self.coverageDataList = coverages
                self.state = .requestSucceeded
            }
        }
    }
}
