import Foundation

public struct ChatbotDateConstraints: Equatable {
    public var minDate: Date?
    public var maxDate: Date?

    public init(minDate: Date? = nil, maxDate: Date? = nil) {
        self.minDate = minDate
        self.maxDate = maxDate
    }
}
