import Foundation

public struct UserInputParameters {

    public var input: Any
    public var chatbotSession: String

    public init(input: Any = "", chatbotSession: String) {
        self.input = input
        self.chatbotSession = chatbotSession
    }
}
