import Foundation
import KanguroSharedDomain

public enum RequestResponse<D: Codable, E: Codable & Error> {

    case success(D)
    case customError(E)
    case failure(NetworkRequestError)
}
