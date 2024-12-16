import Foundation
import KanguroSharedData

public struct RemotePetClaim: Codable, Equatable {

    // MARK: - Stored Properties
    public var id: String
    public var pet: RemotePet?
    public var type: RemoteClaimType?
    public var status: String?
    public var decision: String?
    public var createdAt: String?
    public var updatedAt: String?
    public var invoiceDate: String?
    public var description: String?
    public var prefixId: String?
    public var amount: Double?
    public var amountPaid: Double?
    public var amountTransferred: Double?
    public var deductibleContributionAmount: Double?
    public var chatbotSessionsIds: [String]?
    public var hasPendingCommunications: Bool?
    public var reimbursementProcess: RemoteReimbursementProcessType?
    public var fileUrl: String?
    public var statusDescription: RemoteClaimStatusDescription?

    public init(
        id: String,
        pet: RemotePet? = nil,
        type: RemoteClaimType? = nil,
        status: String? = nil,
        decision: String? = nil,
        createdAt: String? = nil,
        updatedAt: String? = nil,
        invoiceDate: String? = nil,
        description: String? = nil,
        prefixId: String? = nil,
        amount: Double? = nil,
        amountPaid: Double? = nil,
        amountTransferred: Double? = nil,
        deductibleContributionAmount: Double? = nil,
        chatbotSessionsIds: [String]? = nil,
        hasPendingCommunications: Bool? = nil,
        reimbursementProcess: RemoteReimbursementProcessType? = nil,
        fileUrl: String? = nil,
        statusDescription: RemoteClaimStatusDescription? = nil
    ) {
        self.id = id
        self.pet = pet
        self.type = type
        self.status = status
        self.decision = decision
        self.createdAt = createdAt
        self.updatedAt = updatedAt
        self.invoiceDate = invoiceDate
        self.description = description
        self.prefixId = prefixId
        self.amount = amount
        self.amountPaid = amountPaid
        self.amountTransferred = amountTransferred
        self.deductibleContributionAmount = deductibleContributionAmount
        self.chatbotSessionsIds = chatbotSessionsIds
        self.hasPendingCommunications = hasPendingCommunications
        self.reimbursementProcess = reimbursementProcess
        self.fileUrl = fileUrl
        self.statusDescription = statusDescription
    }
}

public enum RemoteClaimType: String, Codable, Equatable {

    case Illness
    case Accident
    case Other
}
