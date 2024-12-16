import Foundation

public final class CreateResetPassword: CreateResetPasswordUseCaseProtocol {
    
    private let resetPasswordRepo: ResetPasswordRepositoryProtocol
    
    public init(resetPasswordRepo: ResetPasswordRepositoryProtocol) {
        self.resetPasswordRepo = resetPasswordRepo
    }
    
    public func execute(parameters: ResetPasswordParameters,
                        completion: @escaping ((Result<Void, RequestError>) -> Void)) {
        resetPasswordRepo.createResetPassword(parameters: parameters) { result in
            completion(result)
        }
    }
}
