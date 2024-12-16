import UIKit
import SwiftUI
import Resolver
import KanguroNetworkDomain
import KanguroRentersDomain
import KanguroPetDomain
import KanguroSharedDomain

extension DashboardCoordinator {
    
    func navigateToFAQ(types: [InformerDataType]) {
        let controller = FAQViewController()
        controller.viewModel = InformerViewModel(types: types)
        controller.backAction = back
        navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToHomeFAQ() {
        let network: NetworkProtocol = Resolver.resolve()
        let view = RentersNavigationViewFactory.make(
            page: .homeFAQ,
            network: network,
            navigation: navigation
        )
        let hostingView = UIHostingController(rootView: view)
        navigation.pushViewController(hostingView, animated: true)
        
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
    
    func navigateToPaymentSettings(policies: [PetPolicy]) {
        let coordinator = PaymentSettingsCoordinator(navigation: self.navigation, policies: policies)
        coordinator.start()
    }
    
    func navigateToBlog(url: URL) {
        let controller = WebviewViewController(url: url)
        navigation.present(controller, animated: true)
    }
    
    func navigateToVetAdvice(type: VetAdviceType) {
        let controller = VetAdviceViewController(type: type)
        controller.viewModel = InformerViewModel(types: [.FAQ])
        controller.backAction = back
        navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToPetParents() {
        let controller = PetParentsViewController()
        controller.viewModel = InformerViewModel(types: [.FAQ])
        controller.backAction = back
        navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToCoverageDetails(policy: PetPolicy) {
        coverageDetailsCoordinator = CoverageDetailsCoordinator(navigation: self.navigation, policy: policy)
        coverageDetailsCoordinator?.start()
        
        coverageDetailsCoordinator?.didTapUpdatePetPictureAction = { [weak self] in
            guard let self else { return }
            self.didTapUpdatePetPictureAction()
        }
    }
    
    func navigateToGetAQuote(url: URL) {
        let controller = WebviewViewController(url: url)
        navigation.present(controller, animated: true)
    }
    
    func navigateToRentersCoverageDetails(policy: RenterPolicy) {
        let view = RentersNavigationViewFactory.make(page: .coverageDetails(policy: policy),
                                                     network: network,
                                                     navigation: navigation)
        let hostingView = UIHostingController(rootView: view)
        navigation.pushViewController(hostingView, animated: true)
    }
    
    func navigateToReminders() {
        let controller = RemindersViewController()
        controller.viewModel = RemindersViewModel()
        controller.didTapCloseButtonAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        controller.didTapMedicalHistoryCardAction = { [weak self] pet in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
            self.navigateToMedicalHistoryChatbot(petId: pet.id)
        }
        controller.didTapCommunicationCardAction = { [weak self] claimId in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
            self.navigateToCommunicationChatbot(claimId: claimId)
        }
        navigation.present(controller, animated: true)
    }
    
    func navigateToAirVet() {
        let view = RentersNavigationViewFactory.make(page: .airvetInstruction, network: network)
        let hostingView = UIHostingController(rootView: view)
        navigation.present(hostingView, animated: true)
    }

    func navigateToNewClaim(_ parameters: NextStepParameters? = nil) {
        let chatbotCoordinator = CentralChatbotCoordinator(navigation: navigation,
                                                           chatbotData: ChatbotData(type: .NewClaim),
                                                           serviceType: .local)
        chatbotCoordinator.start()
    }
    
    func navigateToClaims() {
        let claimsCoordinator = ClaimsCoordinator(navigation: navigation)
        claimsCoordinator.start()
    }
    
    func navigateToCloud() {
        let cloudCoordinator = CloudCoordinator(navigation: navigation)
        cloudCoordinator.start()
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
        //TODO: Erase after all users have phone number
        controller.notificateDisappearingAction = { [weak self] in
            guard let self = self else { return }
            let rootVC = self.navigation.viewControllers.first
            if rootVC is DashboardViewController {
                rootVC?.viewWillAppear(true)
            }
        }
        navigation.present(controller, animated: true)
    }
    
    func navigateToReferFriendTermsOfService(url: URL) {
        let controller = WebviewViewController(url: url)
        navigation.present(controller, animated: true)
    }
    
    func navigateToMedicalHistoryChatbot(petId: Int) {
        let chatbotCoordinator = CentralChatbotCoordinator(navigation: navigation,
                                                           chatbotData: ChatbotData(type: .PetMedicalHistoryDocuments,
                                                                                    currentPetId: petId),
                                                           serviceType: .remote)
        chatbotCoordinator.start()
    }
    
    func navigateToCommunicationChatbot(claimId: String) {
        let controller = CommunicationChatbotViewController()
        controller.viewModel = CommunicationChatbotViewModel(claimId: claimId)
        controller.backAction = back
        navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToMapDetail() {
        let controller = MapDetailViewController()
        controller.viewModel = MapDetailViewModel()
        controller.goBackAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        navigation.present(controller, animated: true)
    }
    
    func restartApp() {
        if let previousVC = navigation.previousViewController,
           let appDelegate = appDelegate {
            previousVC.view.window?.rootViewController?.dismiss(animated: true, completion: nil)
            appDelegate.setupLaunch()
        }
    }
    
    func navigateToPartnerWebpage(_ url: String) {
        if let partnerURL = URL(string: url) {
            UIApplication.shared.open(partnerURL)
        }
    }
}
