import Foundation

public struct ChatbotChoice: Equatable {
    public var id: String
    public var label: String

    public init(id: String, label: String) {
        self.id = id
        self.label = label
    }
}
