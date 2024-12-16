import Foundation

public struct CloudDocument: Equatable {

    public let userId: String?
    public let pets: [PetCloud]?
    public let renters: [RentersCloud]?

    public init(userId: String?, pets: [PetCloud]?, renters: [RentersCloud]?) {
        self.userId = userId
        self.pets = pets
        self.renters = renters
    }
}
