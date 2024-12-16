import Foundation

public protocol UpdatePasswordUseCaseProtocol {
    
    func execute(parameters: PasswordParameters,
                 completion: @escaping ((Result<Void, RequestError>) -> Void))
}
