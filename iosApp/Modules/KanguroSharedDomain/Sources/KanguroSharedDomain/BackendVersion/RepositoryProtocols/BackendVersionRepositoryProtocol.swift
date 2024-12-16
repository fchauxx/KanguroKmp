import Foundation

public protocol BackendVersionRepositoryProtocol {

    func getBackendVersion(maxVersion: String, completion: @escaping (Result<Void, BackendVersionError>) -> Void)
}
