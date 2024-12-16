import Foundation

public struct RemoteChatbotOption: Codable {
    public var label: String?
    public var id: String?
    public var styles: [String]?

    public init(
        label: String? = nil,
        id: String? = nil,
        styles: [String]? = nil
    ) {
        self.label = label
        self.id = id
        self.styles = styles
    }
}
