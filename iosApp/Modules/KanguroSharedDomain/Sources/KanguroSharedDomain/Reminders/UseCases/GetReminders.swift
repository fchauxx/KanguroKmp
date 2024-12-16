import Foundation

public final class GetReminders: GetRemindersUseCaseProtocol {
    
    private let remindersRepo: RemindersRepositoryProtocol
    
    public init(remindersRepo: RemindersRepositoryProtocol) {
        self.remindersRepo = remindersRepo
    }
    
    public func execute(completion: @escaping ((Result<[Reminder], RequestError>) -> Void)) {
        remindersRepo.getReminders { result in
            completion(result)
        }
    }
}
