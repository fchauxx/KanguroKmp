import Foundation

public struct RemoteScheduledItemImage: Codable {
    
    public var id: Int?
    public var fileName: String?
    public var type: RemoteScheduledItemImageType?
    
    public init(id: Int? = nil,
                fileName: String? = nil,
                type: RemoteScheduledItemImageType? = nil) {
        self.id = id
        self.fileName = fileName
        self.type = type
    }
}
