import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroPetDomain

class PaymentSettingsViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    
    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    var policies: [PetPolicy]
    
    // MARK: - Initializers
    init(policies: [PetPolicy]) {
        self.policies = policies
    }
}

// MARK: - Analytics
extension PaymentSettingsViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .PaymentSettings)
    }
}
