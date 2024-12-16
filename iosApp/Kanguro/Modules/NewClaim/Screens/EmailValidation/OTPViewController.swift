import UIKit

class OTPViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: OTPViewModel!
    
    // MARK: - IB Actions
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet var codeConfirmationView: CodeConfirmationView!
    
    // MARK: - Actions
    var didCodeSucceedAction: SimpleClosure = {}
    var backAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension OTPViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        self.hideKeyboardWhenTappedAround()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
}

// MARK: - View State
extension OTPViewController {
    
    func changed(state: OTPViewState) {
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            setupLayout()
            viewModel.sendCode()
        case .sendMessageFailed:
            showConfirmationAlert(message: "otp.failedMessageSending".localized,
                                  confirmAction: viewModel.sendCode,
                                  cancelAction: backAction)
        case .loadingCodeValidation:
            codeConfirmationView.update(state: .loading)
        case .codeValidationFailed:
            codeConfirmationView.update(state: .failed)
        case .codeValidationSucceeded:
            codeConfirmationView.update(state: .succeeded)
            DispatchQueue.main.asyncAfter(deadline: .now() + 1) { [weak self] in
                guard let self = self else { return }
                self.didCodeSucceedAction()
            }
        case .requestFailed:
            codeConfirmationView.update(state: .requestFailed)
            showSimpleAlert(message: viewModel.error)
        default:
            break
        }
    }
}

// MARK: - Setup
extension OTPViewController {
    
    private func setupLayout() {
        setupLabels()
        setupCodeConfirmation()
    }
    
    func setupLabels() {
        titleLabel.set(text: "emailValidation.title.label".localized,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
    }
    
    private func setupCodeConfirmation() {
        codeConfirmationView.setup(numberOfDigits: 6)
        codeConfirmationView.resendCodeAction = { [weak self] in
            guard let self = self else { return }
            self.viewModel.sendCode()
        }
        codeConfirmationView.didInputCodeAction = { [weak self] code in
            guard let self = self else { return }
            self.viewModel.getCodeValidation(code: code)
        }
    }
}

// MARK: - IB Actions
extension OTPViewController {
    
    @IBAction func backActionTouchUpInside(_ sender: UIButton) {
        backAction()
    }
}
