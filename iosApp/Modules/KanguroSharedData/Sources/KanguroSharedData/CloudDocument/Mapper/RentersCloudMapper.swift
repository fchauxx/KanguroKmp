import Foundation
import KanguroNetworkDomain
import KanguroSharedDomain

public struct RenterCloudMapper: ModelMapper {
    public init() {}

    public typealias T = RentersCloud

    public static func map<T>(_ input: some Decodable & Encodable) throws -> T {
        guard let input: RemoteRentersCloud = input as? RemoteRentersCloud else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        let rentersCloud = RentersCloud(
            id: input.id,
            name: input.name,
            cloudDocumentPolicies: try CloudDocumentsPolicyMapper.map(
                input.cloudDocumentPolicies
            )
        )

        guard let result: T = rentersCloud as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        return result
    }
}

public struct RentersCloudMapper: ModelMapper {
    public init() {}
    
    public typealias T = [RentersCloud]

    public static func map<T>(_ input: some Decodable & Encodable) throws -> T {
        guard let input: [RemoteRentersCloud] = input as? [RemoteRentersCloud] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        let rentersCloud: [RentersCloud] = try input.map {
            try RenterCloudMapper.map($0)
        }

        guard let result: T = rentersCloud as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        return result
    }
}

