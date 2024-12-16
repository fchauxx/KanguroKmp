import UIKit
@testable import Kanguro

struct MockedChatbotViewModelData {
    
    // MARK: - Computed Properties
    var messages: [ChatMessage] {
        return [ChatMessage(format: .Text,
                            content: "content",
                            order: 2),
                ChatMessage(format: .Text,
                            content: "content",
                            order: 2),
                ChatMessage(format: .Text,
                            content: "content",
                            order: 2,
                            sender: nil)]
    }
    var actions: [ChatAction] {
        return [ChatAction(order: 1,
                           label: "label",
                           value: "value")]
    }
    var step: ChatInteractionStep {
        return ChatInteractionStep(type: .ButtonList,
                                   orientation: .Horizontal,
                                   actions: actions,
                                   messages: messages,
                                   sessionId: "123")
    }
    var data: ChatbotData {
        return ChatbotData(type: .NewClaim,
                           currentPetId: 3,
                           sessionId: "456",
                           chatInteractionStep: step)
    }
}
