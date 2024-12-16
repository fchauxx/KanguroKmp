import Foundation

public struct RemoteScheduledItem: Codable, Equatable {
    
    public static func == (lhs: RemoteScheduledItem, rhs: RemoteScheduledItem) -> Bool {
        lhs.id == rhs.id
    }
    
    public var id: String?
    public var name: String?
    public var type: RemoteScheduledItemCategoryType?
    public var valuation: Double?
    public var billingCycle: RemoteBillingCycle?
    public var total: Double?
    public var intervalTotal: Double?
    public var images: [RemoteScheduledItemImage]?
    
    public init(id: String? = nil,
                name: String? = nil,
                type: RemoteScheduledItemCategoryType? = nil,
                valuation: Double? = nil,
                billingCycle: RemoteBillingCycle? = nil,
                total: Double? = nil,
                intervalTotal: Double? = nil,
                images: [RemoteScheduledItemImage]? = nil) {
        self.id = id
        self.name = name
        self.type = type
        self.valuation = valuation
        self.billingCycle = billingCycle
        self.total = total
        self.intervalTotal = intervalTotal
        self.images = images
    }
}
