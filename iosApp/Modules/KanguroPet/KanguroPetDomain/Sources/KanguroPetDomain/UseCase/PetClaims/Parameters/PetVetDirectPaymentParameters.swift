import Foundation

public struct PetVetDirectPaymentParameters: Equatable {

    public var petId: Int?
    public var type: PetClaimType?
    public var invoiceDate: Date?
    public var veterinarianEmail: String?
    public var veterinarianName: String?
    public var veterinarianClinic: String?
    public var amount: CGFloat?
    public var description: String?
    public var veterinarianId: Int?
    public var pledgeOfHonor: String?
    public var pledgeOfHonorExtension: String?

    public init(petId: Int? = nil,
                type: PetClaimType? = nil,
                invoiceDate: Date? = nil,
                veterinarianEmail: String? = nil,
                veterinarianName: String? = nil,
                veterinarianClinic: String? = nil,
                description: String? = nil,
                amount: CGFloat? = nil,
                veterinarianId: Int? = nil,
                pledgeOfHonor: String? = nil,
                pledgeOfHonorExtension: String? = nil) {
        self.petId = petId
        self.type = type
        self.invoiceDate = invoiceDate
        self.veterinarianName = veterinarianName
        self.veterinarianEmail = veterinarianEmail
        self.veterinarianClinic = veterinarianClinic
        self.description = description
        self.amount = amount
        self.veterinarianId = veterinarianId
        self.pledgeOfHonor = pledgeOfHonor
        self.pledgeOfHonorExtension = pledgeOfHonorExtension
    }
}
