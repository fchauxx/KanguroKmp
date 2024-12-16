import Foundation
import KanguroSharedDomain

public final class GetIsUserAccessOk: GetIsUserAccessOkUseCaseProtocol {
    
    private let userRepo: UserRepositoryProtocol
    
    public init(userRepo: UserRepositoryProtocol) {
        self.userRepo = userRepo
    }
    
    public func execute(userId: String, completion: @escaping (Result<Void, RequestError>) -> Void) {
        userRepo.getIsUserAccessOk(userId: userId) { result in
            completion(result)
        }
    }
}
