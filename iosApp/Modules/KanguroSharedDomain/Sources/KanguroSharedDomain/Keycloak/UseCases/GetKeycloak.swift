import Foundation

public final class GetKeycloak: GetKeycloakUseCaseProtocol {

    private let keycloakRepo: KeycloakRepositoryProtocol
    
    public init(keycloakRepo: KeycloakRepositoryProtocol) {
        self.keycloakRepo = keycloakRepo
    }
    
    public func execute(completion: @escaping ((Result<KeycloakResponse, RequestError>) -> Void)) {
        keycloakRepo.getKeycloak(completion: completion)
    }
}
