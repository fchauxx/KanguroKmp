import Foundation

public struct RemoteChatbotMessage: Codable {
    public var content: String?
    public var sender: String?
    public var type: String?

    public init(
        content: String?,
        sender: String?,
        type: String?
    ) {
        self.content = content
        self.sender = sender
        self.type = type
    }
}
