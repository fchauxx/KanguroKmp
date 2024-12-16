import Foundation

public protocol UpdateProfileUseCaseProtocol {
    
    func execute(parameters: ProfileParameters,
                 completion: @escaping ((Result<Void, RequestError>) -> Void))
}
