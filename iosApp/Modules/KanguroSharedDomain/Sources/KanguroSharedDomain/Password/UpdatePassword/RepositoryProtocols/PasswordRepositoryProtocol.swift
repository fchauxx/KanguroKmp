import Foundation

public protocol PasswordRepositoryProtocol {
    
    func updatePassword(parameters: PasswordParameters,
                        completion: @escaping ((Result<Void, RequestError>) -> Void))
}
