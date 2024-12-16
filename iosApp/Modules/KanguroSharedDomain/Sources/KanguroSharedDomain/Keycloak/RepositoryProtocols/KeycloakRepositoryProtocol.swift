import Foundation

public protocol KeycloakRepositoryProtocol {

    func getKeycloak(completion: @escaping ((Result<KeycloakResponse, RequestError>) -> Void))
}
