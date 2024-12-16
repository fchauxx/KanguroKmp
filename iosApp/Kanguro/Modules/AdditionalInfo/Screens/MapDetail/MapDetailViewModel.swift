import UIKit
import CoreLocation
import Resolver
import KanguroAnalyticsDomain

class MapDetailViewModel {
    
    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    
    // MARK: - Stored Properties
    //Lower value means more proximity
    let scale: Double = 200
    let coveredDistance: Double = 8000
}

// MARK: - Analytics
extension MapDetailViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .MapDetail)
    }
}
