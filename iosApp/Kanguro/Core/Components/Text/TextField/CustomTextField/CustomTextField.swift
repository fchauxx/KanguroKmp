import UIKit

class CustomTextField: UITextField {
    
    // MARK: - Stored Properties
    var type: TextFieldType?
    var textMask: TextMask?
    
    // MARK: - Computed Properties
    var isEmpty: Bool {
        text?.isEmpty ?? true
    }
    var isContentValid: Bool {
        guard let text = text else { return true }
        switch type {
        case .email:
            return text.isValidEmail
        case .password:
            return text.isValidPassword
        case .cellphone:
            return text.isValidPhone
        case .bankRoutingNumber:
            return text.isValidBankRountingNumber
        case .bankAccount:
            return text.isValidBankAccount
        default:
            return !text.isEmpty
        }
    }
    
    // MARK: - Actions
    var textFieldDidChangeAction: StringClosure = { _ in }
    var textFieldDidBeginEditingAction: SimpleClosure = {}
    var textFieldDidEndEditing: SimpleClosure = {}
    var returnKeyAction: SimpleClosure = {}
    
    override func willMove(toSuperview newSuperview: UIView?) {
        super.willMove(toSuperview: newSuperview)
        setupTextField()
    }
}

// MARK: - Public Methods
extension CustomTextField {
    
    func setup(type: TextFieldType,
               config: TextFieldConfig = TextFieldConfig(),
               actions: TextFieldActions = TextFieldActions()) {
        
        self.type = type
        placeholder = config.placeholder
        returnKeyType = config.returnKeyType
        
        self.textFieldDidChangeAction = actions.didChangeAction
        self.textFieldDidBeginEditingAction = actions.textFieldDidBeginEditingAction
        self.textFieldDidEndEditing = actions.textFieldDidEndEditingAction
        self.returnKeyAction = actions.returnKeyAction
        
        delegate = self
    }
    
    func set(text: String) {
        self.text = text
    }
    
    func set(placeholder: String, color: UIColor?) {
        attributedPlaceholder = NSAttributedString(string: placeholder.getOnlyFirstCharacterCapitalized,
                                                   attributes: [NSAttributedString.Key.foregroundColor: color ?? .disabledBlue]
        )
    }
    
    func set(textMask: TextMask?) {
        self.textMask = textMask
    }
    
    func setPhoneFormattedText() {
        textMask = PhoneTextMask()
        guard let text = text,
              let textMask = textMask as? PhoneTextMask else { return }
        set(text: text.getPhoneFormatted(maskFormat: textMask.mask))
    }
}

// MARK: - Private Methods
extension CustomTextField {
    
    private func setupTextField() {
        self.delegate = self
        self.addTarget(self, action: #selector(textFieldDidChange(_:)), for: .editingChanged)
    }
}

// MARK: - UITextFieldDelegate
extension CustomTextField: UITextFieldDelegate {
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        textFieldDidBeginEditingAction()
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        textFieldDidEndEditing()
    }
    
    @objc func textFieldDidChange(_ textField: UITextField) {
        
        var text = textField.text ?? ""
        
        if let mask = textMask {
            text = text.onlyNumbersAndLetters
            text = mask.apply(text: text)
        }
        
        textField.text = text
        textFieldDidChangeAction(text)
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        returnKeyAction()
        return true
    }
    
    func textField(_ textField: UITextField,
                   shouldChangeCharactersIn range: NSRange,
                   replacementString string: String) -> Bool {
        return !(type == .picker || type == .calendarChatbot || type == .calendar)
    }
}
