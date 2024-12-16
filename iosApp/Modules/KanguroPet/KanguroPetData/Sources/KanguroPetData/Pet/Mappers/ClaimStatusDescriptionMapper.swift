import KanguroSharedDomain
import KanguroPetDomain
import KanguroNetworkDomain

public struct ClaimStatusDescriptionMapper: ModelMapper {
    public typealias T = ClaimStatusDescription
    
    public static func map<T>(_ input: some Decodable & Encodable) throws -> T {
        guard let input: RemoteClaimStatusDescription = input as? RemoteClaimStatusDescription else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        let claimStatusDescriptionType: ClaimStatusDescription = ClaimStatusDescription(
            description: input.description ?? "",
            type: StatusDescriptionType(value: input.type ?? "")
        )

        guard let result: T = claimStatusDescriptionType as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        return result
    }
}
