import UIKit
import KanguroSharedDomain

extension MoreCoordinator {
    
    func navigateToSupportCause() {
        let controller = SupportCauseViewController()
        controller.viewModel = SupportCauseViewModel()
        controller.goBackAction = { [weak self] in
            guard let self else { return }
            self.navigation.dismiss(animated: true)
        }
        controller.didTapChooseToChangeCause = { [weak self] in
            guard let self else { return }
            self.navigation.dismiss(animated: true)
            self.navigateToDonationTypeCause()
        }
        navigation.present(controller, animated: true)
    }
    
    func navigateToDonationTypeCause() {
        let controller = DonationTypeViewController()
        controller.viewModel = DonationTypeViewModel()
        controller.goBackAction = { [weak self] in
            guard let self else { return }
            self.navigation.dismiss(animated: true)
        }
        controller.didTapDonationCard = { [weak self] type, donationCauses in
            guard let self else { return }
            self.navigateToDonationCause(type: type, donationCauses: donationCauses)
        }
        navController = UINavigationController(rootViewController: controller)
        guard let navController else { return }
        navController.isNavigationBarHidden = true
        navigation.present(navController, animated: true)
    }
        
    func navigateToDonationCause(type: DonationType, donationCauses: [DonationCause]) {
        let controller = DonationCauseViewController()
        controller.viewModel = DonationCauseViewModel(type: type, donationCauses: donationCauses)
        controller.goBackAction = { [weak self] in
            guard let self else { return }
            self.navController?.popViewController(animated: true)
        }
        controller.goToRootAction = { [weak self] in
            guard let self else { return }
            self.navController?.dismiss(animated: true)
        }
        controller.goNextAction = { [weak self] in
            guard let self else { return }
            if let previousViewController = self.navigation.previousViewController as? MoreViewController {
                previousViewController.refreshActionCards()
            }
            self.navController?.dismiss(animated: true)
        }
        navController?.pushViewController(controller, animated: true)
    }
}
