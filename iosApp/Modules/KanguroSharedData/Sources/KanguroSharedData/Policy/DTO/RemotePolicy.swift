
public struct RemotePolicy: Codable {
    public var id: String?
    public var startDate: String?
    public var waitingPeriod: String?
    public var waitingPeriodRemainingDays: Double?
    public var endDate: String?
    public var deductable: RemoteDeductable?
    public var reimbursment: RemoteReimbursment?
    public var sumInsured: RemoteSumInsured?
    public var status: RemotePolicyStatus?
    public var feedbackRate: Int?
    public var petId: Int?
    public var payment: RemotePayment?
    public var preventive: Bool?
    public var policyExternalId: Int?
    public var policyOfferId: Int?
    public var isFuture: Bool?

    public init(
        id: String? = nil,
        startDate: String? = nil,
        waitingPeriod: String? = nil,
        waitingPeriodRemainingDays: Double? = nil,
        endDate: String? = nil,
        deductable: RemoteDeductable? = nil,
        reimbursment: RemoteReimbursment? = nil,
        sumInsured: RemoteSumInsured? = nil,
        status: RemotePolicyStatus? = nil,
        feedbackRate: Int? = nil,
        petId: Int? = nil,
        payment: RemotePayment? = nil,
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
        self.petId = petId
        self.payment = payment
        self.preventive = preventive
        self.policyExternalId = policyExternalId
        self.policyOfferId = policyOfferId
        self.isFuture = isFuture
    }
}
