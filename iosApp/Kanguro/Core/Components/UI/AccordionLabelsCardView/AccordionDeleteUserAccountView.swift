import UIKit
import Combine

enum AccordionDeleteUserAccountType {
    
    case account
    case policy
}

class AccordionDeleteUserAccountView: BaseView, NibOwnerLoadable, AccordionViewProtocol {
    
    // MARK: - Dependencies
    var viewModel: AccordionDeleteUserAccountViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet private var stackView: UIStackView!
    @IBOutlet var actionCard: ActionCard!
    @IBOutlet private var topTitleLabel: CustomLabel!
    
    @IBOutlet private var contentView: UIView!
    
    @IBOutlet private var topicLabelsView: UIView!
    @IBOutlet private var firstTopicLabelView: TopicLabelView!
    @IBOutlet private var secondTopicLabelView: TopicLabelView!
    @IBOutlet private var thirdTopicLabelView: TopicLabelView!
    @IBOutlet private var fourthTopicLabelView: TopicLabelView!
    
    @IBOutlet private var separationView: UIView!
    
    @IBOutlet private var buttonView: UIView!
    @IBOutlet private var switchLabel: CustomLabel!
    @IBOutlet var sendRequestButton: CustomButton!
    
    @IBOutlet private var policyContentView: UIView!
    @IBOutlet private var bottomFirstMsgLabel: CustomLabel!
    @IBOutlet private var bottomSecondMsgLabel: CustomLabel!
    @IBOutlet private var bottomThirdMsgLabel: CustomLabel!
    
    // MARK: - Stored Properties
    var isExpanded: Bool = false
    var title: String?
    var type: AccordionDeleteUserAccountType?
    
    // MARK: - Actions
    var didTapExpandAction: SimpleClosure = {}
    var showLoadingAction: SimpleClosure = {}
    var hideLoadingAction: SimpleClosure = {}
    var logoutAction: SimpleClosure = {}
    
    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
    }
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.loadNibContent()
    }
}

// MARK: - View State
extension AccordionDeleteUserAccountView {
    
    private func changed(state: DefaultViewState) {
        
        hideLoadingAction()
        
        switch state {
        case .loading:
            showLoadingAction()
        case .requestFailed:
            showSimpleAlert(message: viewModel.requestError)
        case .requestSucceeded:
            logoutAction()
        default:
            break
        }
    }
}

// MARK: - Setup
extension AccordionDeleteUserAccountView {
    
    private func setupObserver() {
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
    
    private func setupLayout() {
        setupActionCard()
        setupLabels()
        setupButtons()
        setupObserver()
        setupViews()
    }
    
    func setupLabels() {
        guard let type = type else { return }
        
        var topTitle = ""
        switch type {
        case .account:
            setupAccountLabels()
            topTitle = "profile.accountTopTitle.label".localized.uppercased()
        case .policy:
            setupPolicyLabels()
            topTitle = "profile.policyTopTitle.label".localized.uppercased()
        }
        
        topTitleLabel.set(text: topTitle,
                          style: TextStyle(color: .secondaryDarkest, weight: .bold))
        topTitleLabel.setupToFitWidth()
    }
    
    func setupActionCard() {
        guard let title = title else { return }
        actionCard.setup(actionCardData: ActionCardData(leadingTitle: title,
                                                        didTapAction: changeItemsStackStatus,
                                                        viewType: .accordion(fontSize: .p14)),
                         backgroundColor: .white)
    }
    
    func setupButtons() {
        sendRequestButton.set(title: "profile.sendRequest.button".localized,
                              style: .primary)
        sendRequestButton.setImage(nil, for: .normal)
        sendRequestButton.onTap { [weak self] in
            guard let self = self else { return }
            self.showConfirmationAlert(message: "profile.sure.alert".localized,
                                       confirmAction: self.viewModel.deleteUserAccount)
        }
    }
    
    private func setupViews() {
        topicLabelsView.isHidden = !(type == .account)
        separationView.isHidden = !(type == .account)
        buttonView.isHidden = !(type == .account)
        policyContentView.isHidden = !(type == .policy)
        contentView.isHidden = !isExpanded
    }
    
    private func setupAccountLabels() {
        switchLabel.set(text: "profile.switchTitle.label".localized,
                        style: TextStyle(color: .tertiaryExtraDark, size: .p12))
        firstTopicLabelView.setup(data: TopicLabelViewData(title: "profile.firstTopic.label".localized))
        secondTopicLabelView.setup(data: TopicLabelViewData(title: "profile.secondTopic.label".localized))
        thirdTopicLabelView.setup(data: TopicLabelViewData(title: "profile.thirdTopic.label".localized))
        fourthTopicLabelView.setup(data: TopicLabelViewData(title: "profile.fourthTopic.label".localized))
    }
    
    private func setupPolicyLabels() {
        let msgStyle = TextStyle(color: .secondaryMedium)
        let msgCustomStyle = TextStyle(color: .tertiaryExtraDark, underlined: true)
        
        bottomFirstMsgLabel.set(text: "profile.bottomFirstMsg.label".localized,
                                style: msgStyle)
        bottomSecondMsgLabel.setHighlightedText(text: "profile.bottomSecondMsg.label".localized,
                                                style: msgStyle,
                                                highlightedText: "profile.phoneNumber.label".localized,
                                                highlightedStyle: msgCustomStyle)
        bottomThirdMsgLabel.setHighlightedText(text: "profile.bottomThirdMsg.label".localized,
                                               style: msgStyle,
                                               highlightedText: "profile.emailUs.label".localized,
                                               highlightedStyle: msgCustomStyle)
    }
}

// MARK: - Private Methods
extension AccordionDeleteUserAccountView {
    
    func changeItemsStackStatus() {
        isExpanded.toggle()
        actionCard.update(isExpanded: isExpanded)
        animateExpansion()
        didTapExpandAction()
    }
    
    private func animateExpansion() {
        UIView.animate(withDuration: 0.5) { [weak self] in
            guard let self = self else { return }
            self.contentView.isHidden = !self.isExpanded
            self.stackView.superview?.layoutIfNeeded()
        }
    }
}

// MARK: - Public Methods
extension AccordionDeleteUserAccountView {
    
    func setup(title: String, type: AccordionDeleteUserAccountType) {
        self.title = title
        self.type = type
        setupLayout()
    }
    
    func close() {
        isExpanded = false
        animateExpansion()
    }
}

// MARK: - IB Actions
extension AccordionDeleteUserAccountView {
    
    @IBAction private func callSupportTouchUpInside(_ sender: UIButton) {
        viewModel.callSupport()
    }
    
    @IBAction private func sendEmailTouchUpInside(_ sender: UIButton) {
        viewModel.openEmail()
    }
}
