import Foundation

public struct ScheduledItem: Identifiable, Hashable {
    
    public static func == (lhs: ScheduledItem, rhs: ScheduledItem) -> Bool {
        lhs.id == rhs.id
    }
    
    public func hash(into hasher: inout Hasher) {
        hasher.combine(id)
    }
    
    public var id: String?
    public var name: String?
    public var category: ScheduledItemCategory?
    public var images: [ScheduledItemImage]?
    public var valuation: Double?
    public var billingCycle: BillingCycle?
    public var total: Double?
    public var intervalTotal: Double?
    
    public init(id: String? = nil,
                name: String? = nil,
                category: ScheduledItemCategory? = nil,
                images: [ScheduledItemImage]? = nil,
                valuation: Double? = nil,
                billingCycle: BillingCycle? = nil,
                total: Double? = nil,
                intervalTotal: Double? = nil) {
        self.id = id
        self.name = name
        self.category = category
        self.images = images
        self.valuation = valuation
        self.billingCycle = billingCycle
        self.total = total
        self.intervalTotal = intervalTotal
    }
    
    public var hasAtLeastOneImageForType: Bool {
        return ScheduledItemImageType.allCases.allSatisfy { type in
            images?.contains(where: { $0.type == type }) ?? false
        }
    }
}
