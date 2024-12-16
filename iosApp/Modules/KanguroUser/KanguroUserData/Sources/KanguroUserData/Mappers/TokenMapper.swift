import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain
import KanguroNetworkData
import KanguroUserDomain

public struct TokenMapper: ModelMapper {
    
    public typealias T = RemoteToken

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteToken = input as? RemoteToken else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        
        let token = Token(accessToken: input.accessToken,
                          expiresOn: input.expiresOn?.date,
                          refreshToken: input.refreshToken,
                          idToken: input.idToken)
        
        guard let result: T = token as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

extension TokenMapper {
    public static func reverseMap(_ input: Token) -> RemoteToken {
        let expiresOn = input.expiresOn?.isoString
        assert(expiresOn != nil, "ExpiresOn could not be mapped")

        return RemoteToken(
            accessToken: input.accessToken,
            expiresOn: expiresOn,
            refreshToken: input.refreshToken,
            idToken: input.idToken
            )
    }
}
