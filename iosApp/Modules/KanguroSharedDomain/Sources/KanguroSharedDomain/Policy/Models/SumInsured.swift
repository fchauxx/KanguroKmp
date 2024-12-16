import Foundation

public struct SumInsured: Equatable {
    public var id: Int
    public var limit: Double
    public var consumed: Double
    public var remainingValue: Double

    public init(
        id: Int,
        limit: Double,
        consumed: Double,
        remainingValue: Double
    ) {
        self.id = id
        self.limit = limit
        self.consumed = consumed
        self.remainingValue = remainingValue
    }
}
