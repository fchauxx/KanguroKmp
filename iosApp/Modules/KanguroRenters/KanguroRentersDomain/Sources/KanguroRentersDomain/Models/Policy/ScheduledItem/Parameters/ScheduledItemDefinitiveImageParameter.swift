import Foundation

public struct ScheduledItemDefinitiveImageParameter {

    public var fileId: Int?
    public var type: ScheduledItemImageType?

    public init(fileId: Int? = nil, type: ScheduledItemImageType? = nil) {
        self.fileId = fileId
        self.type = type
    }
}
