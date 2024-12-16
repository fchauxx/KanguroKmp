import UIKit
import Resolver
import KanguroSharedDomain
import KanguroPetDomain

class AdditionalInfoCoordinator: Coordinator {
    
    // MARK: - Stored Properties
    var homeTabBarCoordinator: HomeTabBarCoordinator
    var pets: [Pet]?
    var navController: UINavigationController?
    
    // MARK: - Initializers
    init(navigation: UINavigationController, pets: [Pet] = []) {
        self.pets = pets
        homeTabBarCoordinator = HomeTabBarCoordinator(navigation: navigation)
        super.init(navigation: navigation)
    }
    
    // MARK: - Coordinator
    override func start() {
        navigateToAdditionalInfo()
    }
}

// MARK: - NavigateToAdditionalInfo
extension AdditionalInfoCoordinator {
    
    private func navigateToAdditionalInfo() {
        guard let pets = pets else { return }
        let controller = AdditionalInfoViewController()
        controller.viewModel = AdditionalInfoViewModel(pets: pets)
        controller.didTapMapCellAction = { [weak self] in
            guard let self else { return }
            self.navigateToMapDetail()
        }
        controller.didTapGoBackAction =  { [weak self] in
            guard let self else { return }
            if self.navigation.previousViewController is HomeTabBarViewController {
                self.back()
            } else {
                self.navigation.dismiss(animated: true)
                self.homeTabBarCoordinator.start()
            }
        }
        controller.goNextAction = {
            if self.navigation.previousViewController is HomeTabBarViewController {
                self.back()
            } else {
                self.homeTabBarCoordinator.start()
            }
        }
        controller.didTapDonationButtonAction = { [weak self] in
            guard let self else { return }
            self.navigation.dismiss(animated: true)
            self.navigateToDonationTypeCause()
        }
        navigation.pushViewController(controller, animated: true)
    }
}

// MARK: - NavigateToMapDetail
extension AdditionalInfoCoordinator {
    
    private func navigateToMapDetail() {
        let controller = MapDetailViewController()
        controller.viewModel = MapDetailViewModel()
        controller.goBackAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        navigation.present(controller, animated: true)
    }
    
    private func navigateToDonationTypeCause() {
        let controller = DonationTypeViewController()
        controller.viewModel = DonationTypeViewModel()
        controller.goBackAction = { [weak self] in
            guard let self else { return }
            self.navigation.dismiss(animated: true)
            if !(self.navigation.previousViewController is HomeTabBarViewController) {
                self.homeTabBarCoordinator.start()
            }
        }
        controller.didTapDonationCard = { [weak self] type, donationCauses in
            guard let self else { return }
            self.navigateToDonationCause(type: type, donationCauses: donationCauses)
        }
        navController = UINavigationController(rootViewController: controller)
        guard let navController else { return }
        navController.isNavigationBarHidden = true
        navController.modalPresentationStyle = .fullScreen
        navigation.present(navController, animated: true)
    }
    
    private func navigateToDonationCause(type: DonationType, donationCauses: [DonationCause]) {
        let controller = DonationCauseViewController()
        controller.viewModel = DonationCauseViewModel(type: type, donationCauses: donationCauses)
        controller.goBackAction = { [weak self] in
            guard let self else { return }
            self.navController?.popViewController(animated: true)
        }
        controller.goToRootAction = { [weak self] in
            guard let self else { return }
            self.navigation.dismiss(animated: true)
            if !(self.navigation.previousViewController is HomeTabBarViewController) {
                self.homeTabBarCoordinator.start()
            }
        }
        controller.goNextAction = { [weak self] in
            guard let self else { return }
            self.navigation.dismiss(animated: true)
            if !(self.navigation.previousViewController is HomeTabBarViewController) {
                self.homeTabBarCoordinator.start()
            }
        }
        navController?.pushViewController(controller, animated: true)
    }
}
