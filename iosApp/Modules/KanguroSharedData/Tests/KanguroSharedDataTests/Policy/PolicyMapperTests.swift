import XCTest
import KanguroSharedDomain
import KanguroSharedData

final class PolicyMapperTests: XCTestCase {

    func testPolicyMapper() {
        let (policy, remotePolicy) = createPolicy(id: "ABCCAF1234")
        let mapped: Policy? = try? PolicyMapper.map(remotePolicy)
        XCTAssertEqual(mapped, policy)
    }

    func testPoliciesMapper() {
        let (policy1, remotePolicy1) = createPolicy(id: "ABCCAF1234")
        let (policy2, remotePolicy2) = createPolicy(id: "ABCCAF1234")
        let mapped: [Policy]? = try? PoliciesMapper.map([remotePolicy1, remotePolicy2])
        XCTAssertEqual(mapped, [policy1, policy2])
    }

    // MARK: Helpers

    enum CustomError: Codable, Error {
        case unowned
    }

    func createPolicy(id: String) -> (Policy, RemotePolicy) {
        let policy = TestPolicyFactory.makePolicy(
            id: id,
            startDate: Date(timeIntervalSince1970: 1598627222),
            waitingPeriod: Date(timeIntervalSince1970: 1598627222),
            waitingPeriodRemainingDays: Double(1),
            endDate: Date(timeIntervalSince1970: 1598627222),
            deductable: Deductable(id: 1, limit: Double(100), consumed: Double(30)),
            reimbursment: Reimbursment(id: 10, amount: Double(10)),
            sumInsured: SumInsured(id: 1, limit: Double(3), consumed: Double(0), remainingValue: Double(3)),
            status: .ACTIVE,
            feedbackRate: 3,
            petId: 2,
            payment: Payment(totalPayment: Double(30), invoiceInterval: .MONTHLY, firstPayment: Double(5), recurringPayment: Double(1)),
            preventive: false,
            policyExternalId: 3,
            policyOfferId: 4
        )

        let remotePolicy = TestPolicyFactory.makeRemotePolicy(
            id: id,
            startDate: "2020-08-28T15:07:02+00:00",
            waitingPeriod: "2020-08-28T15:07:02+00:00",
            waitingPeriodRemainingDays: Double(1),
            endDate: "2020-08-28T15:07:02+00:00",
            deductable: RemoteDeductable(id: 1, limit: Double(100), consumed: Double(30)),
            reimbursment: RemoteReimbursment(id: 10, amount: Double(10)),
            sumInsured: RemoteSumInsured(id: 1, limit: Double(3), consumed: Double(0), remainingValue: Double(3)),
            status: .ACTIVE,
            feedbackRate: 3,
            petId: 2,
            payment: RemotePayment(totalPayment: Double(30), invoiceInterval: .MONTHLY, firstPayment: Double(5), recurringPayment: Double(1)),
            preventive: false,
            policyExternalId: 3,
            policyOfferId: 4
        )
        return (policy, remotePolicy)
    }

}
