import Foundation

public struct RemoteVeterinarian: Codable {

    public var id: Int?
    public var clinicName: String?
    public var veterinarianName: String?
    public var email: String?

    public init(id: Int? = nil,
                clinicName: String? = nil,
                veterinarianName: String? = nil,
                email: String? = nil) {
        self.id = id
        self.clinicName = clinicName
        self.veterinarianName = veterinarianName
        self.email = email
    }
}
