import Foundation

class DTPDoneViewController: BaseViewController {

    // MARK: - IBOutlets
    @IBOutlet private var navigationBarView: TitleNavigationBarView!
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var descriptionLabel: CustomLabel!
    @IBOutlet private var okayButton: CustomButton!

    // MARK: Actions
    var okayAction: SimpleClosure
    var closeAction: SimpleClosure

    // MARK: - Initializers
    init(okayAction: @escaping SimpleClosure,
         closeAction: @escaping SimpleClosure) {
        self.okayAction = okayAction
        self.closeAction = closeAction
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: - Life Cycle
extension DTPDoneViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
    }
}

// MARK: - Setup
extension DTPDoneViewController {

    private func setupLayout() {
                setupNavigationBar()
                setupButtons()
                setupLabels()
    }

    private func setupNavigationBar() {
        navigationBarView.setup(title: "directPay.title.label".localized,
                                didTapCloseButtonAction: closeAction)
    }

    private func setupLabels() {
        titleLabel.set(text: "directPay.done.title".localized,
                               style: TextStyle(color: .secondaryDarkest,
                                                weight: .bold,
                                                size: .h24,
                                                font: .raleway,
                                                alignment: .center))
        descriptionLabel.set(text: "directPay.done.description".localized,
                               style: TextStyle(color: .secondaryDark,
                                                size: .p16,
                                                font: .lato,
                                                alignment: .center))
    }

    private func setupButtons() {
        okayButton.set(title: "directPay.okay.button".localized,
                       style: .primary)
        okayButton.setImage(nil, for: .normal)
        okayButton.onTap { [weak self] in
            guard let self else { return }
            self.okayAction()
        }
    }
}
