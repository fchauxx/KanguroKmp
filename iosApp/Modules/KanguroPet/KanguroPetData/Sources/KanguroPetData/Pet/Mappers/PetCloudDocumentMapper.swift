import Foundation
import KanguroNetworkDomain
import KanguroPetDomain
import KanguroSharedDomain
import KanguroSharedData

public struct PetCloudDocumentMapper: ModelMapper {
    public typealias T = CloudDocument

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemotePetCloudDocument = input as? RemotePetCloudDocument else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        let cloudDocument = CloudDocument(
            userId: input.userId,
            pets: try PetsCloudMapper.map(input.pets),
            renters: try RentersCloudMapper.map(input.renters)
        )

        guard let result: T = cloudDocument as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
