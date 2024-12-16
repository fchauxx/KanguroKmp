import Foundation

public protocol NotificationsRepositoryProtocol {

    func askForNotificationPermissionIfNeeded(completion: @escaping ((Result<Void, RequestError>) -> Void))
}
