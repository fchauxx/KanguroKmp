import Foundation
import KanguroSharedDomain

public struct RenterPolicy: Hashable {
    
    public var id: String
    public var offerId: Double?
    public var dwellingType: DwellingType?
    public var address: Address?
    public var status: PolicyStatus?
    public var createdAt: Date?
    public var startAt: Date?
    public var endAt: Date?
    public var planSummary: PlanSummary?
    public var additionalCoverages: [RenterAdditionalCoverage]?
    public var payment: Payment?
    public var isInsuranceRequired: Bool?
    public var onboardingCompleted: Bool?
    public var policyExternalId: Int?
    
    public init(
        id: String,
        offerId: Double? = nil,
        dwellingType: DwellingType? = nil,
        address: Address? = nil,
        status: PolicyStatus? = nil,
        createdAt: Date? = nil,
        startAt: Date? = nil,
        endAt: Date? = nil,
        planSummary: PlanSummary? = nil,
        additionalCoverages: [RenterAdditionalCoverage]? = nil,
        payment: Payment? = nil,
        isInsuranceRequired: Bool? = nil,
        onboardingCompleted: Bool? = nil,
        policyExternalId: Int? = nil
    ) {
        self.id = id
        self.offerId = offerId
        self.dwellingType = dwellingType
        self.address = address
        self.status = status
        self.createdAt = createdAt
        self.startAt = startAt
        self.endAt = endAt
        self.planSummary = planSummary
        self.additionalCoverages = additionalCoverages
        self.payment = payment
        self.isInsuranceRequired = isInsuranceRequired
        self.onboardingCompleted = onboardingCompleted
        self.policyExternalId = policyExternalId
    }
}
