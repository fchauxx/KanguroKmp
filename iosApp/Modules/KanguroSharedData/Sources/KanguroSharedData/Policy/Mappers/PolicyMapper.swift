import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public struct PolicyMapper: ModelMapper {
    public typealias T = Policy

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemotePolicy = input as? RemotePolicy else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        var deductable: Deductable? = nil
        var reimbursment: Reimbursment? = nil
        var sumInsured: SumInsured? = nil
        var status: PolicyStatus? = nil
        var payment: Payment? = nil
        if let remoteDeductable: RemoteDeductable = input.deductable {
            deductable = Deductable(
                id: remoteDeductable.id,
                limit: remoteDeductable.limit,
                consumed: remoteDeductable.consumed
            )
        }
        if let remoteReimbursment: RemoteReimbursment = input.reimbursment {
            reimbursment = Reimbursment(
                id: remoteReimbursment.id,
                amount: remoteReimbursment.amount
            )
        }
        if let remoteSumInsured: RemoteSumInsured = input.sumInsured {
            sumInsured = SumInsured(
                id: remoteSumInsured.id,
                limit: remoteSumInsured.limit,
                consumed: remoteSumInsured.consumed,
                remainingValue: remoteSumInsured.remainingValue
            )
        }
        if let remotePolicyStatus: RemotePolicyStatus = input.status {
            status = PolicyStatus(rawValue: remotePolicyStatus.rawValue)
        }
        if let remotePayment: RemotePayment = input.payment {
            guard let invoiceType = remotePayment.invoiceInterval?.rawValue,
                  let invoiceInterval: InvoiceType = InvoiceType(rawValue: invoiceType) else {
                throw(RequestError(errorType: .couldNotMap, errorMessage: "Could not map"))
            }
            payment = Payment(
                totalPayment: remotePayment.totalPayment,
                invoiceInterval: invoiceInterval,
                firstPayment: remotePayment.firstPayment,
                recurringPayment: remotePayment.recurringPayment
            )
        }
        guard let policy = Policy(
            id: input.id,
            startDate: input.startDate?.date,
            waitingPeriod: input.waitingPeriod?.date,
            waitingPeriodRemainingDays: input.waitingPeriodRemainingDays,
            endDate: input.endDate?.date,
            deductable: deductable,
            reimbursment: reimbursment,
            sumInsured: sumInsured,
            status: status,
            feedbackRate: input.feedbackRate,
            petId: input.petId,
            payment: payment,
            preventive: input.preventive,
            policyExternalId: input.policyExternalId,
            policyOfferId: input.policyOfferId
        ) as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return policy
    }
}

public struct PoliciesMapper: ModelMapper {
    public typealias T = [Policy]

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemotePolicy] = input as? [RemotePolicy] else { throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map") }
        let policies: [Policy] = try input.map {
            return try PolicyMapper.map($0)
        }
        guard let result = policies as? T else { throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map") }
        return result
    }
}
