import Foundation
import KanguroUserDomain
import SwiftUI
import KanguroPetPresentation
import UIKit
import KanguroPetDomain

final class CentralChatbotCoordinator: Coordinator {

    // MARK: - Stored Properties
    var chatbotData: ChatbotData
    var serviceType: ChatbotServiceType

    // MARK: - Actions
    var didTapGoToDashboardAction: SimpleClosure = {}

    // MARK: - Constructor
    init(navigation: UINavigationController,
         chatbotData: ChatbotData,
         serviceType: ChatbotServiceType) {
        self.chatbotData = chatbotData
        self.serviceType = serviceType
        super.init(navigation: navigation)
    }

    // MARK: - Coordinator
    override func start() {
        navigateToCentralChatbot()
    }
}

// MARK: - NavigateToChatbot
extension CentralChatbotCoordinator {
    
    func navigateToCentralChatbot() {
        let controller = CentralChatbotViewController()
        controller.viewModel = CentralChabotViewModel(data: chatbotData,
                                                      chatbotServiceType: serviceType)
        controller.backAction = back
        controller.didTapClaimsAction = { [weak self] in
            guard let self else { return }
            self.navigateToClaims()
        }
        controller.goToPledgeOfHonorAction = { [weak self] in
            guard let self else { return }
            self.navigateToPledgeOfHonor()
        }
        controller.goToFeedbackAction = { [weak self] claimId in
            guard let self else { return }
            self.navigateToFeedback(claimId: claimId)
        }
        controller.goToBankAccountAction = { [weak self] in
            guard let self else { return }
            self.navigateToBankingInfo()
        }
        controller.didTapVetAdviceAction = { [weak self] in
            guard let self else { return }
            self.navigateToBaseVetAdvice()
        }
        controller.didTapFAQAction = { [weak self] in
            guard let self else { return }
            self.navigateToFAQ()
        }
        controller.didTapPetParentAction = { [weak self] in
            guard let self else { return }
            self.navigateToPetParents()
        }
        controller.goToPreventiveCoveredAction = { [weak self] policyId in
            guard let self else { return }
            self.navigateToPreventiveCovered(policyId: policyId)
        }
        controller.goToOTPAction = { [weak self] in
            guard let self else { return }
            self.navigateToOTP()
        }
        controller.stopDuplicatedClaimbotAction = { [weak self] in
                    guard let self = self else { return }
                    let rootVC = self.navigation.viewControllers.first as? ClaimsNavigationProtocol
                    self.backToRoot()
                    rootVC?.didTapClaimsAction()
                }
        navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToPledgeOfHonor() {
        let controller = PledgeOfHonorViewController(type: .chatbot)
        controller.viewModel = PledgeOfHonorViewModel()
        controller.goBackAction = { [weak self] in
            guard let self else { return }
            self.navigation.dismiss(animated: true)
        }
        controller.didSaveSignatureAction = { [weak self] signature in
            guard let self,
                  let signatureVC = (self.navigation.previousViewController as? SignatureProtocol) else { return }
            signatureVC.updateSignature(signature: signature)
            self.navigation.dismiss(animated: true)
        }
        navigation.present(controller, animated: true)
    }
    
    func navigateToBankingInfo() {
        let controller = BankingInfoViewController()
        controller.viewModel = BankingInfoViewModel(type: .chatbot)
        controller.backAction = { [weak self] in
            guard let self else { return }
            self.navigation.dismiss(animated: true)
        }
        controller.didSaveBankAccountAction = { [weak self] account in
            guard let self,
                  let accountVC = (self.navigation.previousViewController as? BankAccountProtocol) else { return }
            accountVC.updateBankAccount(account: account)
            self.navigation.dismiss(animated: true)
        }
        navigation.present(controller, animated: true)
    }
    
    func navigateToFeedback(claimId: String) {
        let onFinish: () -> Void = { [weak self] in
            guard let self else { return }
            let rootVC = self.navigation.viewControllers.first as? ClaimsNavigationProtocol
            self.backToRoot()
            rootVC?.didTapClaimsAction()
            self.navigation.dismiss(animated: true)
        }
        
        let viewModel = PetFeedbackViewModel(claimId: claimId, onFinish: onFinish)
        let swiftUIView = PetFeedbackView(viewModel: viewModel)
            .environment(\.appLanguageValue, User.getLanguage())
        let controller = UIHostingController(rootView: swiftUIView)
        controller.modalPresentationStyle = .overFullScreen
        navigation.present(controller, animated: true)
    }
    
    func navigateToClaims() {
        let claimsCoordinator = ClaimsCoordinator(navigation: self.navigation)
        claimsCoordinator.start()
    }
    
    func navigateToFAQ() {
        let controller = FAQViewController()
        controller.viewModel = InformerViewModel(types: [.FAQ])
        controller.backAction = back
        navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToBaseVetAdvice() {
        let controller = VetAdviceBaseViewController()
        controller.backAction = back
        controller.didTapVetAdviceCardAction = { [weak self] type in
            guard let self else { return }
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
    
    func navigateToPreventiveCovered(policyId: String) {
        let controller = PreventiveCoveredViewController()
        controller.viewModel = PreventiveCoveredViewModel(policyId: policyId, type: .editable)
        controller.didFinishPreventiveItemsAction = { [weak self] varNames in
            guard let self,
                  let centralChatbotVC = (self.navigation.previousViewController as? PreventiveItemsProtocol) else { return }
            centralChatbotVC.updatePreventiveItems(names: varNames)
            self.navigation.dismiss(animated: true)
        }
        controller.backAction = { [weak self] in
            guard let self else { return }
            self.navigation.dismiss(animated: true)
        }
        navigation.present(controller, animated: true)
    }
    
    func navigateToOTP() {
        let controller = OTPViewController()
        controller.viewModel = OTPViewModel()
        controller.didCodeSucceedAction = { [weak self] in
            guard let self,
                  let newClaimVC = (self.navigation.previousViewController as? OTPValidationProtocol) else { return }
            newClaimVC.handleSucceededOTPCode()
            self.navigation.dismiss(animated: true)
        }
        controller.backAction = { [weak self] in
            guard let self else { return }
            self.navigation.dismiss(animated: true)
        }
        navigation.present(controller, animated: true)
    }
}
