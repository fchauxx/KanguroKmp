import Foundation

public protocol GetKeycloakUseCaseProtocol {
    
    func execute(completion: @escaping((Result<KeycloakResponse, RequestError>) -> Void))
}
