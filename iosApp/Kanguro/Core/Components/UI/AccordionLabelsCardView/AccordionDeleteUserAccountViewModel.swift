import UIKit
import Combine
import Resolver
import KanguroSharedDomain
import KanguroStorageDomain
import KanguroUserDomain

class AccordionDeleteUserAccountViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var getLocalUserService: GetLocalUser
    @LazyInjected var deleteUserService: DeleteUserAccountUseCaseProtocol
    @LazyInjected var keychain: SecureStorage
    
    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    var email = "profile.delete.label".localized
    var requestError = ""
    
    // MARK: - Computed Properties
    var shouldDeleteCurrentUserData: Bool {
        guard let user: User = try? getLocalUserService.execute().get() else { return false }
        return user.isNeededDeleteData ?? false
    }
}

// MARK: - Public Methods
extension AccordionDeleteUserAccountViewModel {
    
    func callSupport() {
        let supportPhoneNumber = "forgotPassword.phone".localized
        guard let url = URL(string: "tel://\(supportPhoneNumber)"),
              UIApplication.shared.canOpenURL(url) else { return }
        UIApplication.shared.open(url)
    }
    
    func openEmail() {
        guard let url = URL(string: "mailto:\(email)"),
              UIApplication.shared.canOpenURL(url) else { return }
        UIApplication.shared.open(url)
    }
}

// MARK: - Public Methods
extension AccordionDeleteUserAccountViewModel {
    
    func deleteUserAccount() {
        state = .loading
        deleteUserService.execute(true) { response in
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
