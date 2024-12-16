import Foundation
import KanguroNetworkDomain
import KanguroSharedDomain

public struct PetCloudMapper: ModelMapper {
    public init() {}

    public typealias T = PetCloud

    public static func map<T>(_ input: some Decodable & Encodable) throws -> T {
        guard let input: RemotePetCloud = input as? RemotePetCloud else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        let petCloud = PetCloud(
            id: input.id,
            name: input.name,
            cloudDocumentPolicies: try CloudDocumentsPolicyMapper.map(
                input.cloudDocumentPolicies
            )
        )

        guard let result: T = petCloud as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        return result
    }
}

public struct PetsCloudMapper: ModelMapper {
    public init() {}
    
    public typealias T = [PetCloud]

    public static func map<T>(_ input: some Decodable & Encodable) throws -> T {
        guard let input: [RemotePetCloud] = input as? [RemotePetCloud] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        let petsCloud: [PetCloud] = try input.map {
            try PetCloudMapper.map($0)
        }

        guard let result: T = petsCloud as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        return result
    }
}
