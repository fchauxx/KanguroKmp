import Foundation

public struct Reimbursment: Equatable {
    public var id: Int
    public var amount: Double

    public init(id: Int, amount: Double) {
        self.id = id
        self.amount = amount
    }
}
