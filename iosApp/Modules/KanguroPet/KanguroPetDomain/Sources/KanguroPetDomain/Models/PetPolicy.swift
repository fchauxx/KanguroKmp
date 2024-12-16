import Foundation
import KanguroSharedDomain

public final class PetPolicy: PetPolicyProtocol {
    
    public var petId: Int?
    public var id: String?
    public var startDate: Date?
    public var waitingPeriod: Date?
    public var waitingPeriodRemainingDays: Double?
    public var endDate: Date?
    public var deductable: Deductable?
    public var reimbursment: Reimbursment?
    public var sumInsured: SumInsured?
    public var status: PolicyStatus?
    public var feedbackRate: Int?
    public var pet: Pet
    public var payment: Payment?
    public var preventive: Bool?
    public var policyExternalId: Int?
    public var policyOfferId: Int?
    public var isFuture: Bool?

    public init(
        id: String? = nil,
        startDate: Date? = nil,
        waitingPeriod: Date? = nil,
        waitingPeriodRemainingDays: Double? = nil,
        endDate: Date? = nil,
        deductable: Deductable? = nil,
        reimbursment: Reimbursment? = nil,
        sumInsured: SumInsured? = nil,
        status: PolicyStatus? = nil,
        feedbackRate: Int? = nil,
        pet: Pet,
        payment: Payment? = nil,
        preventive: Bool? = nil,
        policyExternalId: Int? = nil,
        policyOfferId: Int? = nil,
        isFuture: Bool? = nil
    ) {
        self.id = id
        self.startDate = startDate
        self.waitingPeriod = waitingPeriod
        self.waitingPeriodRemainingDays = waitingPeriodRemainingDays
        self.endDate = endDate
        self.deductable = deductable
        self.reimbursment = reimbursment
        self.sumInsured = sumInsured
        self.status = status
        self.feedbackRate = feedbackRate
        self.pet = pet
        self.payment = payment
        self.preventive = preventive
        self.policyExternalId = policyExternalId
        self.policyOfferId = policyOfferId
        self.isFuture = isFuture
    }
}

extension PetPolicy: Hashable {
    public static func == (lhs: PetPolicy, rhs: PetPolicy) -> Bool {
        return lhs.id == rhs.id
    }

    public func hash(into hasher: inout Hasher) {
        hasher.combine(id)
    }
}
