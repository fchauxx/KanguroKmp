import Foundation

public struct RemoteDeductable: Codable {

    public var id: Int
    public var limit: Double
    public var consumed: Double

    public init(id: Int, limit: Double, consumed: Double) {
        self.id = id
        self.limit = limit
        self.consumed = consumed
    }
}
