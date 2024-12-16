import Foundation
import KanguroNetworkDomain
import KanguroRentersDomain
import KanguroSharedDomain

public struct RenterPolicyMapper: ModelMapper {
    public typealias T = RenterPolicy
    
    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteRenterPolicy = input as? RemoteRenterPolicy else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        
        var renterPolicy: RenterPolicy? = nil
        var dwellingType: DwellingType? = nil
        var address: Address? = nil
        var policyStatus: PolicyStatus? = nil
        var additionalCoverages: [RenterAdditionalCoverage]? = nil
        var payment: Payment? = nil
        var planSummary: PlanSummary? = nil
        
        if let remoteAddress = input.address {
            address = Address(state: remoteAddress.state,
                              city: remoteAddress.city,
                              streetNumber: remoteAddress.streetNumber,
                              streetName: remoteAddress.streetName,
                              zipCode: remoteAddress.zipCode,
                              complement: remoteAddress.complement)
        }
        
        if let remotePolicyStatus = input.status {
            policyStatus = PolicyStatus(rawValue: remotePolicyStatus.rawValue)
        }
        
        if let remoteRenterAdditionalCoverage = input.additionalCoverages {
            additionalCoverages = try RenterAdditionalCoveragesMapper.map(remoteRenterAdditionalCoverage)
        }
        
        if let remoteDwellingType = input.dwellingType {
            dwellingType = DwellingType(rawValue: remoteDwellingType.rawValue)
        }
        
        if let remotePayment = input.payment {
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
        
        if let remotePlanSummary = input.planSummary {
            planSummary = PlanSummary(
                liability: try PoliciesPlanSummaryItemMapper.map(remotePlanSummary.liability),
                deductible: try PoliciesPlanSummaryItemMapper.map(remotePlanSummary.deductible),
                personalProperty: try PoliciesPlanSummaryItemMapper.map(remotePlanSummary.personalProperty),
                lossOfUse: try PoliciesPlanSummaryItemMapper.map(remotePlanSummary.lossOfUse)
            )
        }
        
        renterPolicy = RenterPolicy(
            id: input.id,
            offerId: input.offerId,
            dwellingType: dwellingType,
            address: address,
            status: policyStatus,
            createdAt: input.createdAt?.date,
            startAt: input.startAt?.date,
            endAt: input.endAt?.date,
            planSummary: planSummary,
            additionalCoverages: additionalCoverages,
            payment: payment,
            isInsuranceRequired: input.isInsuranceRequired,
            onboardingCompleted: input.onboardingCompleted,
            policyExternalId: input.policyExternalId
        )
        
        guard let result: T = renterPolicy as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

public struct RenterPoliciesMapper: ModelMapper {
    public typealias T = [RenterPolicy]
    
    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemoteRenterPolicy] = input as? [RemoteRenterPolicy] else { throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map") }
        let policies: [RenterPolicy] = try input.map {
            return try RenterPolicyMapper.map($0)
        }
        guard let result = policies as? T else { throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map") }
        return result
    }
}
