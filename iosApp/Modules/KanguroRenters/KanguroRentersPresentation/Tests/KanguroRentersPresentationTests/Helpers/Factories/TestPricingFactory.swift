import Foundation
import KanguroRentersDomain

public struct TestPricingFactory {

    static func makePricingWithDecimals(
        billingCycle: BillingCycle? = .MONTHLY,
        currentPolicyValue: Double? = 500.3,
        endorsementPolicyValue: Double? = 500.310,
        billingCycleCurrentPolicyValue: Double? = 800.99,
        billingCycleEndorsementPolicyValue: Double? = 499.40938,
        policyPriceDifferenceValue: Double? = 900.130,
        billingCyclePolicyPriceDifferenceValue: Double? = 1008.43038,
        scheduledItem: ScheduledItem? = TestScheduledItemFactory.makeScheduledItem()) -> Pricing {
            Pricing(billingCycle: billingCycle,
                    currentPolicyValue: currentPolicyValue,
                    endorsementPolicyValue: endorsementPolicyValue,
                    billingCycleCurrentPolicyValue: billingCycleCurrentPolicyValue,
                    billingCycleEndorsementPolicyValue: billingCycleEndorsementPolicyValue,
                    policyPriceDifferenceValue: policyPriceDifferenceValue,
                    billingCyclePolicyPriceDifferenceValue: billingCyclePolicyPriceDifferenceValue,
                    scheduledItem: scheduledItem)
        }

    static func makePricingWithNoDecimals(
        billingCycle: BillingCycle? = .MONTHLY,
        currentPolicyValue: Double? = 500,
        endorsementPolicyValue: Double? = 500,
        billingCycleCurrentPolicyValue: Double? = 800,
        billingCycleEndorsementPolicyValue: Double? = 499,
        policyPriceDifferenceValue: Double? = 900,
        billingCyclePolicyPriceDifferenceValue: Double? = 1000,
        scheduledItem: ScheduledItem? = TestScheduledItemFactory.makeScheduledItem()) -> Pricing {
            Pricing(billingCycle: billingCycle,
                    currentPolicyValue: currentPolicyValue,
                    endorsementPolicyValue: endorsementPolicyValue,
                    billingCycleCurrentPolicyValue: billingCycleCurrentPolicyValue,
                    billingCycleEndorsementPolicyValue: billingCycleEndorsementPolicyValue,
                    policyPriceDifferenceValue: policyPriceDifferenceValue,
                    billingCyclePolicyPriceDifferenceValue: billingCyclePolicyPriceDifferenceValue,
                    scheduledItem: scheduledItem)
        }

    static func makePricingWithNilValues(
        billingCycle: BillingCycle? = .MONTHLY,
        currentPolicyValue: Double? = nil,
        endorsementPolicyValue: Double? = nil,
        billingCycleCurrentPolicyValue: Double? = nil,
        billingCycleEndorsementPolicyValue: Double? = nil,
        policyPriceDifferenceValue: Double? = nil,
        billingCyclePolicyPriceDifferenceValue: Double? = nil,
        scheduledItem: ScheduledItem? = TestScheduledItemFactory.makeScheduledItem()) -> Pricing {
            Pricing(billingCycle: billingCycle,
                    currentPolicyValue: currentPolicyValue,
                    endorsementPolicyValue: endorsementPolicyValue,
                    billingCycleCurrentPolicyValue: billingCycleCurrentPolicyValue,
                    billingCycleEndorsementPolicyValue: billingCycleEndorsementPolicyValue,
                    policyPriceDifferenceValue: policyPriceDifferenceValue,
                    billingCyclePolicyPriceDifferenceValue: billingCyclePolicyPriceDifferenceValue,
                    scheduledItem: scheduledItem)
        }
}
