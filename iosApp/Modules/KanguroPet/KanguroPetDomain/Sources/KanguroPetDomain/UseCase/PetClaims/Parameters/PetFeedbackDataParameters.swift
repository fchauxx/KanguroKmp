import Foundation

public struct PetFeedbackDataParameters {
    
    public var feedbackRate: Int?
    public var feedbackDescription: String?

    public init(
        feedbackRate: Int? = nil,
        feedbackDescription: String? = nil
    ) {
        self.feedbackRate = feedbackRate
        self.feedbackDescription = feedbackDescription
    }
}
