import Foundation
import KanguroSharedDomain
import Resolver
import KanguroSharedData

class NotificationsModuleDependencies {
    
    // MARK: - Stored Properties
    var notificationsRepository: NotificationsRepositoryProtocol?
    var askForNotificationService: AskForNotificationsUseCaseProtocol?
    
    // MARK: - Initializers
    init(notificationsRepository: NotificationsRepositoryProtocol? = nil, askForNotificationService: AskForNotificationsUseCaseProtocol? = nil) {
        self.notificationsRepository = notificationsRepository
        self.askForNotificationService = askForNotificationService
    }
}

// MARK: - ModuleDependencies
extension NotificationsModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        
        let notificationsRepository = self.notificationsRepository ?? NotificationsRepository()
        Resolver.register { notificationsRepository }

        let askForNotificationService = self.askForNotificationService ?? AskForNotifications(notificationsRepo: notificationsRepository)
        Resolver.register { askForNotificationService }
    }
}
