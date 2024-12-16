import Foundation

public struct ChatbotJourneyParameters {

    public var journey: ChatbotJourney
    public var data: [String: Any?]

    public init(journey: ChatbotJourney, data: [String: Any?]) {
        self.journey = journey
        self.data = data
    }
}
