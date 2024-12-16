import UIKit
import KanguroNetworkDomain
import KanguroSharedDomain
import KanguroPetDomain
import Resolver

class MoreCoordinator: Coordinator {

    // MARK: - Dependencies
    @LazyInjected var network: NetworkProtocol

    // MARK: - Stored Properties
    var policies: [PetPolicy]?
    var navController: UINavigationController?

    // MARK: - Computed Properties
    var appDelegate: AppDelegate? {
        UIApplication.shared.delegate as? AppDelegate
    }
    
    // MARK: - Actions
    var logoutAction: SimpleClosure
    var didTapContactUsAction: SimpleClosure
    var productType: ProductType

    // MARK: - Initializers
    init(navigation: UINavigationController, 
         policies: [PetPolicy]? = nil,
         productType: ProductType,
         logoutAction: @escaping SimpleClosure,
         didTapContactUsAction: @escaping SimpleClosure
    ) {
        self.logoutAction = logoutAction
        self.policies = policies
        self.productType = productType
        self.didTapContactUsAction = didTapContactUsAction
        super.init(navigation: navigation)
    }
    
    // MARK: - Coordinator
    override func start() {
        navigateToMore()
    }
}

// MARK: - NavigateToMore
extension MoreCoordinator {
    
    func navigateToMore() {
        let controller = MoreViewController()
        controller.viewModel = MoreViewModel(
            productType: productType
        )

        controller.logoutAction = { [weak self] in
            guard let self = self else { return }
            self.logoutAction()
        }
        controller.didTapRemindersAction = { [weak self] in
            guard let self = self else { return }
            self.navigateToReminders()
        }
        controller.didTapPaymentSettingsAction = { [weak self] in
            guard let self = self,
                  let policies = self.policies else { return }
            self.navigateToPaymentSettings(policies: policies)
        }
        controller.didTapSupportCauseAction = { [weak self] isDonating in
            guard let self else { return }
            if !isDonating {
                self.navigateToDonationTypeCause()
            } else {
                self.navigateToSupportCause()
            }
        }
        controller.didTapAppSettingsAction = { [weak self] in
            guard let self else { return }
            self.navigateToSettings()
        }
        controller.didTapContactUsAction = { [weak self] in
            guard let self else { return }
            self.didTapContactUsAction()
        }
        controller.didTapProfileAction = { [weak self] in
            guard let self else { return }
            self.navigateToProfile()
        }
        controller.didTapReferFriendButtonAction = { [weak self] in
            guard let self else { return }
            self.navigateToReferFriends()
        }
        controller.goToTermsOfServiceAction = { [weak self] in
            guard let self else { return }
            self.navigateToTermsOfService()
        }
        controller.didTapPrivacyPolicyAction = { [weak self] in
            guard let self,
                  let url = controller.viewModel.privacyPolicyURL else { return }
            self.navigateToPrivacyPolicy(url: url)
        }
        controller.didTapVetAdviceAction = { [weak self] in
            guard let self else { return }
            self.navigateToBaseVetAdvice()
        }
        controller.didTapFAQAction = { [weak self] in
            guard let self else { return }
            self.navigateToFAQ()
        }
        controller.didTapBlogAction = { [weak self] in
            guard let self,
                  let url = controller.viewModel.blogURL else { return }
            self.navigateToBlog(url: url)
        }
        controller.didTapPetParentsAction = { [weak self] in
            guard let self else { return }
            self.navigateToPetParents()
        }

        controller.didTapTalkToVet = { [weak self] in
            guard let self else { return }
            self.navigateToAirVet()
        }
        
        navigation.pushViewController(controller, animated: true)
    }
    
    func restartApp() {
        if let previousVC = navigation.previousViewController,
           let appDelegate = appDelegate {
            previousVC.view.window?.rootViewController?.dismiss(animated: true, completion: nil)
            appDelegate.setupLaunch()
        }
    }
}
