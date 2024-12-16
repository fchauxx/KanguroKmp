import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroStorageDomain
import KanguroUserDomain

enum PasswordViewType {
    case `default`
    case create
}

enum PasswordViewState: Equatable {
    
    case started
    case dataChanged
    case loading
    case loginFailed(error: RequestError)
    case loginSucceeded
    case passwordUpdateRequired
    case setLanguageFailed
    case setLanguageSucceeded
}

class PasswordViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var loginService: LoginUseCaseProtocol
    @LazyInjected var updateLanguageService: UpdateAppLanguageUseCaseProtocol
    @LazyInjected var userDefaults: Storage
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var getLocalUserService: GetLocalUser
    @LazyInjected var updateLocalUserService: UpdateLocalUser
    @LazyInjected var secureStorage: SecureStorage
    
    // MARK: - Published Properties
    @Published var state: PasswordViewState = .started
    
    // MARK: - Stored Properties
    var type: PasswordViewType
    var user: User?
    var email: String
    var password: String?
    var requestError: String = ""
    var requestsCounter: Int = 0
    
    // MARK: - Computed Properties
    var isValidData: Bool {
        guard let password = password else { return false }
        if type == .default {
            return email.isValidEmail && password.isValidPassword
        } else {
            return email.isValidEmail && password.isValidCreatedPassword
        }
    }
    
    // MARK: - Initializers
    init(email: String, type: PasswordViewType = .`default`) {
        self.email = email
        self.type = type
    }
}

// MARK: - Analytics
extension PasswordViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .LoginPassword)
    }
}

// MARK: - Public Methods
extension PasswordViewModel {
    
    func update(password: String) {
        self.password = password
        state = .dataChanged
    }
}

// MARK: - Network
extension PasswordViewModel {
    
    func login() {
        secureStorage.cleanAll()
        guard let password = password else { return }
        state = .loading
        let parameters = UserLoginParameters(email: email, password: password)
        
        loginService.execute(parameters) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .loginFailed(error: error)
            case .success(let user):
                self.updateLocalUserService.execute(user) { result in
                    guard let _ = try? result.get() else {
                        assertionFailure("Could not save user")
                        self.requestError = "serverError.default".localized
                        return
                    }
                    self.user = user
                    let isTempPassword = user.isPasswordUpdateNeeded ?? false
                    self.state = isTempPassword ? .passwordUpdateRequired : .loginSucceeded
                }
            }
        }
    }

    func setLanguage(_ language: Language? = nil) {
        if let language {
            setUserLanguage(language)
            return
        }
        guard let langRaw: String = userDefaults.get(key: "preferredLanguage"), let newLang = Language(rawValue: langRaw) else {
            return
        }
        setUserLanguage(newLang)
    }
    
    private func setUserLanguage(_ language: Language) {
        let parameters = LanguageParameters(language: language.title)
        updateLanguageService.execute(parameters: parameters) { [weak self] response in
            guard let self = self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .setLanguageFailed
            case .success:
                guard var user: User = try? getLocalUserService.execute().get() else { return }
                user.language = language
                updateLocalUserService.execute(user) { result in
                    guard let _ = try? result.get() else {
                        assertionFailure("Could not save user locally")
                        self.state = .setLanguageFailed
                        return
                    }
                    self.state = .setLanguageSucceeded
                }
            }
        }
    }
}
