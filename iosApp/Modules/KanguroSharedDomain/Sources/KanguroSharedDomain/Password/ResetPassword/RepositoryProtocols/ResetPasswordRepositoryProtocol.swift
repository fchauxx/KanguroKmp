import Foundation

public protocol ResetPasswordRepositoryProtocol {
    
    func createResetPassword(parameters: ResetPasswordParameters,
                             completion: @escaping ((Result<Void, RequestError>) -> Void))
}
