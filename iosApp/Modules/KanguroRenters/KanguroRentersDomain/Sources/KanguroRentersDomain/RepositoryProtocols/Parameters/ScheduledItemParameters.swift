import Foundation

public struct ScheduledItemParameters {
    
    public var name: String?
    public var type: ScheduledItemCategory?
    public var valuation: Double?
    public var billingCycle: BillingCycle?
    
    public init(name: String? = nil,
                type: ScheduledItemCategory? = nil,
                valuation: Double? = nil,
                billingCycle: BillingCycle? = nil) {
        self.name = name
        self.type = type
        self.valuation = valuation
        self.billingCycle = billingCycle
    }
}
