import UIKit

class GettingStartedViewController: BaseViewController {
    
    // MARK: - IB Outlets
    @IBOutlet private var navigationBarView: TitleNavigationBarView!
    
    @IBOutlet private var firstSubtitleLabel: CustomLabel!
    @IBOutlet private var verticalStepsView: VerticalStepListView!
    
    @IBOutlet private var secondSubtitleLabel: CustomLabel!
    @IBOutlet private var topicLabelListView: TopicLabelListView!
    
    @IBOutlet private var goNextButton: CustomButton!
    
    // MARK: Actions
    var nextAction: SimpleClosure
    var backAction: SimpleClosure
    var closeAction: SimpleClosure
    
    // MARK: - Initializers
    init(nextAction: @escaping SimpleClosure,
         backAction: @escaping SimpleClosure,
         closeAction: @escaping SimpleClosure) {
        self.nextAction = nextAction
        self.backAction = backAction
        self.closeAction = closeAction
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: - Life Cycle
extension GettingStartedViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
    }
}

// MARK: - Setup
extension GettingStartedViewController {
    
    private func setupLayout() {
        setupNavigationBar()
        setupButtons()
        setupLabels()
        setupTopicLabels()
        setupVerticalStepsView()
    }
    
    private func setupNavigationBar() {
        navigationBarView.setup(title: "directPay.title.label".localized,
                                didTapBackButtonAction: backAction,
                                didTapCloseButtonAction: closeAction)
    }
    
    private func setupLabels() {
        let style = TextStyle(color: .secondaryDarkest,
                              weight: .bold,
                              size: .p21,
                              font: .raleway)
        firstSubtitleLabel.set(text: "directPay.gettingStarted".localized,
                               style: style)
        secondSubtitleLabel.set(text: "directPay.pleaseNote".localized,
                               style: style)
    }
    
    private func setupButtons() {
        goNextButton.set(title: "directPay.action.label".localized,
                       style: .primary)
        goNextButton.setImage(nil, for: .normal)
        goNextButton.onTap { [weak self] in
            guard let self else { return }
            self.nextAction()
        }
    }
    
    private func setupTopicLabels() {
        topicLabelListView.setup(dataList: [
            TopicLabelViewData(title: "directPay.gettingStarted.topicLabel.first".localized,
                               highlightedTitle: "directPay.gettingStarted.topicLabel.first.highlighted".localized,
                               style: .highlighted(color: .secondaryDark)),
            TopicLabelViewData(title: "directPay.gettingStarted.topicLabel.second".localized,
                               highlightedTitle: "directPay.gettingStarted.topicLabel.second.highlighted".localized,
                               style: .highlighted(color: .secondaryDark))
        ])
    }
    
    private func setupVerticalStepsView() {
        verticalStepsView.setup(dataList: [
            VerticalStepViewData(iconName: "ic-list-document",
                                 title: "directPay.gettingStarted.step".localized
                .replacingOccurrences(of: "value", with: "1"),
                                 description: "directPay.gettingStarted.verticalStep.first".localized,
                                 highlightedDescription: "directPay.gettingStarted.verticalStep.first.highligted"),
            VerticalStepViewData(iconName: "ic-share",
                                 title: "directPay.gettingStarted.step".localized
                .replacingOccurrences(of: "value", with: "2"),
                                 description: "directPay.gettingStarted.verticalStep.second".localized,
                                 highlightedDescription: "directPay.gettingStarted.verticalStep.second.highligted"),
            VerticalStepViewData(iconName: "ic-mail",
                                 title: "directPay.gettingStarted.step".localized
                .replacingOccurrences(of: "value", with: "3"),
                                 description: "directPay.gettingStarted.verticalStep.third".localized,
                                 isLastItem: true)
        ])
    }
}
