import Foundation
import KanguroSharedData

public struct RemotePet: Codable, Equatable {

    // MARK: - Stored Properties
    public var id: Int
    public var name: String?
    public var birthDate: String?
    public var isBirthDateApproximated: Bool?
    public var gender: RemoteGender?
    public var type: RemotePetType?
    public var size: String?
    public var isNeutered: Bool?
    public var preExistingConditions: String?
    public var petBreedId: Int?
    public var breed: String?
    public var zipCode: String?
    public var territoryId: Int?
    public var additionalInfoSessionId: String?
    public var hasAdditionalInfo: Bool?
    public var petPictureUrl: String?
    public var cloudDocumentPolicies: [RemoteCloudDocumentPolicy]?

    public init(
        id: Int,
        name: String? = nil,
        birthDate: String? = nil,
        isBirthDateApproximated: Bool? = nil,
        gender: RemoteGender? = nil,
        type: RemotePetType? = nil,
        size: String? = nil,
        isNeutered: Bool? = nil,
        preExistingConditions: String? = nil,
        petBreedId: Int? = nil,
        breed: String? = nil,
        zipCode: String? = nil,
        territoryId: Int? = nil,
        additionalInfoSessionId: String? = nil,
        hasAdditionalInfo: Bool? = nil,
        petPictureUrl: String? = nil,
        cloudDocumentPolicies: [RemoteCloudDocumentPolicy]? = nil
    ) {
        self.id = id
        self.name = name
        self.birthDate = birthDate
        self.isBirthDateApproximated = isBirthDateApproximated
        self.gender = gender
        self.type = type
        self.size = size
        self.isNeutered = isNeutered
        self.preExistingConditions = preExistingConditions
        self.petBreedId = petBreedId
        self.breed = breed
        self.zipCode = zipCode
        self.territoryId = territoryId
        self.additionalInfoSessionId = additionalInfoSessionId
        self.hasAdditionalInfo = hasAdditionalInfo
        self.petPictureUrl = petPictureUrl
        self.cloudDocumentPolicies = cloudDocumentPolicies
    }
}
