import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain

enum DonationTypeViewState {
    
    case started
    case loading
    case requestFailed
    case requestDonationCausesSucceeded
    case requestDonatedValueSucceeded
}

class DonationTypeViewModel {
    
    // MARK: - Published Properties
    @Published var state: DonationTypeViewState = .started
    
    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var getCharitiesService: GetCharitiesUseCaseProtocol

    // MARK: - Stored Properties
    var donationCauses: [DonationCause]?
    var donatedValue: Int?
    var requestError = ""
    
    // MARK: - Computed Properties
    var donationData: [DonationType] {
        return DonationType.allCases.map { $0 }
    }
}

// MARK: - Analytics
extension DonationTypeViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .DonationType)
    }
}

// MARK: - Network
extension DonationTypeViewModel {
    
    func getDonationCauses() {
        getCharitiesService.execute { [weak self] response in
            guard let self else { return }
            switch response {
            case .success(let data):
                self.donationCauses = data
                self.state = .requestDonationCausesSucceeded
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            }
        }
    }
    
    func getDonatedValue() {
        // TODO: - Get request from endpoint
        state = .requestDonatedValueSucceeded
    }
}
