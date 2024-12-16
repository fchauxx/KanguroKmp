import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain

class PaymentMethodViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var getKeycloakService: GetKeycloakUseCaseProtocol
    @LazyInjected var environment: EnvironmentProtocol
    
    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    var keycloak: KeycloakResponse?
    var requestError = ""
    
    // MARK: - Computed Properties
    var accessToken: String? {
        return keycloak?.access_token ?? nil
    }
    var htmlFile: String? {
        return Bundle.main.path(forResource: "payment_method", ofType: "html") ?? nil
    }
    var htmlString: String? {
        guard let accessToken = accessToken,
              let htmlFile = htmlFile,
              let htmlString = try? String(contentsOfFile: htmlFile, encoding: String.Encoding.utf8) else { return nil }
        
        var finalHtml = htmlString.replacingOccurrences(of: "%s", with: accessToken)
        finalHtml = finalHtml.replacingOccurrences(of: "sandbox", with: environment.cloudInsuranceTarget)
        
        return finalHtml
    }
}

// MARK: - Analytics
extension PaymentMethodViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .PaymentMethod)
    }
}

// MARK: Network
extension PaymentMethodViewModel {
    
    func getKeycloak() {
        state = .loading
        getKeycloakService.execute { [weak self] response in
            guard let self = self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let keycloak):
                self.keycloak = keycloak
                self.state = .requestSucceeded
            }
        }
    }
}
