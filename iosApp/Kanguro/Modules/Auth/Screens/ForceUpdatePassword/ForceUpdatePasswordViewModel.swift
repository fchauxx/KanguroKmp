import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroStorageDomain
import KanguroUserDomain

enum ForceChangePasswordViewState: Equatable {
    
    case started
    case loading
    case requestFailed
    case requestSucceeded
    case dataChanged
}

class ForceUpdatePasswordViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var updatePasswordService: UpdatePasswordUseCaseProtocol
    @LazyInjected var getLocalUserService: GetLocalUser
    @LazyInjected var updateLocalUserService: UpdateLocalUser
    
    // MARK: - Published Properties
    @Published var state: ForceChangePasswordViewState = .started
    
    // MARK: - Stored Properties
    var email: String
    var currentPassword: String
    var requestError: String = ""
    var newPassword: String?
    var repeatedPassword: String?
    
    // MARK: - Computed Properties
    var isValidPassword: Bool {
        guard let newPassword, let repeatedPassword else { return false }
        return newPassword.isValidPassword && newPassword == repeatedPassword
    }
    var user: User? {
        guard let user: User = try? getLocalUserService.execute().get() else { return nil }
        return user
    }
    
    // MARK: - Init
    init(email: String, currentPassword: String) {
        self.email = email
        self.currentPassword = currentPassword
    }
}

// MARK: - Analytics
extension ForceUpdatePasswordViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .ForceUpdatePassword)
    }
}

// MARK: - Public Methods
extension ForceUpdatePasswordViewModel {
    
    func update(newPassword: String) {
        self.newPassword = newPassword
        state = .dataChanged
    }
    
    func update(repeatedPassword: String) {
        self.repeatedPassword = repeatedPassword
        state = .dataChanged
    }
}

// MARK: - Network
extension ForceUpdatePasswordViewModel {
    
    func updatePassword() {
        guard let newPassword else { return }
        state = .loading
        
        let parameters = PasswordParameters(email: email,
                                            currentPassword: currentPassword,
                                            newPassword: newPassword)
        updatePasswordService.execute(parameters: parameters) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success:
                guard var user: User = try? getLocalUserService.execute().get() else {
                    assertionFailure("Could not get local user")
                    self.state = .requestFailed
                    return
                }
                user.isPasswordUpdateNeeded = false
                updateLocalUserService.execute(user) { result in

                    guard let _ = try? result.get() else {
                        assertionFailure("Could not save user locally")
                        self.state = .requestFailed
                        return
                    }
                    self.state = .requestSucceeded
                }
            }
        }
    }
}
