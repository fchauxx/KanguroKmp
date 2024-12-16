import Foundation
import KanguroChatbotDomain
import KanguroChatbotData
import KanguroDesignSystem
import KanguroNetworkDomain
import KanguroChatbotPresentation
import Resolver

public struct SwiftUIChatbotFactory {
    
    static public var chatbotViewModel: KanguroChatbotPresentation.ChatbotViewModel?
    
    public static func make(delegate: ChatbotLifeCycleDelegate?,
                            network: NetworkProtocol,
                            journey: ChatbotJourney,
                            policyId: String) -> KanguroChatbotPresentation.ChatbotView {
        
        let chatbotViewModel: KanguroChatbotPresentation.ChatbotViewModel = KanguroChatbotPresentation.ChatbotViewModel(
            delegate: delegate,
            createSessionService: CreateChatbotSession(chatbotRepo: RemoteChatbotRepository(network: network)),
            getFirstChatbotStepService: GetFirstChatbotStep(chatbotRepo: RemoteChatbotRepository(network: network)),
            getNextChatbotStepService: GetNextChatbotStep(chatbotRepo: RemoteChatbotRepository(network: network)),
            chatbotJourney: journey,
            policyId: policyId,
            botMessageQueue: SimpleMessageQueue(shouldAddIsTyping: true),
            userMessageQueue: SimpleMessageQueue()
        )
        
        SwiftUIChatbotFactory.chatbotViewModel = chatbotViewModel
        return KanguroChatbotPresentation.ChatbotView(viewModel: chatbotViewModel,
                                                      choosenDate: DateViewData(selected: Date()))
    }
}
