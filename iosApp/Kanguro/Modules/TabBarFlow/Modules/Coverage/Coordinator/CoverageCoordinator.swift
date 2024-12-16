import UIKit
import KanguroSharedDomain
import KanguroSharedData
import KanguroPetDomain

class CoverageCoordinator: Coordinator {

    // MARK: - Coordinator
    override func start() {
        navigateToCoverage()
    }
    
    // MARK: - Stored Properties
    var policies: [PetPolicy]
    var coverageDetailsCoordinator: CoverageDetailsCoordinator?
    
    // MARK: - Actions
    var didTapFileClaimAction: NextStepClosure = { _ in }
    var didTapUpdatePetPictureAction: SimpleClosure = {}
    private var blockedAction: SimpleClosure 
    
    // MARK: - Initializers
    init(
        navigation: UINavigationController,
        policies: [PetPolicy],
        blockedAction: @escaping SimpleClosure
    ) {
        self.policies = policies
        self.blockedAction = blockedAction
        super.init(navigation: navigation)
    }
}

// MARK: - NavigateToCoverage
extension CoverageCoordinator {
    
    func navigateToCoverage() {
        let controller = CoverageViewController()
        controller.viewModel = CoverageViewModel(policies: policies)
        controller.blockedAction = blockedAction
        controller.didTapClaimsAction = { [weak self] in
            guard let self = self else { return }
            self.navigateToClaims()
        }
        controller.didTapFileClaimAction = { [weak self] in
            guard let self = self else { return }
            self.navigateToNewClaim()
        }
        controller.didTapCardAction = { [weak self] policy in
            guard let self = self else { return }
            self.navigateToCoverageDetails(policy: policy)
        }
        controller.didTapPaymentSettingsAction = { [weak self] in
            guard let self = self else { return }
            self.navigateToPaymentSettings(policies: self.policies)
        }
        controller.didTapFAQAction = { [weak self] in
            guard let self = self else { return }
            self.navigateToFAQ()
        }
        controller.didTapGetAQuoteAction = { [weak self] in
            guard let self,
                  let url = controller.viewModel.getQuoteUrl else { return }
            self.navigateToGetAQuote(url: url)
        }
        navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToClaims() {
        let claimsCoordinator = ClaimsCoordinator(navigation: self.navigation)
        claimsCoordinator.start()
    }
    
    private func navigateToCoverageDetails(policy: PetPolicy) {
        coverageDetailsCoordinator = CoverageDetailsCoordinator(navigation: self.navigation, policy: policy)
        coverageDetailsCoordinator?.start()
        
        coverageDetailsCoordinator?.didTapUpdatePetPictureAction = { [weak self] in
            guard let self else { return }
            self.didTapUpdatePetPictureAction()
        }
    }
    
    func navigateToPaymentSettings(policies: [PetPolicy]) {
        let coordinator = PaymentSettingsCoordinator(navigation: self.navigation, policies: policies)
        coordinator.start()
    }
    
    func navigateToFAQ() {
        let controller = FAQViewController()
        controller.viewModel = InformerViewModel(types: [.FAQ])
        controller.backAction = back
        navigation.pushViewController(controller, animated: true)
    }
    
    private func navigateToGetAQuote(url: URL) {
        let controller = WebviewViewController(url: url)
        navigation.present(controller, animated: true)
    }
    
    func navigateToNewClaim(_ parameters: NextStepParameters? = nil) {
        let chatbotCoordinator = CentralChatbotCoordinator(navigation: navigation,
                                                           chatbotData: ChatbotData(type: .NewClaim),
                                                           serviceType: .local)
        chatbotCoordinator.start()
    }
}
