import Foundation

public struct RemoteScheduledItemParameters: Codable {
    
    public var name: String?
    public var type: RemoteScheduledItemCategoryType?
    public var valuation: Double?
    public var billingCycle: RemoteBillingCycle?

    public init(name: String? = nil,
                type: RemoteScheduledItemCategoryType? = nil,
                valuation: Double? = nil,
                billingCycle: RemoteBillingCycle? = nil) {
        self.name = name
        self.type = type
        self.valuation = valuation
        self.billingCycle = billingCycle
    }
}
