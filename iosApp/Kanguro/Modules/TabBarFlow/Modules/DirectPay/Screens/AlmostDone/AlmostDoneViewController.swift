import UIKit

class AlmostDoneViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: AlmostDoneViewModel
    
    // MARK: - IB Outlets
    @IBOutlet private var closeButton: CustomButton!
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var descriptionLabel: CustomLabel!
    @IBOutlet private var tapHereDescription: CustomLabel!
    @IBOutlet private var okayButton: CustomButton!

    // MARK: Actions
    var closeAction: SimpleClosure
    var okayAction: SimpleClosure
    var didTapSendFormYourself: ClaimClosure

    // MARK: Initializers
    init(viewModel: AlmostDoneViewModel,
         closeAction: @escaping SimpleClosure,
         okayAction: @escaping SimpleClosure,
         didTapSendFormYourself: @escaping ClaimClosure
    ) {
        self.closeAction = closeAction
        self.okayAction = okayAction
        self.didTapSendFormYourself = didTapSendFormYourself
        self.viewModel = viewModel
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: - Life Cycle
extension AlmostDoneViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
}

// MARK: - View State
extension AlmostDoneViewController {
    
    private func changed(state: DefaultViewState) {
        
        switch state {
        default:
            break
        }
    }
}

// MARK: - Setup
extension AlmostDoneViewController {
    
    private func setupLayout() {
        setupLabels()
        setupButtons()
        setupLabelAction()
    }
    
    private func setupLabels() {
        titleLabel.set(text: "directPay.almostDone.title".localized,
                       style: TextStyle(color: .secondaryDark,
                                        weight: .bold,
                                        size: .h24,
                                        font: .raleway,
                                        alignment: .center))
        descriptionLabel.setHighlightedText(text: "directPay.almostDone.description".localized,
                                            style: TextStyle(color: .secondaryDark,
                                                             size: .p16,
                                                             alignment: .center),
                                            highlightedText: "directPay.almostDone.description.highlighted".localized)
        tapHereDescription.setHighlightedText(text: "directPay.almostDone.secondary.description".localized,
                                         style: TextStyle(color: .secondaryDark,
                                                          weight: .regular,
                                                          alignment: .center),
                                         highlightedText: "directPay.almostDone.tapHere.description".localized,
                                         highlightedStyle: TextStyle(color: .tertiaryDarkest,
                                                                     underlined: true))
    }

    @objc func labelTapped(_ sender: UITapGestureRecognizer) {
        didTapSendFormYourself(viewModel.dtpPetClaim)
    }

    private func setupLabelAction() {
        let labelTap = UITapGestureRecognizer(target: self, action: #selector(self.labelTapped(_:)))
        tapHereDescription.isUserInteractionEnabled = true
        tapHereDescription.addGestureRecognizer(labelTap)
    }
    
    private func setupButtons() {
        okayButton.set(title: "directPay.okay.button".localized,
                       style: .primary)
        okayButton.setImage(nil, for: .normal)
        okayButton.onTap { [weak self] in
            guard let self else { return }
            self.okayAction()
        }
        
        closeButton.onTap { [weak self] in
            guard let self else { return }
            self.closeAction()
        }
    }
}
