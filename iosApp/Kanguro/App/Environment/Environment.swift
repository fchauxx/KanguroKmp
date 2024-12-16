import Foundation

class Environment: EnvironmentProtocol {
    
    var infoDictionary: [String: Any] {
        return Bundle.main.infoDictionary ?? [:]
    }
    
    var baseURL: String {
        guard let scheme = infoDictionary["HTTP_SCHEME"] as? String,
              let url = infoDictionary["BASE_URL"] as? String else { return "" }

        return scheme.appending("://").appending(url)
    }
    
    var apiKey: String {
        return infoDictionary["API_KEY"] as? String ?? ""
    }
    
    var target: String {
        return infoDictionary["ENVIRONMENT"] as? String ?? ""
    }
    
    var cloudInsuranceTarget: String {
        return infoDictionary["CLOUD_INSURANCE_ENVIRONMENT"] as? String ?? ""
    }
    
    var compatibleBackendVersion: String {
        return infoDictionary["COMPATIBLE_BACKEND_VERSION"] as? String ?? ""
    }
    
    var sentryEnv: String {
        return infoDictionary["SENTRY_ENV"] as? String ?? ""
    }
    
    var sentryDsn: String {
        return infoDictionary["SENTRY_DSN"] as? String ?? ""
    }
    
    var airvetPartnerId: String {
        return infoDictionary["AIRVET_PARTNER_ID"] as? String ?? ""
    }

    var airvetURL: String {
        guard let scheme = infoDictionary["HTTP_SCHEME"] as? String,
              let airvetUrl = infoDictionary["AIRVET_URL"] as? String else { return "" }
        return scheme.appending("://").appending(airvetUrl)
    }
    
}
