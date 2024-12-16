import UIKit

class CodeConfirmationView: BaseView, NibOwnerLoadable {
    
    // MARK: - Dependencies
    var viewModel: CodeConfirmationViewModel! = CodeConfirmationViewModel()
    
    // MARK: - IB Outlets
    @IBOutlet var enterCodeLabel: CustomLabel!
    @IBOutlet private var resendCodeLabel: CustomLabel!
    @IBOutlet var countdownLabel: CustomLabel!
    @IBOutlet var invalidCodeLabel: CustomLabel!
    @IBOutlet private var codeValidationStackView: UIStackView!
    @IBOutlet private var loaderImageView: UIImageView!
    @IBOutlet var validationButton: UIButton!
    @IBOutlet private var belowMessagesStackView: UIStackView!
    
    // MARK: - Stored Properties
    var numberOfDigits: Int?
    var timer = Timer()
    let seconds = 30
    
    // MARK: - Computed Properties
    var codeTextFields: [CodeValidationTextField] {
        return codeValidationStackView.arrangedSubviews as? [CodeValidationTextField] ?? []
    }
    var code: String? {
        let allTexts = codeTextFields.map { $0.text }
        return allTexts.reduce("", { $0 + ($1 ?? "") })
    }
    var isValidCode: Bool {
        return !codeTextFields.contains { $0.isEmpty }
    }
    
    // MARK: - Actions
    var didInputCodeAction: StringClosure = { _ in }
    var resendCodeAction: SimpleClosure = {}
    
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
extension CodeConfirmationView {
    
    private func changed(state: CodeConfirmationViewState) {
        
        switch state {
        case .loading:
            loading()
        case .failed:
            failed()
        case .succeeded:
            succeeded()
        case .requestFailed:
            requestFailed()
        default:
            break
        }
    }
}

// MARK: - Setup
extension CodeConfirmationView {
    
