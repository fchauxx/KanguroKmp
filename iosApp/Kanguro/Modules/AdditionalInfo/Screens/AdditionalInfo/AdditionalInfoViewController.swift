import UIKit
import MobileCoreServices

class AdditionalInfoViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: AdditionalInfoViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet var chatbotView: ChatbotView!
    
    // MARK: - Actions
    var didTapMapCellAction: SimpleClosure = {}
    var didTapGoBackAction: SimpleClosure = {}
    var didTapDonationButtonAction: SimpleClosure = {}
    var goNextAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension AdditionalInfoViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        setupChatbotView()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
}

// MARK: - View State
extension AdditionalInfoViewController {
    
    func changed(state: AdditionalInfoViewState) {
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
        case .dataChanged:
            guard let currentPetId = viewModel.currentPet?.id else { return }
            chatbotView.viewModel.updatePet(currentPetId)
        case .finishedAllPets:
            DispatchQueue.main.asyncAfter(deadline: .now() + 3) { [weak self] in
                guard let self else { return }
                self.viewModel.isUserDonating ? self.goNextAction() : self.showDonationPopUpMenu()
            }
        }
    }
}

// MARK: - Setup
extension AdditionalInfoViewController {
    
    private func setupChatbotView() {
        guard let currentPetId = viewModel.currentPet?.id else { return }
        chatbotView.viewModel = ChatbotViewModel(data: ChatbotData(type: .AdditionalInformation,
                                                                   currentPetId: currentPetId),
                                                 chatbotServiceType: .remote)
        chatbotView.update(finishPetInformationAction: viewModel.removeCompletedPet)
        chatbotView.update(mapAction: didTapMapCellAction)
        chatbotView.setup()
    }
    
    private func showDonationPopUpMenu() {
        guard let donationVC = DonationPopUpViewController.create() as? DonationPopUpViewController,
              let keyWindow = UIApplication.shared.connectedScenes
            .compactMap({ $0 as? UIWindowScene })
            .flatMap({ $0.windows })
            .first(where: { $0.isKeyWindow }),
            let currentViewController = keyWindow.rootViewController else { return }
        let popUp = PopUpViewController(contentViewController: donationVC)
        popUp.disableSwipeToDismiss = true
        popUp.show(onViewController: currentViewController)
        donationVC.didTapDonationAction = didTapDonationButtonAction
        donationVC.goBackAction = didTapGoBackAction
    }
}
