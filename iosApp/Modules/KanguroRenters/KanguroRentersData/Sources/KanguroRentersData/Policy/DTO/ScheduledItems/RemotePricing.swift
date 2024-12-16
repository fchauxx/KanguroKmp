import Foundation

public struct RemotePricing: Codable, Equatable {
    
    public static func == (lhs: RemotePricing, rhs: RemotePricing) -> Bool {
        lhs.id == rhs.id
    }
    
    public var id: UUID {
        return UUID()
    }
    
    public var billingCycle: RemoteBillingCycle?
    public var currentPolicyValue: Double?
    public var endorsementPolicyValue: Double?
    public var billingCycleCurrentPolicyValue: Double?
    public var billingCycleEndorsementPolicyValue: Double?
    public var policyPriceDifferenceValue: Double?
    public var billingCyclePolicyPriceDifferenceValue: Double?
    public var scheduledItem: RemoteScheduledItem?
    
    public init(billingCycle: RemoteBillingCycle? = nil,
                currentPolicyValue: Double? = nil,
                endorsementPolicyValue: Double? = nil,
                billingCycleCurrentPolicyValue: Double? = nil,
                billingCycleEndorsementPolicyValue: Double? = nil,
                policyPriceDifferenceValue: Double? = nil,
                billingCyclePolicyPriceDifferenceValue: Double? = nil,
                scheduledItem: RemoteScheduledItem? = nil) {
        self.billingCycle = billingCycle
        self.currentPolicyValue = currentPolicyValue
        self.endorsementPolicyValue = endorsementPolicyValue
        self.billingCycleCurrentPolicyValue = billingCycleCurrentPolicyValue
        self.billingCycleEndorsementPolicyValue = billingCycleEndorsementPolicyValue
        self.policyPriceDifferenceValue = policyPriceDifferenceValue
        self.billingCyclePolicyPriceDifferenceValue = billingCyclePolicyPriceDifferenceValue
        self.scheduledItem = scheduledItem
    }
}
