import KanguroPetDomain
import KanguroSharedDomain

public struct ClaimTrackerCardViewModel {
    let id: String
    let petName: String
    let petType: PetType
    let claimType: PetClaimType
    let claimStatus: ClaimStatus
    let claimLastUpdated: String
    let claimAmount: String
    let claimAmountPaid: String
    let reimbursementProcess: ReimbursementProcessType
    let claimStatusDescription: String?
    let petPictureUrl: String?

    public init(
        id: String,
        petName: String,
        petType: PetType,
        claimType: PetClaimType,
        claimStatus: ClaimStatus,
        claimLastUpdated: String,
        claimAmount: String,
        claimAmountPaid: String,
        reimbursementProcess: ReimbursementProcessType,
        claimStatusDescription: String? = nil,
        petPictureUrl: String? = nil
    ) {
        self.id = id
        self.petName = petName
        self.petType = petType
        self.claimType = claimType
        self.claimStatus = claimStatus
        self.claimLastUpdated = claimLastUpdated
        self.claimAmount = claimAmount
        self.claimAmountPaid = claimAmountPaid
        self.reimbursementProcess = reimbursementProcess
        self.claimStatusDescription = claimStatusDescription
        self.petPictureUrl = petPictureUrl
    }
}
