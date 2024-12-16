import UIKit
import KanguroAnalyticsDomain
import KanguroPetDomain
import Combine
import Resolver

class AlmostDoneViewModel {

    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol

    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started

    // MARK: - Stored Properties
    var dtpPetClaim: PetClaim

    init(dtpPetClaim: PetClaim) {
        self.dtpPetClaim = dtpPetClaim
    }
}

// MARK: - Analytics
extension AlmostDoneViewModel {

    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .DTPAlmostDone)
    }
}
