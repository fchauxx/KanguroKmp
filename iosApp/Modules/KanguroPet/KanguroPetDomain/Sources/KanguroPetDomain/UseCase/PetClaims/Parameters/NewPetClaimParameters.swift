import Foundation
import KanguroSharedDomain

public struct NewPetClaimParameters {

    public var description: String
    public var invoiceDate: Date
    public var amount: Double
    public var petId: Int
    public var type: PetClaimType
    public var pledgeOfHonorId: Int
    public var reimbursementProcess: ReimbursementProcessType
    public var documentIds: [Int]

    public init(
        description: String,
        invoiceDate: Date,
        amount: Double,
        petId: Int,
        type: PetClaimType,
        pledgeOfHonorId: Int,
        reimbursementProcess: ReimbursementProcessType,
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
