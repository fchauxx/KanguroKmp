import UIKit
import Resolver
import KanguroSharedDomain
import KanguroPetDomain

class AuthCoordinator: Coordinator {
    
    // MARK: - Stored Properties
    var homeTabBarCoordinator: HomeTabBarCoordinator
    var email: String?
    var hasAdditionalInfo: Bool = false
    var deepLinkType: DeepLink?
    
    // MARK: - Initializers
    init(navigation: UINavigationController, 
         homeTabBarCoordinator: HomeTabBarCoordinator) {
        self.homeTabBarCoordinator = homeTabBarCoordinator
        super.init(navigation: navigation)
    }
    
    // MARK: - Coordinator
    override func start() {
        navigateToLogin()
    }
}

// MARK: - NavigateToLogin
extension AuthCoordinator {
    
    private func navigateToLogin() {
        let controller = LoginViewController()
        controller.viewModel = LoginViewModel(email: email)
        controller.goBackAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        controller.goNextAction = { [weak self,
                                     weak controller] email in
            guard let self = self,
                  let controller = controller else { return }
            self.email = email
            controller.dismiss(animated: true)
            self.navigateToPassword(type: .`default`)
        }
        navigation.present(controller, animated: true)
    }
}

// MARK: - NavigateToPassword
extension AuthCoordinator {
    
    private func navigateToPassword(type: PasswordViewType) {
        guard let email = email else { return }
        let controller = PasswordViewController()
        controller.viewModel = PasswordViewModel(email: email, type: type)
        controller.goBackAction = { [weak self,
                                     weak controller] in
            guard let self = self,
                  let controller = controller else { return }
            controller.dismiss(animated: true)
            self.navigateToLogin()
        }
        controller.goToRootAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        controller.goNextAction = { [weak self] in
            guard let self = self else { return }
            switch type {
            case .`default`:
                controller.dismiss(animated: true)
                if self.deepLinkType != nil {
                    self.homeTabBarCoordinator.deepLinkType = self.deepLinkType
                    self.deepLinkType = nil
                }
                self.homeTabBarCoordinator.start()
            case .create:
                controller.dismiss(animated: true)
            }
        }
        controller.goToBlockedAccountAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
            self.navigateToBlockedUserAccount()
        }
        controller.goToForceUpdatePasswordAction = { [weak self] (email, password) in
            guard let self else { return }
            self.navigation.dismiss(animated: true)
            self.navigateToForceUpdatePassword(email: email, currentPassword: password)
        }
        controller.goToForgotPasswordAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
            self.navigateToForgotPassword(email: email)
        }
        navigation.present(controller, animated: true)
    }
}

// MARK: - NavigateToAdditionalInfo
extension AuthCoordinator {
    
    private func navigateToAdditionalInfo(pets: [Pet]) {
        let additionalInfoCoordinator = AdditionalInfoCoordinator(navigation: self.navigation, pets: pets)
        additionalInfoCoordinator.start()
    }
}

// MARK: - NavigateToBlockedUserAccount
extension AuthCoordinator {
    
    private func navigateToBlockedUserAccount() {
        let controller = BlockedAccountViewController()
        controller.viewModel = BlockedAccountViewModel()
        navigation.present(controller, animated: true)
    }
}

// MARK: - NavigateToForgotPassword
extension AuthCoordinator {
    
    private func navigateToForgotPassword(email: String) {
        let controller = ForgotPasswordViewController()
        controller.viewModel = ForgotPasswordViewModel(email: email)
        controller.goBackAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        navigation.present(controller, animated: true)
    }
}

// MARK: - NavigateToForceUpdatePassword
extension AuthCoordinator {
    
    private func navigateToForceUpdatePassword(email: String, currentPassword: String) {
        let controller = ForceUpdatePasswordViewController()
        controller.viewModel = ForceUpdatePasswordViewModel(email: email, currentPassword: currentPassword)
        controller.goToRootAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        controller.saveNewPasswordAction = { [weak self] in
            guard let self else { return }
            self.navigation.dismiss(animated: true)
            self.homeTabBarCoordinator.start()
        }
        navigation.present(controller, animated: true)
    }
}
