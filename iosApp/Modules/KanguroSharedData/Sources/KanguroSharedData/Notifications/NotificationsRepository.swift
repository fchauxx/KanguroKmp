import Foundation
import UIKit
import UserNotifications
import KanguroSharedDomain

public class NotificationsRepository: NotificationsRepositoryProtocol {
    
    public init() {}
    
    public func askForNotificationPermissionIfNeeded(completion: @escaping ((Result<Void, RequestError>) -> Void)) {
        let authOptions: UNAuthorizationOptions = [.alert, .badge, .sound]
        UNUserNotificationCenter.current().requestAuthorization(options: authOptions,
                                                                completionHandler: { success, error in
            if success {
                DispatchQueue.main.async {
                    UIApplication.shared.registerForRemoteNotifications()
                }
                completion(.success(()))
            } else {
                let error = error as? RequestError
                completion(.failure(error ?? RequestError(errorType: .notAllowed, 
                                                          errorMessage: "Notifications not allowed")))
            }
        })
    }
}
