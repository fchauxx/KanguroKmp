import Foundation

public struct RemoteChatbotJourneyParameters: Codable {
    
    public var journey: String
    public var data: [String: AnyCodable?]

    public init(journey: String, data: [String: AnyCodable?] = [:]) {
        self.journey = journey
        self.data = data
    }
}
