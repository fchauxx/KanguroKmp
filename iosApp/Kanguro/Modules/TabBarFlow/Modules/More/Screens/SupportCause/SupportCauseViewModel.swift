import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroStorageDomain
import KanguroUserDomain
import KanguroSharedDomain

enum SupportCauseViewState {
    
    case started
    case loading
    case requestFailed
    case requestActualDonationSucceeded
    case requestDonatedValueSucceeded
}

class SupportCauseViewModel {
    
    // MARK: - Published Properties
    @Published var state: SupportCauseViewState = .started
    
    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var keychain: SecureStorage
    @LazyInjected var getLocalUserService: GetLocalUser
    @LazyInjected var getCharitiesService: GetCharitiesUseCaseProtocol

    // MARK: - Stored Properties
    var actualUserDonation: DonationCause?
    var donatedValue: Int?
    var requestError = ""
    
    // MARK: - Computed Properties
    var user: User? {
        guard let user: User = try? getLocalUserService.execute().get() else { return nil }
        return user
    }
}

// MARK: - Analytics
extension SupportCauseViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .SupportCause)
    }
}

// MARK: - Network
extension SupportCauseViewModel {
    
    func getDonatedValue() {
        // TODO: - Get request from endpoint
        state = .requestDonatedValueSucceeded
    }
    
    func getActualCharity() {
        getCharitiesService.execute { [weak self] response in
            guard let self else { return }
            switch response {
            case .success(let data):
                self.actualUserDonation = data.first(where: { $0.attributes.charityKey == self.user?.donation?.charityId })
                self.state = .requestActualDonationSucceeded
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            }
        }
    }
}
