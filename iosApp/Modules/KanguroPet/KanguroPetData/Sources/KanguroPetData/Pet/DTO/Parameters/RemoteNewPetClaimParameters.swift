import Foundation
import KanguroSharedData

public struct RemoteNewPetClaimParameters: Codable {
    public var description: String
    public var invoiceDate: String
    public var amount: Double
    public var petId: Int
    public var type: RemoteClaimType
    public var pledgeOfHonorId: Int
    public var reimbursementProcess: RemoteReimbursementProcessType
    public var documentIds: [Int]

    public init(
        description: String,
        invoiceDate: String,
        amount: Double,
        petId: Int,
        type: RemoteClaimType,
        pledgeOfHonorId: Int,
        reimbursementProcess: RemoteReimbursementProcessType,
        documentIds: [Int]
    ) {
        self.description = description
        self.invoiceDate = invoiceDate
        self.amount = amount
        self.petId = petId
        self.type = type
        self.pledgeOfHonorId = pledgeOfHonorId
        self.reimbursementProcess = reimbursementProcess
        self.documentIds = documentIds
    }
}
