import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroStorageDomain
import KanguroPetDomain

enum RemindersViewState {
    
    case started
    case loading
    case requestFailed
    case getPetsSucceeded
    case getRemindersSucceeded
}

class RemindersViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var keychain: SecureStorage
    @LazyInjected var getRemindersService: GetRemindersUseCaseProtocol
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var getPetsService: GetPetsUseCaseProtocol
    
    // MARK: - Published Properties
    @Published var state: RemindersViewState = .started

    // MARK: - Stored Properties
    var requestError = ""
    var pets: [Pet]?
    var reminders: [KanguroSharedDomain.Reminder] = []
}

// MARK: - Analytics
extension RemindersViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .Reminders)
    }
}

// MARK: - Network
extension RemindersViewModel {
    
    func getPets() {
        getPetsService.execute { [weak self] response in
            guard let self = self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let pets):
                self.pets = pets
                self.state = .getPetsSucceeded
                self.state = .getRemindersSucceeded
            }
        }
    }
    
    func getReminders() {
        state = .loading
        getRemindersService.execute { [weak self] response in
            guard let self = self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let reminders):
                self.reminders = reminders
                self.getPets()
            }
        }
    }
}
