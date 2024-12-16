import Foundation
import KanguroSharedDomain
import KanguroUserDomain
import KanguroStorageDomain

enum KeychainStorageKey: String {
    case user
}

public class LocalUserRepository: GetUserRepositoryProtocol  {

    private let storage: SecureStorage

    public init(storage: SecureStorage) {
        self.storage = storage
    }

    public func getUser(completion: @escaping (Result<User, RequestError>) -> Void) {
        let remoteUser: RemoteUser? = storage.get(key: KeychainStorageKey.user.rawValue)
        guard let remoteUser else {
            completion(.failure(RequestError(errorType: .notFound, 
                                             errorMessage: "No user found")))
            return
        }
        guard let user: User = try? UserMapper.map(remoteUser) else {
            completion(.failure(RequestError(errorType: .couldNotMap, 
                                             errorMessage: "Could not map")))
            return
        }
        completion(.success(user))
    }

    public func getUser() -> Result<User, RequestError> {
        let remoteUser: RemoteUser? = storage.get(key: KeychainStorageKey.user.rawValue)
        guard let remoteUser else {
            return .failure(RequestError(errorType: .notFound, 
                                         errorMessage: "No user found"))
        }
        guard let user: User = try? UserMapper.map(remoteUser) else {
            return .failure(RequestError(errorType: .couldNotMap, 
                                         errorMessage: "Could not map"))
        }
        return .success(user)
    }
}

extension LocalUserRepository: UpdateUserRepositoryProtocol {

    public func updateUser(_ user: User, 
                           completion: @escaping (Result<Void, RequestError>) -> Void) {
        guard let remoteUser = try? UserMapper.reverseMap(user) else {
            completion(.failure(RequestError(errorType: .couldNotMap, 
                                             errorMessage: "Could not map")))
            return
        }
        storage.save(value: remoteUser, key: KeychainStorageKey.user.rawValue)
        completion(.success(()))
    }
}
