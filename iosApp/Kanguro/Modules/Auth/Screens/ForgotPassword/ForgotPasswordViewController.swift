import UIKit

class ForgotPasswordViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: ForgotPasswordViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet private var forgotPasswordView: UIView!
    @IBOutlet var forgotPasswordTitleLabel: CustomLabel!
    @IBOutlet var emailTextField: CustomTextFieldView!
    @IBOutlet var sendButton: CustomButton!
    
    @IBOutlet var instructionsView: UIView!
    @IBOutlet private var instructionsTitleLabel: CustomLabel!
    @IBOutlet private var instructionsSubtitleLabel: CustomLabel!
    @IBOutlet private var contactLabel: CustomLabel!
    @IBOutlet private var underlinedContactLabel: CustomLabel!
    @IBOutlet private var backLabel: CustomLabel!
    
    // MARK: Actions
    var goBackAction: SimpleClosure = {}
    var goNextAction: StringClosure = { _ in }
    var didTapSendButtonAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension ForgotPasswordViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
        self.hideKeyboardWhenTappedAround()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
}

// MARK: - View State
extension ForgotPasswordViewController {
    
    func changed(state: DefaultViewState) {
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
        case .loading:
            sendButton.startLoader()
        case .dataChanged:
            sendButton.isEnabled(viewModel.isValidData)
        case .requestSucceeded:
            sendButton.stopLoader()
            showInstructionsView()
        case .requestFailed:
            sendButton.stopLoader()
            showActionAlert(title: "forgotPassword.errorTitle.alert".localized,
                            message: viewModel.requestError,
                            action: goBackAction)
        }
    }
}

// MARK: - Setup
extension ForgotPasswordViewController {
    
    private func setupLayout() {
        setupLabels()
        setupTextField()
        setupButtons()
    }
    
    private func setupLabels() {
        forgotPasswordTitleLabel.set(text: "forgotPassword.forgotPassword.label".localized, style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
        instructionsTitleLabel.set(text: "forgotPassword.sentInstructions.label".localized, style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p21, font: .raleway))
        instructionsSubtitleLabel.set(text: "forgotPassword.securityReasons.label".localized, style: TextStyle(color: .secondaryDarkest))
        contactLabel.set(text: "forgotPassword.contactUs.label".localized, style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p12))
        underlinedContactLabel.set(text: "forgotPassword.underlinedContactUs.label".localized, style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p12, underlined: true))
        backLabel.set(text: "forgotPassword.back.label".localized, style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p16, underlined: true))
    }
     
    private func setupTextField() {
        emailTextField.set(type: .email, actions: TextFieldActions(didChangeAction: update(email:)))
        emailTextField.set(title: "forgotPassword.confirmEmail.textField".localized) //overriding email type default title
        if let email = viewModel.email {
            emailTextField.set(text: email)
            sendButton.isEnabled(true)
        }
    }
    
    private func setupButtons() {
        sendButton.set(title: "forgotPassword.sendResetInstructions.button".localized, style: .primary)
        sendButton.isEnabled(viewModel.isValidData)
        sendButton.onTap { [weak self] in
            guard let self = self,
                  let email = self.viewModel.email else { return }
            self.viewModel.resetPassword(email: email)
        }
    }
}

// MARK: - Public Methods
extension ForgotPasswordViewController {
    
    func update(email: String) {
        viewModel.update(email: email)
    }
    
    func showInstructionsView() {
        instructionsView.isHidden = false
        UIView.animate(withDuration: 0.7) { [weak self] in
            guard let self = self else { return }
            self.forgotPasswordView.alpha = 0
            self.instructionsView.alpha = 1
        }
    }
}

// MARK: - IB Actions
extension ForgotPasswordViewController {
    
    @IBAction private func goBackTouchUpInside(_ sender: UIButton) {
        goBackAction()
    }
    
    @IBAction private func contactSupportTouchUpInside(_ sender: UIButton) {
        viewModel.callSupport()
    }
}
