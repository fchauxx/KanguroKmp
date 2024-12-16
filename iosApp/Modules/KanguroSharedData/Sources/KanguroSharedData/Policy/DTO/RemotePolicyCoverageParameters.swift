public struct RemotePolicyCoverageParameters: Codable {

    public var id: String
    public var offerId: Int?
    public var reimbursement: Double

    public init(
        id: String,
        offerId: Int? = nil,
        reimbursement: Double
    ) {
        self.id = id
        self.offerId = offerId
        self.reimbursement = reimbursement
    }
}
