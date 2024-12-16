import Foundation

public struct RemotePetVetDirectPaymentParameters: Codable {

    public var petId: Int?
    public var type: RemoteClaimType?
    public var invoiceDate: String?
    public var veterinarianEmail: String?
    public var veterinarianName: String?
    public var veterinarianClinic: String?
    public var description: String?
    public var amount: CGFloat?
    public var veterinarianId: Int?
    public var pledgeOfHonor: String?
    public var pledgeOfHonorExtension: String?

    public init(petId: Int? = nil,
                type: RemoteClaimType? = nil,
                invoiceDate: String? = nil,
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
        self.veterinarianEmail = veterinarianEmail
        self.veterinarianName = veterinarianName
        self.veterinarianClinic = veterinarianClinic
        self.description = description
        self.amount = amount
        self.veterinarianId = veterinarianId
        self.pledgeOfHonor = pledgeOfHonor
        self.pledgeOfHonorExtension = pledgeOfHonorExtension
    }
}
