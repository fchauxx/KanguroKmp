import UIKit

class FeedbackViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: FeedbackViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet var feedbackButton: CustomButton!
    @IBOutlet private var sliderView: CustomSlider!
    @IBOutlet var kanguroImageView: UIImageView!
    @IBOutlet private var textViewBG: UIView!
    @IBOutlet var feedbackTextView: CustomTextView!
    
    // MARK: - Actions
    var didTapSendFeedbackAction: SimpleClosure = {}
    var didFinishFeedbackAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension FeedbackViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
        self.hideKeyboardWhenTappedAround()
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
extension FeedbackViewController {
    
    func changed(state: DefaultViewState) {
        
        feedbackButton.isLoading(false)
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
        case .loading:
            feedbackButton.isLoading(true)
        case .dataChanged:
            viewModel.sendFeedback()
        case .requestFailed:
            showActionAlert(message: viewModel.requestError,
                            action: didFinishFeedbackAction)
        case .requestSucceeded:
            didFinishFeedbackAction()
        }
    }
}

// MARK: - Setup
extension FeedbackViewController {
    
    private func setupLayout() {
        setupActions()
        setupLabels()
        setupSliderView()
        setupTextViews()
        setupButtons()
    }
    
    private func setupLabels() {
        titleLabel.set(text: "feedback.title.label".localized,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h24, font: .raleway, alignment: .center))
    }
    
    private func setupTextViews() {
        let style = TextStyle(color: .secondaryDarkest, weight: .bold)
        feedbackTextView.setup(style: style,
                               actions: TextFieldActions(didChangeAction: viewModel.update(feedbackDescription:),
                                                         textFieldDidBeginEditingAction: updateFeedbackBGColor,
                                                         textFieldDidEndEditingAction: clearFeedbackBgColor))
        feedbackTextView.set(placeholder: "feedback.placeholder.textfield".localized,
                             style: style)
    }
    
    func setupButtons() {
        feedbackButton.set(title: "feedback.sendFeedback.button".localized, style: .primary)
        feedbackButton.setImage(nil, for: .normal)
        feedbackButton.onTap(didTapSendFeedbackAction)
    }
    
    private func setupSliderView() {
        sliderView.setupSliderData(viewModel.sliderData)
    }
    
    func updateKanguroImage(rate: Int) {
        guard let image = UIImage(named: "feedback-\(rate)") else { return }
        kanguroImageView.image = image
    }
    
    private func setupActions() {
        sliderView.didChangeRateValueAction = { [weak self] rate in
            guard let self = self else { return }
            self.updateKanguroImage(rate: rate)
        }
        didTapSendFeedbackAction = { [weak self] in
            guard let self = self else { return }
            self.viewModel.update(feedbackRate: self.sliderView.feedbackRate)
        }
    }
    
    private func updateFeedbackBGColor() {
        UIView.animate(withDuration: 0.1) { [weak self] in
            guard let self = self else { return }
            self.textViewBG.backgroundColor = .primaryLight
        }
    }
    
    private func clearFeedbackBgColor() {
        if !feedbackTextView.text.isEmpty { return }
        UIView.animate(withDuration: 0.1) { [weak self] in
            guard let self = self else { return }
            self.textViewBG.backgroundColor = .clear
        }
    }
}
