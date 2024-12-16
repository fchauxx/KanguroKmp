import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroUserDomain
import KanguroPetDomain

enum CoverageViewState {
    case started
    case blockedUser
}

class CoverageViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var getLocalUserService: GetLocalUser
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var getHasAccessBlockedService: GetIsUserAccessOkUseCaseProtocol
    
    // MARK: - Published Properties
    @Published var state: CoverageViewState = .started
    
    // MARK: - Stored Properties
    var policies: [PetPolicy] = []
    
    // MARK: Computed Properties
    var pets: [Pet] {
        return policies.map { $0.pet }
    }
    var user: User? {
        guard let user: User = try? getLocalUserService.execute().get() else { return nil }
        return user
    }
    var getQuoteUrl: URL? {
        guard let user else { return nil }
        return URL(string: AppURLs.getQuoteLoggedUrl(user: user)) ?? nil
    }
    
    // MARK: - Initializers
    init(policies: [PetPolicy]) {
        self.policies = policies
    }
}

// MARK: - Analytics
extension CoverageViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .Coverages)
    }
}

// MARK: - Network
extension CoverageViewModel {
    func checkIfUserIsBlocked() {
        guard let user = try? getLocalUserService.execute().get() else {
            assertionFailure("User should not be nil")
            return
        }
        getHasAccessBlockedService.execute(userId: user.id) { [weak self] result in
            guard let self else { return }
            switch result {
            case .success: break
            case .failure(let error):
                if error.errorType == .notAllowed {
                    self.state = .blockedUser
                }
            }
        }
    }
}
