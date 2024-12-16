import Foundation

public protocol RemindersRepositoryProtocol {
    
    func getReminders(completion: @escaping ((Result<[Reminder], RequestError>) -> Void))
}
