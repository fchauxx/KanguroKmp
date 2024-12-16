import Foundation
import KanguroSharedDomain

public final class GetLocalUser: GetUserUseCaseProtocol {

    private let localUserRepo: GetUserRepositoryProtocol
    
    public init(localUserRepo: GetUserRepositoryProtocol) {
        self.localUserRepo = localUserRepo
    }
    
    public func execute() -> Result<User,RequestError> {
        localUserRepo.getUser()
    }

    public func execute(completion: @escaping (Result<User, RequestError>) -> Void) {
        localUserRepo.getUser { result in
            completion(result)
        }
    }
}
