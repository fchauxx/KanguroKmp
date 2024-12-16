import Foundation

public struct RemoteScheduledItemDefinitiveImageParameters: Codable {

    public var fileId: Int?
    public var type: RemoteScheduledItemImageType?

    public init(fileId: Int? = nil,
                type: RemoteScheduledItemImageType? = nil) {
        self.fileId = fileId
        self.type = type
    }
}
