import Foundation
import Combine
import KanguroDesignSystem
import KanguroSharedDomain
import KanguroChatbotDomain
import KanguroRentersDomain

public class OnboardingChatViewModel: ObservableObject, ChatbotLifeCycleDelegate {
    
    var router: RentersRouter<RentersPage>
    
    var didFinishAction: SimpleClosure
    
    public init(_ router: RentersRouter<RentersPage>, didFinishAction: @escaping SimpleClosure) {
        self.router = router
        self.didFinishAction = didFinishAction
    }
    
    public func didFinished() {
        didFinishAction()
    }

    public func didAskExternalFlow(_ flow: KanguroChatbotDomain.ChatbotExternalFlow) {
        switch flow {
        case .scheduledItem(let policyId):
            router.push(RentersPage.scheduleItemSummaryView(policy: RenterPolicy(id: policyId)))
        case .uploadFile:
            router.present(page: RentersPage.uploadFileVideo)
        }
    }
}