    private func setup() {
        setupLayout()
        setupTimer()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
    
    private func setupLayout() {
        setupCodeValidationStackView()
        setupLabels()
    }
    
    private func setupCodeValidationStackView() {
        guard let numberOfDigits,
              numberOfDigits > 0 else { return }
        for _ in 0..<numberOfDigits {
            let codeTextField = CodeValidationTextField()
            codeTextField.addTarget(self, action: #selector(editingChanged), for: .editingChanged)
            codeValidationStackView.addArrangedSubview(codeTextField)
        }
        codeValidationStackView.layoutIfNeeded()
        codeTextFields.first?.isFirstItem = true
        codeTextFields.first?.becomeFirstResponder()
    }
    
    private func setupLabels() {
        enterCodeLabel.set(text: "codeConfirmationView.enterCode.label".localized,
                           style: TextStyle(color: .secondaryDarkest, size: .p12))
        resendCodeLabel.set(text: "codeConfirmationView.timerMessage.label".localized,
                            style: TextStyle(color: .neutralMedium))
        countdownLabel.set(text: "0:\(String(format: "%02d", seconds))",
                           style: TextStyle(color: .secondaryDarkest, weight: .bold))
        invalidCodeLabel.set(text: "codeConfirmationView.invalidCode.label".localized,
                             style: TextStyle(color: .negativeDarkest))
        invalidCodeLabel.isHidden = true
    }
    
    func setupTimer() {
        var currentSeconds = seconds
        timer = Timer.scheduledTimer(withTimeInterval: 1.0, repeats: true) { [weak self] timer in
            guard let self = self else { return }
            currentSeconds -= 1
            self.countdownLabel.set(text: "0:\(String(format: "%02d", currentSeconds))",
                                    style: TextStyle(color: .secondaryDarkest, weight: .bold))
            if (currentSeconds == 0) {
                timer.invalidate()
                self.resendCode()
            }
        }
    }
}

// MARK: - State Methods
extension CodeConfirmationView {
    
    private func loading() {
        isLoading(true)
        loaderImageView.image = UIImage(named: "ic-loader")
        rotateLoader()
    }
    
    private func succeeded() {
        isLoading(false)
        validationButton.setImage(UIImage(named: "ic-correct"), for: .normal)
        codeTextFields.forEach { $0.isCorrect(true) }
        validationButton.isHidden = false
        belowMessagesStackView.isHidden = true
    }
    
    private func failed() {
        isLoading(false)
        validationButton.setImage(UIImage(named: "ic-clear"), for: .normal)
        codeTextFields.forEach { $0.isCorrect(false) }
        invalidCodeLabel.isHidden = false
    }
    
    private func requestFailed() {
        isLoading(false)
        resetLayout()
    }
    
    @objc func tapFunction(sender: UITapGestureRecognizer) {
        resendCodeAction()
        setupLabels()
        setupTimer()
    }
    
    private func resendCode() {
        resendCodeLabel.set(text: "codeConfirmationView.resendCode.label".localized,
                            style: TextStyle(color: .neutralMedium))
        countdownLabel.set(text: "codeConfirmationView.resendUnderlined.label".localized,
                           style: TextStyle(color: .secondaryDarkest, weight: .bold, underlined: true))
        enableInteraction(true)
        let tap = UITapGestureRecognizer(target: self, action: #selector(self.tapFunction))
        countdownLabel.addGestureRecognizer(tap)
    }
    
    private func rotateLoader() {
        let rotation = CABasicAnimation(keyPath: "transform.rotation.z")
        rotation.toValue = NSNumber(value: Double.pi * 2)
        rotation.duration = 2.0
        rotation.isCumulative = true
        rotation.repeatCount = Float.greatestFiniteMagnitude
        loaderImageView.layer.add(rotation, forKey: "rotationAnimation")
    }
    
    private func isLoading(_ loading: Bool) {
        validationButton.isHidden = loading
        loaderImageView.isHidden = !loading
    }
    
    private func enableInteraction(_ enable: Bool) {
        validationButton.isEnabled = enable
        countdownLabel.isUserInteractionEnabled = enable
    }
    
    private func resetLayout() {
        validationButton.isHidden = true
        invalidCodeLabel.isHidden = true
        codeTextFields.forEach { textField in
            textField.reset()
        }
    }
}

// MARK: - Private Methods
extension CodeConfirmationView {
    
    private func getTextFieldIndex(_ textField: CodeValidationTextField) -> Int {
        return codeTextFields.firstIndex(of: textField) ?? 0
    }
    
    private func validateCode() {
        guard let code else { return }
        if isValidCode { didInputCodeAction(code) }
        codeTextFields.last?.resignFirstResponder()
    }
    
    private func goBack(from textField: CodeValidationTextField) {
        textField.text = ""
        let previousIndex = (getTextFieldIndex(textField)) - 1
        if previousIndex >= 0 {
            codeTextFields[previousIndex].beginInteraction(shouldClearText: true)
        }
    }
    
    private func goFoward(from textField: CodeValidationTextField) {
        if textField.isEmpty { return }
        let nextIndex = (getTextFieldIndex(textField)) + 1
        (codeTextFields.count != nextIndex) ? codeTextFields[nextIndex].beginInteraction() : validateCode()
    }
}

// MARK: - Code Validation Flow
extension CodeConfirmationView {
    
    @objc func editingChanged(_ textField: CodeValidationTextField) {
        textField.pressedDelete ? goBack(from: textField) : goFoward(from: textField)
    }
}

// MARK: - Public Methods
extension CodeConfirmationView {
    
    func setup(numberOfDigits: Int) {
        self.numberOfDigits = numberOfDigits
        setup()
    }
    
    func update(state: CodeConfirmationViewState) {
        self.viewModel.updateState(state)
    }
}

// MARK: - IB Actions
extension CodeConfirmationView {
    
    @IBAction func validationButtonTouchUpInside(_ sender: UIButton) {
        resetLayout()
        codeTextFields.first?.beginInteraction()
    }
}
