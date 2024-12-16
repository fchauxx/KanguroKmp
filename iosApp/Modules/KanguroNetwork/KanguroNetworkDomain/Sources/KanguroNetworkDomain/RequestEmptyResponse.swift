import Foundation
import KanguroSharedDomain

public enum RequestEmptyResponse<E: Codable & Error> {

    case success
    case customError(E)
    case failure(NetworkRequestError)
}
