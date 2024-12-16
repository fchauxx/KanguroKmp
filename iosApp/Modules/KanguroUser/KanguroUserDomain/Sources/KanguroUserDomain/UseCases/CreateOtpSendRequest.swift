import Foundation
import KanguroSharedDomain

public final class CreateOtpSendRequest: CreateOtpSendRequestUseCaseProtocol {
    
    private let userRepo: UserRepositoryProtocol
    
    public init(userRepo: UserRepositoryProtocol) {
        self.userRepo = userRepo
    }
    
    public func execute(completion: @escaping (Result<Void, RequestError>) -> Void) {
        userRepo.createOtpSendRequest { result in
            completion(result)
        }
    }
}
