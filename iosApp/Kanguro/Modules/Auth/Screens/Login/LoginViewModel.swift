import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroUserDomain

class LoginViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    
    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    var user: User?
    var email: String?
    
    // MARK: - Computed Properties
    var isValidData: Bool {
        guard let email = email else { return false }
        return email.isValidEmail
    }
    
    // MARK: - Initializers
    init(email: String? = nil) {
        self.email = email
    }
}

// MARK: - Analytics
extension LoginViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .Cellphone)
    }
}

// MARK: - Public Methods
extension LoginViewModel {
    
    func update(email: String) {
        self.email = email
        state = .dataChanged
    }
}
