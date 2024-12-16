import UIKit
import KanguroSharedDomain
import Resolver
import KanguroSharedData
import KanguroNetworkDomain
import KanguroPetDomain

class HomeTabBarCoordinator: Coordinator {
    
    // MARK: - Dependencies
    @LazyInjected var network: NetworkProtocol

    // MARK: - Stored Properties
    var onboardingCoordinator: OnboardingCoordinator?
    var deepLinkType: DeepLink?

    // MARK: - Initializers
    override init(navigation: UINavigationController) {
        super.init(navigation: navigation)
    }
    
    // MARK: - Life Cycle
    override func start() {
        navigateToTabBar()
    }
}

// MARK: - Menu
extension HomeTabBarCoordinator {
    
    func navigateToTabBar() {
        let controller = HomeTabBarFactory.makeViewController(
            nav: navigation
        )
        controller.viewModel = HomeTabBarFactory.makeViewModel()
        controller.viewModel.deepLinkType = deepLinkType
        
        controller.logoutAction = logout
        controller.goToUpdateRequiredAction = { [weak self] in
            guard let self else { return }
            self.navigateToUpdateRequired()
        }
        controller.goToBlockedUserAction = { [weak self] in
            guard let self else { return }
            self.navigateToBlockedAccount()
        }
        controller.goToAdditionalInfoAction = { [weak self] pets in
            guard let self else { return }
            self.navigateToAdditionalInfo(pets: pets)
        }
        navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToUpdateRequired() {
        let controller = UpdateRequiredViewController()
        controller.viewModel = UpdateRequiredViewModel()
        navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToBlockedAccount() {
        let controller = BlockedAccountViewController()
        controller.viewModel = BlockedAccountViewModel()
        controller.goBackAction = { [weak self] in
            guard let self else { return }
            self.logout()
        }
        navigation.present(controller, animated: true)
    }
    
    private func navigateToAdditionalInfo(pets: [Pet]) {
        let additionalInfoCoordinator = AdditionalInfoCoordinator(navigation: navigation, 
                                                                  pets: pets)
        additionalInfoCoordinator.start()
    }
    
    private func logout() {
        DispatchQueue.main.async { [weak self] in
            guard let self else { return }
            self.deepLinkType = nil
            self.onboardingCoordinator?.authCoordinator.deepLinkType = nil
            if self.navigation.popToViewController(viewControllerType: WelcomeViewController.self) == nil {
                self.navigation.popToRootViewController(animated: false)
                self.onboardingCoordinator?.navigateToWelcome()
            }
        }
    }
}

