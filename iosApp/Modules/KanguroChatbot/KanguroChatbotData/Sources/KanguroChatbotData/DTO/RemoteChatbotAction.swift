import Foundation

public struct RemoteChatbotAction: Codable {
    public var type: String?
    public var options: [RemoteChatbotOption]?
    public var dateRange: RemoteChatbotDateRange?
    public var policyId: String?

    public init(
        type: String?,
        options: [RemoteChatbotOption]? = nil,
        dateRange: RemoteChatbotDateRange? = nil,
        policyId: String? = nil
    ) {
        self.type = type
        self.options = options
        self.dateRange = dateRange
        self.policyId = policyId
    }
}
