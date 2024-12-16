import UIKit
import Resolver
import KanguroAnalyticsDomain
import KanguroAnalyticsData

class AnalyticsModuleDependencies {
    
    // MARK: - Stored Properties
    var analytics: KanguroAnalyticsModuleProtocol?
    
    // MARK: - Initializers
    init(analytics: KanguroAnalyticsModuleProtocol? = nil) {
        self.analytics = analytics
    }
}

// MARK: - ModuleDependencies
extension AnalyticsModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        let analytics = self.analytics ?? FirebaseAnalyticsModule()
        Resolver.register { analytics }
    }
}
