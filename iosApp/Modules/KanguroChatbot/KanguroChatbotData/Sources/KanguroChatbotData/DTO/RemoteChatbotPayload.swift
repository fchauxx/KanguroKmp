import Foundation

public struct RemoteChatbotPayload: Codable {
    public var messages: [RemoteChatbotMessage]?
    public var action: RemoteChatbotAction?
    public var id: String?
    
    public init(
        messages: [RemoteChatbotMessage]? = nil,
        action: RemoteChatbotAction? = nil,
        id: String? = nil
    ) {
        self.messages = messages
        self.action = action
        self.id = id
    }
}
