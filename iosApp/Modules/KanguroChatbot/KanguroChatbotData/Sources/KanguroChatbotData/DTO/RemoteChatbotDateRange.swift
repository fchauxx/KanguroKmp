import Foundation

public struct RemoteChatbotDateRange: Codable {
    public var startDate: String?
    public var endDate: String?

    public init(startDate: String? = nil, endDate: String? = nil) {
        self.startDate = startDate
        self.endDate = endDate
    }
}
