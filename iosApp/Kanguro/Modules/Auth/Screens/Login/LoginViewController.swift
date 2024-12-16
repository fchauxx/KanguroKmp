import UIKit

class LoginViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: LoginViewModel!
    
    // MARK: - Actions
    var goBackAction: SimpleClosure = {}
    var goNextAction: StringClosure = { _ in }
    
    // MARK: - IB Outlets
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet var emailTextField: CustomTextFieldView!
    @IBOutlet var continueButton: CustomButton!
}

// MARK: - Life Cycle
extension LoginViewController {
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
extension LoginViewController {

    func changed(state: DefaultViewState) {
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            setupLayout()
        case .dataChanged:
            continueButton.isEnabled(viewModel.isValidData)
        default:
            break
        }
    }
}

// MARK: - Setup
extension LoginViewController {

    private func setupLayout() {
        setupLabels()
        setupButtons()
        setupTextFields()
    }

    func setupLabels() {
        titleLabel.set(text: "login.hello.label".localized + ",",
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
    }

    func setupButtons() {
        continueButton.set(title: "login.continue.button".localized, style: .primary)
        continueButton.isEnabled(false)
        continueButton.onTap { [weak self] in
            guard let self = self,
                  let email = self.viewModel.email else { return }
            self.goNextAction(email)
        }
    }

    func setupTextFields() {
        emailTextField.textField.becomeFirstResponder()
        emailTextField.set(type: .email, actions: TextFieldActions(didChangeAction: update(email:)))
        if let email = viewModel.email {
            emailTextField.set(text: email)
            continueButton.isEnabled(true)
        }
    }

    func update(email: String) {
        viewModel.update(email: email)
    }
}

// MARK: - IB Actions
extension LoginViewController {

    @IBAction func goBackTouchUpInside(_ sender: UIButton) {
        goBackAction()
    }

    @IBAction func signInWithAppleTouchUpInside(_ sender: UIButton) {
    }

    @IBAction func signInWithGoogleTouchUpInside(_ sender: UIButton) {
    }

    @IBAction func continueTouchDownAction(_ sender: Any) {

        self.continueButton.titleLabel?.alpha = 0.3
        self.continueButton.imageView?.alpha = 0.3
    }

    @IBAction func continueTouchUpInsideAction(_ sender: Any) {
        UIView.animate(withDuration: 0.2) { [weak self] in
            guard let self else { return }
            self.continueButton.titleLabel?.alpha = 1.0
            self.continueButton.imageView?.alpha = 1.0
        }
    }
}
