import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain

enum DefaultActionType: Int {
    
    case fileClaim = 1
    case vetAdvice = 2
    case faq = 3
    case support = 4
    case petParent = 5
}

class CentralChabotViewModel: ChatbotViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    
    // MARK: - Stored Properties
    var actionType: DefaultActionType? = .none
    
    // MARK: - Computed Properties
    private var defaultMessages: [ChatMessage] {
        return [ChatMessage(format: .Text,
                            content: "centralChatbot.botMsg1.label".localized,
                            order: 0,
                            sender: .Bot),
                ChatMessage(format: .Text,
                            content: "centralChatbot.botMsg2.label".localized,
                            order: 1,
                            sender: .Bot)]
    }
    var defaultActions: [ChatAction] {
        return [ChatAction(order: 1,
                           label: "centralChatbot.fileClaim.label".localized,
                           value: "centralChatbot.fileClaim.label".localized,
                           action: .LocalAction),
                ChatAction(order: 2,
                           label: "centralChatbot.vetAdvice.label".localized,
                           value: "centralChatbot.vetAdvice.label".localized,
                           action: .LocalAction),
                ChatAction(order: 3,
                           label: "centralChatbot.faq.label".localized,
                           value: "centralChatbot.faq.label".localized,
                           action: .LocalAction),
                ChatAction(order: 4,
                           label: "centralChatbot.support.label".localized,
                           value: "centralChatbot.support.label".localized,
                           action: .LocalAction),
                ChatAction(order: 5,
                           label: "centralChatbot.petParent.label".localized,
                           value: "centralChatbot.petParent.label".localized,
                           action: .LocalAction)]
    }
    var defaultStep: ChatInteractionStep {
        
        return ChatInteractionStep(type: .ButtonList,
                                   orientation: .Vertical,
                                   actions: defaultActions,
                                   messages: defaultMessages,
                                   sessionId: "")
    }
}

// MARK: - Analytics
extension CentralChabotViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: data.type == .NewClaim ? .FileClaimChatbot : .MedicalHistoryChatbot)
    }
}

// MARK: - Public Methods
extension CentralChabotViewModel {
    
    func callSupport() {
        let supportPhoneNumber = "forgotPassword.phone".localized
        guard let url = URL(string: "tel://\(supportPhoneNumber)"),
              UIApplication.shared.canOpenURL(url) else { return }
        UIApplication.shared.open(url)
    }
    
    func isValidIndex(_ index: Int) -> Bool {
        let orders = defaultActions.map { $0.order }
        return orders.contains(index)
    }
}
