import UIKit

class CommunicationChatbotViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: CommunicationChatbotViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet var chatbotView: ChatbotView!
    @IBOutlet var navigationBackButton: NavigationBackButton!
    
    // MARK: - Actions
    var backAction: SimpleClosure = {}
    var goNextAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension CommunicationChatbotViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        hideNavigationTabBar()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        showNavigationTabBar()
    }
}

// MARK: - View State
extension CommunicationChatbotViewController {
    
    func changed(state: DefaultViewState) {
        
        hideLoadingView()
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            viewModel.getCommunications()
        case .loading:
            showLoadingView(shouldAnimate: false)
        case .requestFailed:
            showActionAlert(message: viewModel.requestError,
                            action: backAction)
        case .dataChanged:
            updateChatbotData()
        case .requestSucceeded:
            showActionAlert(message: "serverSuccess.default".localized,
                            action: backAction)
        }
    }
}

// MARK: - Setup
extension CommunicationChatbotViewController {
    
    private func setupLayout() {
        setupNavigationBackButton()
        setupChatbotView()
    }
    
    func setupNavigationBackButton() {
        navigationBackButton.update(title: "common.back".localized)
        navigationBackButton.update(action: backAction)
    }
    
    func setupChatbotView() {
        chatbotView.viewModel = ChatbotViewModel(data: ChatbotData(type: .Communication),
                                                 chatbotServiceType: .remote)
        chatbotView.didFinishCommunicationButtonAction = { [weak self] in
            guard let self = self else { return }
            self.viewModel.postCommunications(parameters: self.chatbotView.viewModel.communicationParameter)
        }
        chatbotView.didUpdateCommunicationStep = { [weak self] in
            guard let self = self else { return }
                self.chatbotView.viewModel.update(step: self.viewModel.addMoreFilesStep)
        }
        chatbotView.setup()
    }
    
    func updateChatbotData() {
        chatbotView.viewModel.update(communicationParameterId: viewModel.claimId)
        chatbotView.viewModel.update(step: viewModel.uploadFilesStep)
    }
}

