import Foundation
import KanguroSharedDomain

public final class UpdateFirebaseToken: UpdateFirebaseTokenUseCaseProtocol {

    private let userRepo: UserRepositoryProtocol
    
    public init(userRepo: UserRepositoryProtocol) {
        self.userRepo = userRepo
    }
    
    public func execute(_ parameters: FirebaseTokenParameters,
                        completion: @escaping ((Result<Void,RequestError>) -> Void)) {
        userRepo.updateFirebaseToken(parameters) { result in
            completion(result)
        }
    }
}
