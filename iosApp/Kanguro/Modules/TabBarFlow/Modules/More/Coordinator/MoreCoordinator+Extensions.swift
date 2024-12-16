import UIKit
import SwiftUI
import KanguroPetDomain

extension MoreCoordinator {
    
    func navigateToFAQ() {
        let controller = FAQViewController()
        controller.viewModel = InformerViewModel(types: [.FAQ])
        controller.backAction = back
        navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToBlog(url: URL) {
        let controller = WebviewViewController(url: url)
        navigation.present(controller, animated: true)
    }
    
    func navigateToBaseVetAdvice() {
        let controller = VetAdviceBaseViewController()
        controller.backAction = back
        controller.didTapVetAdviceCardAction = { [weak self] type in
            guard let self = self else { return }
            self.navigateToVetAdvice(type: type)
        }
        navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToVetAdvice(type: VetAdviceType) {
        let controller = VetAdviceViewController(type: type)
        controller.viewModel = InformerViewModel(types: [.VetAdvice])
        controller.backAction = back
        navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToPetParents() {
        let controller = PetParentsViewController()
        controller.viewModel = InformerViewModel(types: [.NewPetParent])
        controller.backAction = back
        navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToReminders() {
        let controller = RemindersViewController()
        controller.viewModel = RemindersViewModel()
        controller.didTapCloseButtonAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        navigation.present(controller, animated: true)
    }
    
    func navigateToPaymentSettings(policies: [PetPolicy]) {
        let coordinator = PaymentSettingsCoordinator(navigation: self.navigation, policies: policies)
        coordinator.start()
    }
    

    func navigateToSettings() {
        let controller = SettingsViewController()
        controller.viewModel = SettingsViewModel()
        controller.goBackAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        controller.restartAppAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
            self.restartApp()
        }
        navigation.present(controller, animated: true)
    }
    
    func navigateToProfile() {
        let controller = ProfileViewController()
        controller.viewModel = ProfileViewModel()
        controller.goBackAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        controller.logoutAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
            self.logoutAction()
        }
        navigation.present(controller, animated: true)
    }
    
    func navigateToReferFriends() {
        let controller = ReferFriendsViewController()
        controller.viewModel = ReferFriendsViewModel()
        controller.didTapTermsOfServiceAction = { [weak self] in
            guard let self,
                  let url = controller.viewModel.getTermOfService else { return }
            self.navigation.dismiss(animated: true)
            self.navigateToReferFriendTermsOfService(url: url)
        }
        controller.goBackAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        navigation.present(controller, animated: true)
    }
    
    func navigateToReferFriendTermsOfService(url: URL) {
        let controller = WebviewViewController(url: url)
        navigation.present(controller, animated: true)
    }
    
    func navigateToTermsOfService() {
        let controller = PDFViewController()
        controller.viewModel = TermsOfServiceViewModel()
        controller.backAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        navigation.present(controller, animated: true)
    }
    
    func navigateToPrivacyPolicy(url: URL) {
        let controller = WebviewViewController(url: url)
        navigation.present(controller, animated: true)
    }

    func navigateToAirVet() {
        let view = PetNavigationViewFactory.make(page: .airvetInstruction, network: network)
        let hostingView = UIHostingController(rootView: view)
        navigation.present(hostingView, animated: true)
    }
}
