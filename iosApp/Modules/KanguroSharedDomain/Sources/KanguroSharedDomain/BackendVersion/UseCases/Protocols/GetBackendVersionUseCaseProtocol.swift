import Foundation

public protocol GetBackendVersionUseCaseProtocol {
    
    func execute(maxVersion: String, completion: @escaping (Result<Void, BackendVersionError>) -> Void)
}
