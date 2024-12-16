import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroPetDomain

class BillingPreferencesViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    
    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    var policy: PetPolicy
    
    // MARK: - Initializers
    init(policy: PetPolicy) {
        self.policy = policy
    }
}

// MARK: - Analytics
extension BillingPreferencesViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .BillingPreferences)
    }
}
