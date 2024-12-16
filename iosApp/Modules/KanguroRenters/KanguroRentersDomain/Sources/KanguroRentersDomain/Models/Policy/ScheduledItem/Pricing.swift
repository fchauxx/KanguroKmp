import Foundation

public struct Pricing: Identifiable, Hashable {
    
    public let id = UUID()
    
    public var billingCycle: BillingCycle?
    public var currentPolicyValue: Double?
    public var endorsementPolicyValue: Double?
    public var billingCycleCurrentPolicyValue: Double?
    public var billingCycleEndorsementPolicyValue: Double?
    public var policyPriceDifferenceValue: Double?
    public var billingCyclePolicyPriceDifferenceValue: Double?
    public var scheduledItem: ScheduledItem?
    
    public init(billingCycle: BillingCycle? = nil,
                currentPolicyValue: Double? = nil,
                endorsementPolicyValue: Double? = nil,
                billingCycleCurrentPolicyValue: Double? = nil,
                billingCycleEndorsementPolicyValue: Double? = nil,
                policyPriceDifferenceValue: Double? = nil,
                billingCyclePolicyPriceDifferenceValue: Double? = nil,
                scheduledItem: ScheduledItem? = nil) {
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
