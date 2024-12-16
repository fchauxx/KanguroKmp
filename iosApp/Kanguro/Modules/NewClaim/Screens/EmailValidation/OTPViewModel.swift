import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroStorageDomain
import KanguroUserDomain

enum OTPViewState {
    
    case started
    case loading
    case dataChanged
    case sendMessageSucceeded
    case sendMessageFailed
    case loadingCodeValidation
    case codeValidationSucceeded
    case codeValidationFailed
    case requestFailed
}

class OTPViewModel {
    
    // MARK: - Dependecies
    @LazyInjected var getOtpValidationService: GetOtpValidationUseCaseProtocol
    @LazyInjected var createOtpService: CreateOtpSendRequestUseCaseProtocol
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var keychain: SecureStorage
    @LazyInjected var getLocalUserService: GetLocalUser
    
    // MARK: - Published Properties
    @Published var state: OTPViewState = .started
    
    // MARK: - Stored Properties
    var error = ""
    
    // MARK: - Computed Properties
    var userEmail: String? {
        guard let user: User = try? getLocalUserService.execute().get() else { return nil }
        return user.email?.escaped
    }
    var isValidData: Bool {
        return false
    }
}

// MARK: - Analytics
extension OTPViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .EmailValidation)
    }
}

// MARK: - Service
extension OTPViewModel {
    
    func sendCode() {
        state = .loading
        createOtpService.execute { response in
            switch response {
            case .failure(_):
                self.error = "serverError.default".localized
                self.state = .sendMessageFailed
            case .success:
                self.state = .sendMessageSucceeded
            }
        }
    }
    
    func getCodeValidation(code: String) {
        guard let userEmail else { return }
        state = .loadingCodeValidation
        let parameters = CodeValidationDataParameters(email: userEmail, code: code)
        getOtpValidationService.execute(parameters) { [weak self] response in
            guard let self else { return }
            switch response {
            case .success:
                self.state = .codeValidationSucceeded
            case .failure(let error):
                if error.errorType == .notAllowed {
                    self.state = .codeValidationFailed
                    self.error = error.errorMessage ?? "serverError.default".localized
                } else {
                    self.error = "serverError.default".localized
                    self.state = .requestFailed
                }
            }
        }
    }
}
