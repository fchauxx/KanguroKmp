import Foundation
import KanguroSharedDomain
import KanguroRentersDomain
import KanguroNetworkDomain

public struct RentersPricingMapper: ModelMapper {
    public typealias T = Pricing

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemotePricing = input as? RemotePricing else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        
        var billingCycle: BillingCycle? = nil
        var scheduledItem: ScheduledItem? = nil
        
        if let remoteBillingCycle = input.billingCycle {
            billingCycle = BillingCycle(rawValue: remoteBillingCycle.rawValue)
        }
        
        if let remoteScheduledItem = input.scheduledItem {
            scheduledItem = try RentersScheduledItemMapper.map(remoteScheduledItem)
        }
        
        let pricing = Pricing(billingCycle: billingCycle,
                              currentPolicyValue: input.currentPolicyValue,
                              endorsementPolicyValue: input.endorsementPolicyValue,
                              billingCycleCurrentPolicyValue: input.billingCycleEndorsementPolicyValue,
                              billingCycleEndorsementPolicyValue: input.billingCycleEndorsementPolicyValue,
                              policyPriceDifferenceValue: input.policyPriceDifferenceValue,
                              billingCyclePolicyPriceDifferenceValue: input.billingCyclePolicyPriceDifferenceValue,
                              scheduledItem: scheduledItem)
        
        guard let result: T = pricing as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
