import Foundation
import Combine
import KanguroDesignSystem
import KanguroSharedDomain
import KanguroChatbotDomain
import KanguroRentersDomain

public class ClaimsChatViewModel: ObservableObject, ChatbotLifeCycleDelegate {
    
    var router: RentersRouter<RentersPage>
    
    public init(_ router: RentersRouter<RentersPage>) {
        self.router = router
    }
    
    // TODO: - Make sure that the scheduled items summary view recieve the correct Policy with policy id
    public func didFinished() {
        router.push(RentersPage.scheduleItemSummaryView(policy: RenterPolicy(id: "abc")))
    }
    
    public func didAskExternalFlow(_ flow: KanguroChatbotDomain.ChatbotExternalFlow) {
        switch flow {
        case .scheduledItem(let policyId):
            router.present(page: RentersPage.scheduleItemSummaryView(policy: RenterPolicy(id: "abc")))
        case .uploadFile:
            router.present(page: .uploadFileVideo)
        }
    }
}
