import Foundation

public final class GetBackendVersion: GetBackendVersionUseCaseProtocol {
    
    private let backendRepo: BackendVersionRepositoryProtocol

    public init(backendRepo: BackendVersionRepositoryProtocol) {
        self.backendRepo = backendRepo
    }

    public func execute(maxVersion: String, completion: @escaping (Result<Void, BackendVersionError>) -> Void) {
        backendRepo.getBackendVersion(maxVersion: maxVersion) { result in
            completion(result)
        }
    }
}
