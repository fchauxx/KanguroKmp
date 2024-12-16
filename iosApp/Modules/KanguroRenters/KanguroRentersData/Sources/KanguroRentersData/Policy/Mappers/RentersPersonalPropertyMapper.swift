import Foundation
import KanguroSharedDomain
import KanguroRentersDomain
import KanguroNetworkDomain

public struct RentersPersonalPropertyMapper: ModelMapper {
    public typealias T = PersonalProperty

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemotePersonalProperty = input as? RemotePersonalProperty else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        
        let personalProperty = PersonalProperty(minimum: input.minimum, maximum: input.maximum)

        guard let result: T = personalProperty as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
