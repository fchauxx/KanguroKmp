import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class PaymentSettingsCoordinator: Coordinator {
    
    // MARK: - Coordinator
    override func start() {
        navigateToPaymentSettings()
    }
    
    // MARK: - Stored Properties
    var policies: [PetPolicy]
    
    // MARK: - Initializers
    init(navigation: UINavigationController, policies: [PetPolicy]) {
        self.policies = policies
        super.init(navigation: navigation)
    }
}

// MARK: - NavigateToPaymentSettings
extension PaymentSettingsCoordinator {
    
    func navigateToPaymentSettings() {
        let controller = PaymentSettingsViewController()
        controller.viewModel = PaymentSettingsViewModel(policies: policies)
        controller.goBackAction = self.back
        controller.goToBillingPreferencesAction = { [weak self] policy in
            guard let self = self else { return }
            self.navigateToBillingPreferences(policy: policy)
        }
        controller.goToPaymentMethodAction = { [weak self] in
            guard let self = self else { return }
            self.navigateToPaymentMethods()
        }
        controller.goToReimbursementAction = { [weak self] in
            guard let self = self else { return }
            self.navigateToBankingInfo()
        }
        navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToBillingPreferences(policy: PetPolicy) {
        let controller = BillingPreferencesViewController()
        controller.viewModel = BillingPreferencesViewModel(policy: policy)
        controller.goBackAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        navigation.present(controller, animated: true)
    }
    
    private func navigateToPaymentMethods() {
        let controller = PaymentMethodViewController()
        controller.viewModel = PaymentMethodViewModel()
        controller.backAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        navigation.present(controller, animated: true)
    }
    
    private func navigateToBankingInfo() {
        let controller = BankingInfoViewController()
        controller.viewModel = BankingInfoViewModel(type: .edit)
        controller.backAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        navigation.present(controller, animated: true)
    }
}
