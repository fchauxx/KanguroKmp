import Foundation

public enum RemoteScheduledItemImageType: String, Identifiable, Codable {
    
    public var id: UUID {
        return UUID()
    }
    
    case ReceiptOrAppraisal
    case Item
    case ItemWithReceiptOrAppraisal
}
