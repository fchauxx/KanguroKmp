import Foundation
import KanguroSharedDomain

public final class UpdateLocalUser: UpdateUserUseCaseProtocol {

    private let localUserRepo: UpdateUserRepositoryProtocol

    public init(localUserRepo: UpdateUserRepositoryProtocol) {
        self.localUserRepo = localUserRepo
    }

    public func execute(_ user: User, completion: @escaping ((Result<Void, RequestError>) -> Void)) {
        localUserRepo.updateUser(user) { result in
            completion(result)
        }
    }
}
