import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class CoverageDetailsCoordinator: Coordinator {
    
    // MARK: - Coordinator
    override func start() {
        navigateToCoverageDetails()
    }
    
    // MARK: - Stored Properties
    var policy: PetPolicy
    var presentNavigationController: UINavigationController?
    
    // MARK: - Actions
    var didTapUpdatePetPictureAction: SimpleClosure = {}
    
    // MARK: - Initializers
    init(navigation: UINavigationController, policy: PetPolicy) {
        self.policy = policy
        super.init(navigation: navigation)
    }
}

// MARK: - NavigateToCoverage
extension CoverageDetailsCoordinator {
    
    func navigateToCoverageDetails() {
        let controller = CoverageDetailsViewController()
        let cardPosition = (navigation.previousViewController as? CardPositionProtocol)?.cardPosition
        controller.viewModel = CoverageDetailsViewModel(policy: policy)
        controller.cardPosition = cardPosition
        controller.goBackAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.popViewController(animated: true)
        }
        controller.goToBillingPreferencesAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
            self.navigateToBillingPreferences(policy: self.policy)
        }
        controller.didTapClaimsAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
            self.navigateToClaims()
        }
        controller.didTapFileClaimAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
            self.navigateToNewClaim()
        }
        controller.didTapPlanCoveredAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
            self.navigateToPlanCovered()
        }
        controller.didTapPreventiveCoveredAction = { [weak self] in
            guard let self,
                  let policyId = controller.viewModel.policy.id else { return }
            self.navigation.dismiss(animated: true)
            self.navigateToPreventiveCovered(policyId: policyId)
        }
        controller.didTapDocumentAction = { [weak self] document in
            guard let self = self,
                  let policyId = controller.viewModel.policy.id else { return }
            self.navigation.dismiss(animated: true)
            if let document = document as?KanguroSharedDomain.PolicyDocumentData {
                self.navigateToPolicyDocument(document: document, policyId: policyId)
            }
        }
        controller.didTapUpdatePetPictureAction = { [weak self] in
            guard let self else { return }
            self.didTapUpdatePetPictureAction()
        }
        controller.didTapDirectPayAction = { [weak self] pets in
            guard let self else { return }
            self.navigateToDTPBeforeGetStarted(pets: pets)
        }
        controller.didTapFAQAction = { [weak self] in
            guard let self else { return }
            self.navigateToFAQ()
        }
        navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToFAQ() {
        let controller = FAQViewController()
        controller.viewModel = InformerViewModel(types: [.FAQ])
        controller.backAction = back
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
    
    func navigateToNewClaim(_ parameters: NextStepParameters? = nil) {
        let chatbotCoordinator = CentralChatbotCoordinator(navigation: navigation,
                                                           chatbotData: ChatbotData(type: .NewClaim),
                                                           serviceType: .local)
        chatbotCoordinator.start()
    }
    
    func navigateToClaims() {
        let claimsCoordinator = ClaimsCoordinator(navigation: self.navigation)
        claimsCoordinator.start()
    }
    
    private func navigateToPlanCovered() {
        let controller = PlanCoveredViewController()
        controller.backAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        navigation.present(controller, animated: true)
    }
    
    private func navigateToPreventiveCovered(policyId: String) {
        let controller = PreventiveCoveredViewController()
        controller.viewModel = PreventiveCoveredViewModel(policyId: policyId)
        controller.goToNewClaimAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
            self.navigateToNewClaim()
        }
        controller.backAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        navigation.present(controller, animated: true)
    }
    
    private func navigateToPolicyDocument(document: KanguroSharedDomain.PolicyDocumentData, policyId: String) {
        let controller = PDFViewController()
        controller.viewModel = PolicyDocumentViewModel(cloudType:CloudType.pet,policyId: policyId, document: document)
        controller.backAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        navigation.present(controller, animated: true)
    }
}
