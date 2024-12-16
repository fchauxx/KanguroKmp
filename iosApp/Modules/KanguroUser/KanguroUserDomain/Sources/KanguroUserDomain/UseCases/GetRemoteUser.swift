import Foundation
import KanguroSharedDomain

public final class GetRemoteUser: GetUserUseCaseProtocol {
    
    private let remoteUserRepo: GetUserRepositoryProtocol
    
    public init(remoteUserRepo: GetUserRepositoryProtocol) {
        self.remoteUserRepo = remoteUserRepo
    }
    
    public func execute(completion: @escaping (Result<User,RequestError>) -> Void) {
        remoteUserRepo.getUser { result in
            completion(result)
        }
    }
}
