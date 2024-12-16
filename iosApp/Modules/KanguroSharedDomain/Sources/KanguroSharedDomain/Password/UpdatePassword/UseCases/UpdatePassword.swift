import Foundation

public final class UpdatePassword: UpdatePasswordUseCaseProtocol {
    
    private let passwordRepo: PasswordRepositoryProtocol
    
    public init(passwordRepo: PasswordRepositoryProtocol) {
        self.passwordRepo = passwordRepo
    }
    
    public func execute(parameters: PasswordParameters,
                        completion: @escaping ((Result<Void, RequestError>) -> Void)) {
        passwordRepo.updatePassword(parameters: parameters) { result in
            completion(result)
        }
    }
}
