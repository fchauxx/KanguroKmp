import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroStorageDomain
import KanguroUserDomain

class SettingsViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var userDefaults: Storage
    @LazyInjected var updateLanguageService: UpdateAppLanguageUseCaseProtocol
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var keychain: SecureStorage
    @LazyInjected var getLocalUserService: GetLocalUser
    @LazyInjected var updateLocalUserService: UpdateLocalUser
    
    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    var requestError = ""
    var language: Language?
    
    // MARK: - Computed Properties
    var initialLanguage: Language {
        guard let savedLanguage: String = userDefaults.get(key: "preferredLanguage") else {
            return .English
        }
        return Language(rawValue: savedLanguage) ?? .English
    }
}

// MARK: - Analytics
extension SettingsViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .Settings)
    }
}

// MARK: - Public Methods
extension SettingsViewModel {
    
    func update(language: Language) {
        guard var user: User = try? getLocalUserService.execute().get() else { return }
        user.language = language
        updateLocalUserService.execute(user) { [weak self] result in
            guard let self else { return }
            if let _ = try? result.get() {
                userDefaults.save(value: language.rawValue, key: "preferredLanguage")
                self.putLanguage(language)
                self.state = .dataChanged
                return
            }
            assertionFailure("Could not update local user")
        }
    }
}

// MARK: - Public Methods
extension SettingsViewModel {
    
    func putLanguage(_ language: Language) {
        state = .loading
        let parameters = LanguageParameters(language: language.title)
        updateLanguageService.execute(parameters: parameters) { [weak self] response in
            guard let self = self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success:
                NotificationCenter.default.post(name: .languageUpdate, object: nil, userInfo: ["language": language.rawValue])
                self.state = .requestSucceeded
            }
        }
    }
}
