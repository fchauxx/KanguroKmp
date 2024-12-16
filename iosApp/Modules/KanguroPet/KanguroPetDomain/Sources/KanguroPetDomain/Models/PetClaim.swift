import Foundation
import KanguroSharedDomain

public struct PetClaim: ClaimProtocol {
    
    public var id: String
    public var pet: Pet?
    public var type: PetClaimType?
    public var status: ClaimStatus?
    public var decision: String?
    public var createdAt: Date?
    public var updatedAt: Date?
    public var incidentDate: Date?
    public var description: String?
    public var prefixId: String?
    public var amount: Double?
    public var amountPaid: Double?
    public var amountTransferred: Double?
    public var deductibleContributionAmount: Double?
    public var chatbotSessionsIds: [String]?
    public var isPendingCommunication: Bool
    public var reimbursementProcess: ReimbursementProcessType?
    public var fileUrl: String?
    public var statusDescription: ClaimStatusDescription?

    // MARK: - Computed Properties
    public var isOpenStatus: Bool {
        return (status == .Submitted ||
                status == .InReview ||
                status == .Assigned)
    }
    public var isClosedStatus: Bool {
        return (status == .Closed ||
                status == .Denied ||
                status == .Approved ||
                status == .Paid)
    }
    public var isWarningViewRequired: Bool {
        return (
            status == .Denied || 
            status == .PendingMedicalHistory ||
            isPendingCommunication == true
        )
    }

    public var trackerStatusList: [ClaimStatus] {
        return [.Submitted, .Assigned, .InReview, .Closed]
    }
    
    public init(id: String,
                pet: Pet? = nil,
                type: PetClaimType? = nil,
                status: ClaimStatus? = nil,
                decision: String? = nil,
                createdAt: Date? = nil,
                updatedAt: Date? = nil,
                incidentDate: Date? = nil,
                description: String? = nil,
                prefixId: String? = nil,
                amount: Double? = nil,
                amountPaid: Double? = nil,
                amountTransferred: Double? = nil,
                deductibleContributionAmount: Double? = nil,
                chatbotSessionsIds: [String]? = nil,
                isPendingCommunication: Bool = false,
                reimbursementProcess: ReimbursementProcessType? = nil,
                fileUrl: String? = nil,
                statusDescription: ClaimStatusDescription? = nil) {
        self.id = id
        self.pet = pet
        self.type = type
        self.status = status
        self.decision = decision
        self.createdAt = createdAt
        self.updatedAt = updatedAt
        self.incidentDate = incidentDate
        self.description = description
        self.prefixId = prefixId
        self.amount = amount
        self.amountPaid = amountPaid
        self.amountTransferred = amountTransferred
        self.deductibleContributionAmount = deductibleContributionAmount
        self.chatbotSessionsIds = chatbotSessionsIds
        self.isPendingCommunication = isPendingCommunication
        self.reimbursementProcess = reimbursementProcess
        self.fileUrl = fileUrl
        self.statusDescription = statusDescription
    }
}
