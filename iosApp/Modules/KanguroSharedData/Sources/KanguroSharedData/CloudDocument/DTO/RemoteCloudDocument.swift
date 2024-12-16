import Foundation

public struct RemoteCloudDocument {
    public let userId: String?
    public let pets: [RemotePetCloud]?
    public let renters: [RemoteRentersCloud]?

    public init(userId: String?, pets: [RemotePetCloud]?, renters: [RemoteRentersCloud]?) {
        self.userId = userId
        self.pets = pets
        self.renters = renters
    }
}
