import Foundation
import KanguroSharedDomain

public final class PatchCharity: PatchCharityUseCaseProtocol {
    
    private let userRepo: UserRepositoryProtocol
    
    public init(userRepo: UserRepositoryProtocol) {
        self.userRepo = userRepo
    }
    
    public func execute(_ parameters:UserDonationCause,
                        completion: @escaping(Result<Void, RequestError>) -> Void) {
        userRepo.patchCharity(parameters) { result in
            completion(result)
        }
    }
}

