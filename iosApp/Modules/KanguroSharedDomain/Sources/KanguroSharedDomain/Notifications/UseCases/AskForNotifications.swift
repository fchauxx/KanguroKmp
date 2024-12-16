import Foundation

public final class AskForNotifications: AskForNotificationsUseCaseProtocol {
    
    private let notificationsRepo: NotificationsRepositoryProtocol

    public init(notificationsRepo: NotificationsRepositoryProtocol) {
        self.notificationsRepo = notificationsRepo
    }

    public func execute(completion: @escaping ((Result<Void, RequestError>) -> Void)) {
        notificationsRepo.askForNotificationPermissionIfNeeded { result in
            completion(result)
        }
    }
}
