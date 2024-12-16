import UIKit

class BeforeGetStartedViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: BeforeGetStartedViewModel
    
    // MARK: - IB Outlets
    @IBOutlet private var navigationBarView: TitleNavigationBarView!
    @IBOutlet private var subtitleLabel: CustomLabel!
    
    @IBOutlet private var topicLabelListView: TopicLabelListView!
    @IBOutlet private var textFieldView: CustomTextFieldView!
    @IBOutlet private var goNextButton: CustomButton!
    
    // MARK: Actions
    var nextAction: DoubleOptionalClosure
    var closeAction: SimpleClosure
    
    // MARK: - Initializers
    init(viewModel: BeforeGetStartedViewModel,
         nextAction: @escaping DoubleOptionalClosure,
         closeAction: @escaping SimpleClosure) {
        self.viewModel = viewModel
        self.nextAction = nextAction
        self.closeAction = closeAction
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: - Life Cycle
extension BeforeGetStartedViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
        observe(viewModel.$state) { [weak self] state in
            guard let self else { return }
            self.changed(state: state)
        }
    }
}

// MARK: - View State
extension BeforeGetStartedViewController {
    
    private func changed(state: DefaultViewState) {
        switch state {
        default:
            break
        }
    }
}

// MARK: - Setup
extension BeforeGetStartedViewController {
    
    private func setupLayout() {
        setupNavigationBar()
        setupLabels()
        setupTopicLabels()
        setupButtons()
        setupTextFieldView()
    }
    
    private func setupNavigationBar() {
        navigationBarView.setup(title: "directPay.title.label".localized,
                                didTapCloseButtonAction: closeAction)
    }
    
    private func setupLabels() {
        subtitleLabel.set(text: "directPay.beforeGetStarted".localized,
                          style: TextStyle(color: .secondaryDarkest,
                                           weight: .bold,
                                           size: .p21,
                                           font: .raleway))
    }
    
    private func setupTextFieldView() {
        textFieldView.set(type: .defaultCurrency,
                                config: TextFieldConfig(title: "directPay.beforeGetStarted.claimValue".localized),
                                actions: TextFieldActions(didChangeAction: { [weak self] value in
            guard let self else { return }
            self.viewModel.update(claimValue: value)
            self.goNextButton.isEnabled(viewModel.minimumClaimValue)
        }))
    }
    
    private func setupTopicLabels() {
        topicLabelListView.setup(dataList: [
            TopicLabelViewData(title: "directPay.beforeGetStarted.topicLabel.first".localized,
                               highlightedTitle: "directPay.beforeGetStarted.topicLabel.first.highlighted".localized,
                               style: .highlighted(color: .secondaryDark)),
            TopicLabelViewData(title: "directPay.beforeGetStarted.topicLabel.second".localized,
                               highlightedTitle: "directPay.beforeGetStarted.topicLabel.second.highlighted".localized,
                               style: .highlighted(color: .secondaryDark)),
            TopicLabelViewData(title: "directPay.beforeGetStarted.topicLabel.third".localized,
                               highlightedTitle: "directPay.beforeGetStarted.topicLabel.third.highlighted".localized,
                               style: .highlighted(color: .secondaryDark)),
            TopicLabelViewData(title: "directPay.beforeGetStarted.topicLabel.fourth".localized,
                               highlightedTitle: "directPay.beforeGetStarted.topicLabel.fourth.highlighted".localized,
                               style: .highlighted(color: .secondaryDark))
        ])
    }
    
    private func setupButtons() {
        goNextButton.set(title: "directPay.action.label".localized,
                         style: .primary)
        goNextButton.setImage(nil, for: .normal)
        goNextButton.isEnabled(viewModel.claimValue != nil)
    }
}

// MARK: - Actions
extension BeforeGetStartedViewController {

    @IBAction func nextButtonTouchUpInside(_ sender: UIButton) {
        nextAction(viewModel.claimValue)
    }
}
