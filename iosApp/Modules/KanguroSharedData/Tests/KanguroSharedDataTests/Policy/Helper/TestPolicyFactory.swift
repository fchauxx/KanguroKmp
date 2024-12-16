import Foundation
import KanguroSharedData
import KanguroSharedDomain

struct TestPolicyFactory {
    static func makeRemotePolicy(
        id: String? = "MyPolicy",
        startDate: String? = "2020-08-28T15:07:02+00:00",
        waitingPeriod: String? = "2020-08-28T15:07:02+00:00",
        waitingPeriodRemainingDays: Double? = Double(10),
        endDate: String? = "2020-08-28T15:07:02+00:00",
        deductable: RemoteDeductable? = RemoteDeductable(
            id: 1,
            limit: Double(20),
            consumed: Double(10)
        ),
        reimbursment: RemoteReimbursment? = RemoteReimbursment(
            id: 1,
            amount: Double(10)
        ),
        sumInsured: RemoteSumInsured? = RemoteSumInsured(
            id: 1,
            limit: Double(30),
            consumed: Double(20),
            remainingValue: Double(10)
        ),
        status: RemotePolicyStatus? = .ACTIVE,
        feedbackRate: Int? = 8,
        petId: Int? = 1,
        payment: RemotePayment? = RemotePayment(
            totalPayment: Double(10),
            invoiceInterval: .YEARLY,
            firstPayment: Double(20),
            recurringPayment: Double(1)
        ),
        preventive: Bool? = false,
        policyExternalId: Int? = 1,
        policyOfferId: Int? = 1
    ) -> RemotePolicy {
        RemotePolicy(
            id: id,
            startDate: startDate,
            waitingPeriod: waitingPeriod,
            waitingPeriodRemainingDays: waitingPeriodRemainingDays,
            endDate: endDate,
            deductable: deductable,
            reimbursment: reimbursment,
            sumInsured: sumInsured,
            status: status,
            feedbackRate: feedbackRate,
            petId: petId,
            payment: payment,
            preventive: preventive,
            policyExternalId: policyExternalId,
            policyOfferId: policyOfferId
        )
    }

    static func makePolicy(
        id: String? = "MyPolicy",
        startDate: Date? = Date(timeIntervalSince1970: 1598627222), // "2020-08-28T15:07:02+00:00")
        waitingPeriod: Date? = Date(timeIntervalSince1970: 1598627222),
        waitingPeriodRemainingDays: Double? = Double(10),
        endDate: Date? = Date(timeIntervalSince1970: 1598627222),
        deductable: Deductable? = Deductable(
            id: 1,
            limit: Double(20),
            consumed: Double(10)
        ),
        reimbursment: Reimbursment? = Reimbursment(
            id: 1,
            amount: Double(10)
        ),
        sumInsured: SumInsured? = SumInsured(
            id: 1,
            limit: Double(30),
            consumed: Double(20),
            remainingValue: Double(10)
        ),
        status: PolicyStatus? = .ACTIVE,
        feedbackRate: Int? = 8,
        petId: Int? = 1,
        payment: Payment? = Payment(
            totalPayment: Double(10),
            invoiceInterval: .YEARLY,
            firstPayment: Double(20),
            recurringPayment: Double(1)
        ),
        preventive: Bool? = false,
        policyExternalId: Int? = 1,
        policyOfferId: Int? = 1
    ) -> Policy {
        Policy(
            id: id,
            startDate: startDate,
            waitingPeriod: waitingPeriod,
            waitingPeriodRemainingDays: waitingPeriodRemainingDays,
            endDate: endDate,
            deductable: deductable,
            reimbursment: reimbursment,
            sumInsured: sumInsured,
            status: status,
            feedbackRate: feedbackRate,
            petId: petId,
            payment: payment,
            preventive: preventive,
            policyExternalId: policyExternalId,
            policyOfferId: policyOfferId
        )
    }
}
