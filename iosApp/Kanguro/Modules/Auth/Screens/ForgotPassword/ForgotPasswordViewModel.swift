import UIKit
import Resolver
import Combine
import KanguroAnalyticsDomain
import KanguroSharedDomain

class ForgotPasswordViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var resetPasswordService: CreateResetPasswordUseCaseProtocol
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    
    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    var email: String?
    var requestError: String = ""
    
    // MARK: - Computed Properties
    var isValidData: Bool {
        return email?.isValidEmail ?? false
    }
    
    // MARK: - Initializers
    init(email: String? = nil) {
        self.email = email
    }
}

// MARK: - Analytics
extension ForgotPasswordViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .ForgotPassword)
    }
}

// MARK: - Public Methods
extension ForgotPasswordViewModel {
    
    func update(email: String) {
        self.email = email
        state = .dataChanged
    }
    
    func callSupport() {
        let supportPhoneNumber = "forgotPassword.phone".localized
        guard let url = URL(string: "tel://\(supportPhoneNumber)"),
              UIApplication.shared.canOpenURL(url) else { return }
        UIApplication.shared.open(url)
    }
}

// MARK: - Public Methods
extension ForgotPasswordViewModel {
    
    func resetPassword(email: String) {
        state = .loading
        let parameters = KanguroSharedDomain.ResetPasswordParameters(email: email)
        resetPasswordService.execute(parameters: parameters) { response in
            
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success:
                self.state = .requestSucceeded
            }
        }
    }
}
