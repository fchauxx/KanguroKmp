import Foundation

public protocol GetRemindersUseCaseProtocol {
    
    func execute(completion: @escaping ((Result<[Reminder], RequestError>) -> Void))
}
