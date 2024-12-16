import Foundation
import KanguroSharedData

public struct RemoteRenterPolicy: Codable {
    
    public var id: String
    public var offerId: Double?
    public var dwellingType: RemoteDwellingType?
    public var address: RemoteAddress?
    public var status: RemotePolicyStatus?
    public var createdAt: String?
    public var startAt: String?
    public var endAt: String?
    public var planSummary: RemotePlanSummary?
    public var additionalCoverages: [RemoteRenterAdditionalCoverage]?
    public var payment: RemotePayment?
    public var isInsuranceRequired: Bool?
    public var onboardingCompleted: Bool?
    public var policyExternalId: Int?
    
    public init(
        id: String,
        offerId: Double? = nil,
        dwellingType: RemoteDwellingType? = nil,
        address: RemoteAddress? = nil,
        status: RemotePolicyStatus? = nil,
        createdAt: String? = nil,
        startAt: String? = nil,
        endAt: String? = nil,
        additionalCoverages: [RemoteRenterAdditionalCoverage]? = nil,
        payment: RemotePayment? = nil,
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
        self.additionalCoverages = additionalCoverages
        self.payment = payment
        self.isInsuranceRequired = isInsuranceRequired
        self.onboardingCompleted = onboardingCompleted
        self.policyExternalId = policyExternalId
    }
}
