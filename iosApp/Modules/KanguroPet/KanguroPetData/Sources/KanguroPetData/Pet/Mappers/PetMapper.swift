import Foundation
import KanguroSharedData
import KanguroSharedDomain
import KanguroNetworkDomain
import KanguroPetDomain

public struct PetsMapper: ModelMapper {
    public typealias T = [Pet]

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemotePet] = input as? [RemotePet] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let pets: [Pet] = try input.map {
            try PetMapper.map($0)
        }
        guard let result: T = pets as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

public struct PetMapper: ModelMapper {
    public typealias T = Pet

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemotePet = input as? RemotePet else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        var gender: PetGender? = nil
        var petType: PetType? = nil
        var cloudDocumentPolicies: [CloudDocumentPolicy]? = nil
        if let remoteGender: RemoteGender = input.gender {
            gender = PetGender(rawValue: remoteGender.rawValue)
        }
        if let remotePetType: RemotePetType = input.type {
            petType = PetType(rawValue: remotePetType.rawValue)
        }
        if let remoteCloudDocumentPolicies: [RemoteCloudDocumentPolicy] =
            input.cloudDocumentPolicies {
            cloudDocumentPolicies = try
            CloudDocumentsPolicyMapper.map(remoteCloudDocumentPolicies)
        }
        let pet = Pet(
            id: input.id,
            name: input.name,
            birthDate: input.birthDate?.date,
            isBirthDateApproximated: input.isBirthDateApproximated,
            gender: gender,
            type: petType,
            size: input.size,
            isNeutered: input.isNeutered,
            preExistingConditions: input.preExistingConditions,
            petBreedId: input.petBreedId,
            breed: input.breed,
            zipCode: input.zipCode,
            territoryId: input.territoryId,
            additionalInfoSessionId: input.additionalInfoSessionId,
            hasAdditionalInfo: input.hasAdditionalInfo,
            petPictureUrl: input.petPictureUrl,
            cloudDocumentPolicies: cloudDocumentPolicies
        )
        guard let result: T = pet as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
