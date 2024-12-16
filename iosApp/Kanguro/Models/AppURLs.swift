import UIKit
import KanguroSharedDomain
import KanguroUserDomain
import Resolver

enum QuoteType {
    case general
    case renters
    case pet
}

public enum AppURLs {
    
    case kanguro
    case getPetQuote
    case getRentersQuote
    case privacyPolicy
    case instagram(username: String)
    case webInstagram(username: String)
    case appleStore
}

// MARK: - Public Properties
extension AppURLs {
    
    var url: URL? {
        switch self {
        case .kanguro:
            return URL(fileURLWithPath: "https://kanguroseguro.com")
        case .getPetQuote:
            return URL(string: AppURLs.getPetQuoteUrl)
        case .getRentersQuote:
            return URL(string: AppURLs.getRentersQuoteUrl)
        case .privacyPolicy:
            return URL(string: "https://kanguroseguro.com/privacy-policy")
        case .instagram(let username):
            return URL(string: "instagram://user?username=\(username)")
        case .webInstagram(let username):
            return URL(string: "https://instagram.com/\(username)")
        case .appleStore:
            return URL(string: "itms-apps://itunes.apple.com/app/id1625776781")
        }
    }
}

// MARK: - Private Properties
extension AppURLs {
    
    private static var infoDictionary: [String: Any] {
        return Bundle.main.infoDictionary ?? [:]
    }
    static var getPetQuoteUrl: String {
        guard let scheme = infoDictionary["HTTP_SCHEME"] as? String,
              let url = infoDictionary["GET_PET_QUOTE"] as? String else { return "" }
        
        return scheme.appending("://").appending(url)
    }
    private static var getRentersQuoteUrl: String {
        guard let scheme = infoDictionary["HTTP_SCHEME"] as? String,
              let url = infoDictionary["GET_RENTERS_QUOTE"] as? String else { return "" }
        
        return scheme.appending("://").appending(url)
    }
}

// MARK: - Public Methods
extension AppURLs {
    
    static func getProductQuoteUrl(language: Language?) -> String {
        guard let scheme = infoDictionary["HTTP_SCHEME"] as? String,
              let url = infoDictionary["GET_QUOTE"] as? String else { return "" }
        
        if let language, language == .Spanish {
            return getValidatedLanguagePath(scheme: scheme, url: url, language: language)
        } else {
            return scheme.appending("://").appending(url)
        }
    }
    
    static func blogUrl(language: Language) -> String {
        guard let scheme = infoDictionary["HTTP_SCHEME"] as? String,
              let url = infoDictionary["BLOG"] as? String else { return "" }
        
        return getValidatedLanguagePath(scheme: scheme, url: url, language: language)
    }
    
    static func getQuoteLoggedUrl(user: User, quoteType: QuoteType? = nil) -> String {
        var urlKey: String

        switch quoteType {
        case .renters:
            urlKey = "GET_RENTERS_QUOTE"
        case .pet:
            urlKey = "GET_PET_QUOTE"
        default:
            urlKey = "GET_QUOTE"
        }
        
        guard let scheme = infoDictionary["HTTP_SCHEME"] as? String,
              let url = infoDictionary[urlKey] as? String,
              let userLanguage = user.language?.rawValue else { return "" }
        
        let urlWithLanguage = url.insertTextAfterIndex(text: "\(userLanguage)/", character: "/")
        var path = scheme.appending("://").appending(urlWithLanguage)
        
        if let accessToken = user.accessToken,
           let refreshToken = user.refreshToken {
            let tokensPath = "?authToken=\(accessToken)&refreshToken=\(refreshToken)"
            path.append(tokensPath)
            return path
        }
        
        return scheme.appending("://").appending(url)
    }
    
    static func getReferFriendsTermOfService(language: Language) -> URL? {
        guard let scheme = infoDictionary["HTTP_SCHEME"] as? String,
              let url = infoDictionary["REFER_FRIENDS_TERM_SERVICE"] as? String else { return nil }
        
        return URL(string: getValidatedLanguagePath(scheme: scheme, url: url, language: language))
    }
    
    static func getPrivacyPolicy(language: Language) -> String {
        guard let scheme = infoDictionary["HTTP_SCHEME"] as? String,
              let url = infoDictionary["PRIVACY_POLICY"] as? String else { return "" }
        
        return getValidatedLanguagePath(scheme: scheme, url: url, language: language)
    }
    
    private static func getValidatedLanguagePath(scheme: String, url: String, language: Language) -> String {
        let urlWithLanguage = url.insertTextAfterIndex(text: "\(language.rawValue)/", character: "/")
        return scheme.appending("://").appending(urlWithLanguage)
    }
}
