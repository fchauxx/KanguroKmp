import SwiftUI
import Resolver
import KanguroStorageDomain
import KanguroSharedDomain

public class OnboardingViewModel: ObservableObject {
    
    // MARK: - Dependencies
    @LazyInjected var userDefaults: Storage
    @Published var publishedLanguage: Language = .English
    
    public init () {
        self.publishedLanguage = currentLanguage
    }
    
    // MARK: - Computed Properties
    var currentLanguage: Language {
        if let prefLang: String = userDefaults.get(key: "preferredLanguage") {
            return Language(rawValue: prefLang) ?? .Spanish
        } else {
            return .Spanish
        }
    }
    var alternativeLanguage: Language {
        return currentLanguage == .English ? .Spanish : .English
    }
}

// MARK: - Public Methods
extension OnboardingViewModel {
    
    func switchLanguage() {
        publishedLanguage = alternativeLanguage
        userDefaults.save(value: alternativeLanguage.rawValue, key: "preferredLanguage")
    }
}
