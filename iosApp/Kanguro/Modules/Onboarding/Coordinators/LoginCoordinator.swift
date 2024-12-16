import UIKit
import Resolver

class AuthCoordinator: Coordinator, CoordinatorProtocol {
    
    // MARK: - Coordinator
    
    func start() {
        let controller = LoginViewController()
        controller.viewModel = LoginViewModel()
        controller.didLoginAction = { [] in
            self.navigateToHome()
        }
        navigation.pushViewController(controller, animated: true)
    }

    private func navigateToHome() {
        let coordinator = HomeCoordinator(navigation: self.navigation)
        coordinator.start()
    }
}
