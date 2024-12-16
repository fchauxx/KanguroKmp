import UIKit
import KanguroUserDomain

class CentralChatbotViewController: BaseViewController, ClaimsNavigationProtocol {
    
    // MARK: - Dependencies
    var viewModel: CentralChabotViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet private var navigationBackButton: NavigationBackButton!
    @IBOutlet var chatbotView: ChatbotView!
    
    // MARK: - Stored Properties
    var shouldSetupChatbot = true
    
    // MARK: Actions
    var shouldFinishChatbotAction: SimpleClosure = {}
    var backAction: SimpleClosure = {}
    var didTapClaimsAction: SimpleClosure = {}
    
    var goToOTPAction: SimpleClosure = {}
    var goToPledgeOfHonorAction: SimpleClosure = {}
    var goToBankAccountAction: SimpleClosure = {}
    var goToFeedbackAction: StringClosure = { _ in }
    var didTapFileClaimAction: SimpleClosure = {}
    var didTapFAQAction: SimpleClosure = {}
    var didTapVetAdviceAction: SimpleClosure = {}
    var didTapSupportAction: SimpleClosure = {}
    var didTapPetParentAction: SimpleClosure = {}
    var goToPreventiveCoveredAction: StringClosure = { _ in }
    var stopDuplicatedClaimbotAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension CentralChatbotViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
        observe(viewModel.$state) { [weak self] state in
            guard let self else { return }
            self.changed(state: state)
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        hideNavigationTabBar()
        if shouldSetupChatbot {
            setupChatbotViewModelType()
        }
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        showNavigationTabBar()
        if shouldSetupChatbot {
            chatbotView.hideAllInputs()
        }
    }
}

// MARK: - View State
extension CentralChatbotViewController {
    
    private func changed(state: ChatbotViewState) {
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
        default:
            break
        }
    }
}

// MARK: - Setup
extension CentralChatbotViewController {
    
    private func setupLayout() {
        setupDefaultActions()
        setupNavigationBackButton()
    }
    
    private func setupChatbotViewModelType() {

        if viewModel.data.type == .Central {
            viewModel.data.chatInteractionStep = viewModel.defaultStep
        }
        
        chatbotView.viewModel = viewModel
        chatbotView.update(stopClaimAction: shouldFinishChatbotAction)
        chatbotView.update(stopDuplicatedClaimAction: stopDuplicatedClaimbotAction)
        setupChatActions()
        chatbotView.setup()
    }
    
    private func setupNavigationBackButton() {
        navigationBackButton.update(title: "common.back".localized)
        navigationBackButton.update(action: shouldFinishChatbotAction)
    }
    
    private func setupChatActions() {
        chatbotView.didTapSignatureAction = { [weak self] in
            guard let self else { return }
            self.goToPledgeOfHonorAction()
        }
        chatbotView.didTapBankAccountAction = { [weak self] in
            guard let self else { return }
            self.goToBankAccountAction()
        }
        chatbotView.didFinishClaim = { [weak self] claimId in
            guard let self else { return }
            self.goToFeedbackAction(claimId)
        }
        chatbotView.didTapOTPAction = { [weak self] in
            guard let self else { return }
            self.goToOTPAction()
        }
        chatbotView.didTapVaccinesAndExamsAction = { [weak self] in
            guard let self,
                  let policyId = self.chatbotView.viewModel.petInfo?.policyId else { return }
            self.goToPreventiveCoveredAction(policyId)
        }
        chatbotView.requestFailedAction = { [weak self] in
            guard let self else { return }
            self.backAction()
        }
        chatbotView.didFinishPetInformationAction = { [weak self] in
            guard let self else { return }
            DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
                self.backAction()
            }
        }
        chatbotView.didTapIndexAction = { [weak self] index in
            guard let self else { return }
            
            if !self.viewModel.isValidIndex(index) { return }
            self.viewModel.actionType = DefaultActionType.init(rawValue: index)
            
            switch self.viewModel.actionType {
            case .fileClaim:
                self.didTapFileClaimAction()
            case .vetAdvice:
                self.didTapVetAdviceAction()
            case .faq:
                self.didTapFAQAction()
            case .support:
                self.didTapSupportAction()
            case .petParent:
                self.didTapPetParentAction()
            default:
                break
            }
        }
    }
    
    private func setupDefaultActions() {
        shouldFinishChatbotAction = { [weak self] in
            guard let self else { return }
            self.shouldSetupChatbot = true
            self.backAction()
        }
        didTapFileClaimAction = { [weak self] in
            guard let self else { return }
            let data = ChatbotData(type: .NewClaim)
            self.chatbotView.viewModel = ChatbotViewModel(data: data,
                                                          chatbotServiceType: viewModel.chatbotServiceType)
            self.chatbotView.setup()
        }
        didTapSupportAction = { [weak self] in
            guard let self else { return }
            self.viewModel.callSupport()
        }
    }
}

// MARK: - Protocols
extension CentralChatbotViewController: SignatureProtocol, BankAccountProtocol, PreventiveItemsProtocol, OTPValidationProtocol {
    
    func updateSignature(signature: UIImage) {
        chatbotView.updateSignature(signature: signature)
    }
    
    func updateBankAccount(account: BankAccount) {
        chatbotView.updateBankAccount(account: account)
    }
    
    func updatePreventiveItems(names: String) {
        chatbotView.updatePreventiveItems(names: names)
    }
    
    func handleSucceededOTPCode() {
        chatbotView.handleSucceededOTPCode()
    }
}
