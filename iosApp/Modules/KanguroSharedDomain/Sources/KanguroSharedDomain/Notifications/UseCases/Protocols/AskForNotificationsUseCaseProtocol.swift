import Foundation

public protocol AskForNotificationsUseCaseProtocol {
    
    func execute(completion: @escaping ((Result<Void, RequestError>) -> Void))
}
