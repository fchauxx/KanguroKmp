import Foundation

public enum ScheduledItemImageType: String, CaseIterable {
    
    case ReceiptOrAppraisal
    case Item
    case ItemWithReceiptOrAppraisal
}

public struct ScheduledItemImage: Identifiable {
    
    public var id: Int?
    public var fileName: String?
    public var type: ScheduledItemImageType?
    public var url: String?

    public init(id: Int? = nil,
                fileName: String? = nil,
                type: ScheduledItemImageType? = nil,
                url: String? = nil) {
        self.id = id
        self.fileName = fileName
        self.type = type
        self.url = url
    }
}
