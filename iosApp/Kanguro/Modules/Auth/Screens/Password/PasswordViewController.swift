import UIKit

class PasswordViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: PasswordViewModel!
    
    // MARK: - Actions
    var goBackAction: SimpleClosure = {}
    var goToRootAction: SimpleClosure = {}
    var goNextAction: SimpleClosure = {}
    var goToBlockedAccountAction: SimpleClosure = {}
    var goToForgotPasswordAction: SimpleClosure = {}
    var goToForceUpdatePasswordAction: TwoStringClosure = { (email, password) in }
    
    // MARK: - IB Outlets
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet var passwordTextField: CustomTextFieldView!
    @IBOutlet var signInButton: CustomButton!
    @IBOutlet private var forgotPasswordButton: CustomButton!
    @IBOutlet private var backButton: CustomButton!
    @IBOutlet var backStackView: UIStackView!
}

// MARK: - Life Cycle
extension PasswordViewController {
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
extension PasswordViewController {
    
    func changed(state: PasswordViewState) {
        
        signInButton.isLoading(false)
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            setupLayout()
        case .dataChanged:
            signInButton.isEnabled(viewModel.isValidData)
        case .loading:
            signInButton.isLoading(true)
        case .loginFailed(let error):
            error.errorType == .forbidden ? goToBlockedAccountAction() : showSimpleAlert(message: viewModel.requestError)
        case .setLanguageFailed:
            showSimpleAlert(message: viewModel.requestError)
        case .loginSucceeded:
            viewModel.user?.language == nil ? viewModel.setLanguage() : goNextAction()
        case .passwordUpdateRequired:
            guard let currentPassword = viewModel.password else { return }
            goToForceUpdatePasswordAction(viewModel.email, currentPassword)
        case .setLanguageSucceeded:
            goNextAction()
        }
    }
}

// MARK: - Setup
extension PasswordViewController {
    
    private func setupLayout() {
        setupLabels()
        setupButtons()
        setupTextFields()
    }
    
    func setupLabels() {
        let title = viewModel.type == .`default` ? "password.hello.label".localized : "password.welcome.label".localized
        titleLabel.set(text: title + ",",
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
    }
    
    func setupButtons() {
        let signInTitle = viewModel.type == .`default` ? "password.signIn.button".localized : "password.start.button".localized
        signInButton.set(title: signInTitle, style: .primary)
        signInButton.isEnabled(false)
        signInButton.onTap { [weak self] in
            guard let self else { return }
            self.viewModel.login()
        }
        backButton.set(title: "password.back.button".localized, style: .underlined(color: .secondaryDarkest))
        forgotPasswordButton.set(title: "password.forgot.button".localized, style: .underlined(color: .secondaryDarkest))
        
        if viewModel.type == .create {
            backStackView.isHidden = true
            backButton.isEnabled = false
            forgotPasswordButton.isHidden = true
        }
    }
    
    func setupTextFields() {
        passwordTextField.textField.becomeFirstResponder()
        passwordTextField.set(type: .password,
                              actions: TextFieldActions(didChangeAction: update(password:)))
        if viewModel.type == .create {
            passwordTextField.set(title: "password.createPassword.textField".localized)
        }
    }
    
    func update(password: String) {
        viewModel.update(password: password)
    }
}

// MARK: - IB Actions
extension PasswordViewController {
    
    @IBAction func goBackTouchUpInside(_ sender: UIButton) {
        goBackAction()
    }
    
    @IBAction func goToRootTouchUpInside(_ sender: UIButton) {
        goToRootAction()
    }
    
    @IBAction func forgotPassowrdTouchUpInside(_ sender: UIButton) {
        goToForgotPasswordAction()
    }
}
