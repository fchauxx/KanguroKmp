import Foundation

public struct ChatMessage: Identifiable, Hashable {
    
    public var id: UUID = UUID()
    public var message: String
    public var sender: ChatMessageSender
    public var isFirstMessage: Bool

    public init(message: String, sender: ChatMessageSender, isFirstMessage: Bool = false) {
        self.message = message
        self.sender = sender
        self.isFirstMessage = isFirstMessage
    }
}
