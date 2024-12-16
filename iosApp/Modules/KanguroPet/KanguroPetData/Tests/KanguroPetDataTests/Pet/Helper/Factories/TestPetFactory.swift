import Foundation
import KanguroSharedData
import KanguroSharedDomain
import KanguroPetDomain
import KanguroPetData

struct TestPetFactory {
    static func makeRemotePet(
        id: Int = 1,
        name: String? = "Azeitona",
        birthDate: String? = "2020-08-28T15:07:02+00:00",
        isBirthDateApproximated: Bool? = false,
        gender: RemoteGender? = RemoteGender.Female,
        type: RemotePetType? = RemotePetType.Dog,
        size: String? = "large",
        isNeutered: Bool? = true,
        preExistingConditions: String? = "false",
        petBreedId: Int? = 1,
        breed: String? = "Mixed",
        zipCode: String? = "055410",
        territoryId: Int? = 3,
        additionalInfoSessionId: String? = "ADD INFO",
        hasAdditionalInfo: Bool? = false,
        petPictureUrl: String? = "http://test.io",
        cloudDocumentPolicies: [RemoteCloudDocumentPolicy]? = [RemoteCloudDocumentPolicy(id: "remote", ciId: 1, policyStartDate: "2020-08-28T15:07:02+00:00", policyAttachments: nil, policyDocuments: nil, claimDocuments: nil)]
    ) -> RemotePet {
        RemotePet(
            id: id,
            name: name,
            birthDate: birthDate,
            isBirthDateApproximated: isBirthDateApproximated,
            gender: gender,
            type: type,
            size: size,
            isNeutered: isNeutered,
            preExistingConditions: preExistingConditions,
            petBreedId: petBreedId,
            breed: breed,
            zipCode: zipCode,
            territoryId: territoryId,
            additionalInfoSessionId: additionalInfoSessionId,
            hasAdditionalInfo: hasAdditionalInfo,
            petPictureUrl: petPictureUrl,
            cloudDocumentPolicies: cloudDocumentPolicies
        )
    }

    static func makePet(
        id: Int = 1,
        name: String? = "Azeitona",
        birthDate: Date? = Date(timeIntervalSince1970: 1598627222), // "2020-08-28T15:07:02+00:00"
        isBirthDateApproximated: Bool? = false,
        gender: PetGender? = PetGender.Female,
        type: PetType? = PetType.Dog,
        size: String? = "large",
        isNeutered: Bool? = true,
        preExistingConditions: String? = "false",
        petBreedId: Int? = 1,
        breed: String? = "Mixed",
        zipCode: String? = "055410",
        territoryId: Int? = 3,
        additionalInfoSessionId: String? = "ADD INFO",
        hasAdditionalInfo: Bool? = false,
        petPictureUrl: String? = "http://test.io",
        cloudDocumentPolicies: [CloudDocumentPolicy]? = [CloudDocumentPolicy(id: "remote", ciId: 1, policyStartDate: Date(timeIntervalSince1970: 1598627222), policyAttachments: nil, policyDocuments: nil, claimDocuments: nil)]
    ) -> Pet {
        Pet(
            id: id,
            name: name,
            birthDate: birthDate,
            isBirthDateApproximated: isBirthDateApproximated,
            gender: gender,
            type: type,
            size: size,
            isNeutered: isNeutered,
            preExistingConditions: preExistingConditions,
            petBreedId: petBreedId,
            breed: breed,
            zipCode: zipCode,
            territoryId: territoryId,
            additionalInfoSessionId: additionalInfoSessionId,
            hasAdditionalInfo: hasAdditionalInfo,
            petPictureUrl: petPictureUrl,
            cloudDocumentPolicies: cloudDocumentPolicies
        )
    }
}
