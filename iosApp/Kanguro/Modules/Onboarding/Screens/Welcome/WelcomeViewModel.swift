import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroStorageDomain
import KanguroSharedDomain

class WelcomeViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var userDefaults: Storage
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    
    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Computed Properties
    var currentLanguage: Language {
        guard let prefLang: String = userDefaults.get(key: "preferredLanguage") else {
            return .English
        }
        return Language(rawValue: prefLang) ?? .English
    }
    var alternativeLanguage: Language {
        currentLanguage == .English ? .Spanish : .English
    }
}

// MARK: - Analytics
extension WelcomeViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .Welcome)
    }
}

// MARK: - Public Methods
extension WelcomeViewModel {
    
    func switchLanguage() {
        userDefaults.save(value: alternativeLanguage.rawValue, key: "preferredLanguage")
    }
}
