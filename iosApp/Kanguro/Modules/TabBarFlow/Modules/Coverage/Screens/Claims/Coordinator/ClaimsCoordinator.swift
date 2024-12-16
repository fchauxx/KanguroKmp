import UIKit
import KanguroUserDomain
import SwiftUI
import KanguroPetPresentation
import KanguroSharedData
import Resolver
import KanguroNetworkDomain
import KanguroSharedDomain
import KanguroPetDomain

final class ClaimsCoordinator: Coordinator {

    @LazyInjected var network: NetworkProtocol

    // MARK: - Stored Properties
    var presentNavigationController: UINavigationController?

    // MARK: - Coordinator
    override func start() {
        navigateToClaims()
    }

    // MARK: - Initializers
    override init(
        navigation: UINavigationController
    ) {
        super.init(navigation: navigation)
    }
}

// MARK: - Environment Wrapper
struct EnvironmentWrapperView<Content: View>: View {
    let content: Content

    var body: some View {
        content.environment(\.appLanguageValue, User.getLanguage())
    }
}

// MARK: - NavigateToCoverage
extension ClaimsCoordinator {

    func navigateToClaims() {
        let trackYourClaimsViewModel = TrackYourClaimsViewModel()
        trackYourClaimsViewModel.fetchClaims()
        trackYourClaimsViewModel.analyticsLogScreen()

        let trackYourClaimView = TrackYourClaimsView(
            viewModel: trackYourClaimsViewModel,
            onBackPressed:  self.back,
            onClaimDetailPressed: { [weak self] claim in
                guard let self = self else { return }
                self.navigateToClaimDetails(claim: claim)
            },
            onDirectPayToVetPressed: { [weak self] dtpClaim in
                guard let self = self else { return }
                self.navigateToDTPAlmostDone(dtpPetClaim: dtpClaim)
            },
            onNewClaimPressed: { [weak self] in
                guard let self = self else { return }
                self.navigateToNewClaim()
            }
        )

        let wrappedView = EnvironmentWrapperView(content: trackYourClaimView)

        let hostingView = UIHostingController(rootView: wrappedView)
        navigation.pushViewController(hostingView, animated: true)
    }

    func navigateToClaimDetails(claim: PetClaim) {
        let controller = ClaimDetailsViewController()
        controller.viewModel = ClaimDetailsViewModel(claim: claim, communicationParameter: PetCommunicationParameters())
        controller.backAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        controller.goToCommunicationChatbot = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
            self.navigateToCommunicationChatbot(claimId: claim.id)
        }
        controller.didTapAttachmentAction = { [weak self] data in
            guard let self,
                  let fileData = data as? Data else { return }
            self.navigation.dismiss(animated: true)
            self.navigateToFilePreview(data: fileData)
        }
        navigation.present(controller, animated: true)
    }

    func navigateToCommunicationChatbot(claimId: String) {
        let controller = CommunicationChatbotViewController()
        controller.viewModel = CommunicationChatbotViewModel(claimId: claimId)
        controller.backAction = back
        navigation.pushViewController(controller, animated: true)
    }

    func navigateToNewClaim(_ parameters: NextStepParameters? = nil) {
        let chatbotCoordinator = CentralChatbotCoordinator(navigation: navigation,
                                                           chatbotData: ChatbotData(type: .NewClaim),
                                                           serviceType: .local)
        chatbotCoordinator.start()
    }

    func navigateToFilePreview(data: Data) {
        let controller = FilePreviewViewController()
        controller.viewModel = FilePreviewViewModel(data: data)
        navigation.present(controller, animated: true)
    }

    func navigateToDTPAlmostDone(dtpPetClaim: PetClaim) {
        let controller = AlmostDoneViewController(viewModel: AlmostDoneViewModel(dtpPetClaim: dtpPetClaim),
                                                  closeAction: { [weak self] in
            guard let self else { return }
            self.presentNavigationController?.dismiss(animated: true)
        }, okayAction: { [weak self] in
            guard let self else { return }
            self.presentNavigationController?.dismiss(animated: true)
            self.navigateToClaims()
        }, didTapSendFormYourself: { [weak self] petClaim in
            guard let self else { return }
            self.navigateToShareWithVet(dtpPetClaim: petClaim)
        })
        presentNavigationController = UINavigationController(rootViewController: controller)
        guard let presentNavigationController else { return }
        presentNavigationController.setNavigationBarHidden(true, animated: true)
        navigation.present(presentNavigationController, animated: true)
    }

    func navigateToShareWithVet(dtpPetClaim: PetClaim) {
        let controller = ShareWithVetViewController(viewModel: ShareWithVetViewModel(petClaim: dtpPetClaim),
                                                    goToRootAction: { [weak self] in
            guard let self else { return }
            self.presentNavigationController?.dismiss(animated: true)
        },
                                                    didTapDoneAction: { [weak self] in
            guard let self else { return }
            self.navigateToDoneDTP()
        })
        presentNavigationController?.pushViewController(controller, animated: true)
    }

    func navigateToDoneDTP() {
        let controller = DTPDoneViewController { [weak self] in
            guard let self else { return }
            self.presentNavigationController?.dismiss(animated: true)
        } closeAction: { [weak self] in
            guard let self else { return }
            self.presentNavigationController?.dismiss(animated: true)
        }
        presentNavigationController?.pushViewController(controller, animated: true)
    }
}
