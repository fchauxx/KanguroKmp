import Foundation

public protocol CreateResetPasswordUseCaseProtocol {
    
    func execute(parameters: ResetPasswordParameters,
                 completion: @escaping ((Result<Void, RequestError>) -> Void))
}
