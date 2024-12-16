import UIKit

class CodeValidationTextField: UITextField {
    
    // MARK: - Stored Properties
    var isFirstItem = false
    var pressedDelete = false
    
    // MARK: - Computed Properties
    var isEmpty: Bool {
        return text?.isEmpty ?? true
    }
    
    // MARK: - Actions
    var textFieldDidChangeAction: StringClosure = { _ in }
    var textFieldDidBeginEditingAction: SimpleClosure = {}
    var textFieldDidEndEditingAction: SimpleClosure = {}
    var returnKeyAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension CodeValidationTextField {
    
    override func willMove(toSuperview newSuperview: UIView?) {
        super.willMove(toSuperview: newSuperview)
        addTarget(self, action: #selector(editingChanged), for: .editingChanged)
        setupTextField()
        setupLayout()
    }
}

// MARK: - Public Methods
extension CodeValidationTextField {
    
    func setup(actions: TextFieldActions = TextFieldActions()) {
        
        self.textFieldDidChangeAction = actions.didChangeAction
        self.textFieldDidBeginEditingAction = actions.textFieldDidBeginEditingAction
        self.textFieldDidEndEditingAction = actions.textFieldDidEndEditingAction
        self.returnKeyAction = actions.returnKeyAction
    }
    
    func set(text: String) {
        self.text = text
    }
    
    func reset() {
        borderColor = .secondaryDarkest
        borderWidth = 1
        self.text = ""
    }
    
    func isCorrect(_ correct: Bool) {
        borderWidth = 2
        borderColor = (correct == true) ? .positiveDarkest : .negativeDarkest
    }
    
    func beginInteraction(shouldClearText: Bool = false) {
        isUserInteractionEnabled = true
        becomeFirstResponder()
        if shouldClearText { self.text = "" }
    }
}

// MARK: - Private Methods
extension CodeValidationTextField {
    
    private func setupLayout() {
        setupView()
        setupConstraints()
    }
    
    private func setupView() {
        borderColor = .secondaryDarkest
        borderWidth = 1
        cornerRadius = 8
        isSecureTextEntry = true
        textColor = .secondaryDarkest
        textAlignment = .center
        keyboardType = .decimalPad
    }
    
    private func setupConstraints() {
        translatesAutoresizingMaskIntoConstraints = false
        widthAnchor.constraint(greaterThanOrEqualToConstant: 36).isActive = true
        heightAnchor.constraint(greaterThanOrEqualToConstant: 40).isActive = true
        sizeToFit()
    }
    
    @objc func editingChanged() {
        let maxLength = 1
        text = String(text!.prefix(maxLength))
    }
    
    private func setupTextField() {
        self.delegate = self
        self.addTarget(self, action: #selector(textFieldDidChange(_:)), for: .editingChanged)
    }
}

// MARK: - UITextFieldDelegate
extension CodeValidationTextField: UITextFieldDelegate {
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        borderWidth = 2
        textFieldDidBeginEditingAction()
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        borderWidth = 1
        pressedDelete = false
        textFieldDidEndEditingAction()
    }
    
    @objc func textFieldDidChange(_ textField: UITextField) {
        let text = textField.text ?? ""
        textFieldDidChangeAction(text)
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        returnKeyAction()
        return true
    }
    
    // hide selection
    override func selectionRects(for range: UITextRange) -> [UITextSelectionRect] { [] }
    // disable copy paste
    override func canPerformAction(_ action: Selector, withSender sender: Any?) -> Bool { false }
    // override deleteBackward method, set the property value to true and send an action for editingChanged
    override func deleteBackward() {
        if !isFirstItem { pressedDelete = true }
        sendActions(for: .editingChanged)
    }
}
