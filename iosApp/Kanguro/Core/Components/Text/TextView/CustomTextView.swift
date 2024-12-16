import UIKit

class CustomTextView: UITextView {
    
    // MARK: - Stored Properties
    private let textBuilder = TextBuilder()
    private var style: TextStyle?
    var placeholder: String?
    
    // MARK: - Actions
    var textViewDidChangeAction: StringClosure = { _ in }
    var textViewDidBeginEditingAction: SimpleClosure = {}
    var textViewDidEndEditing: SimpleClosure = {}
    var returnKeyAction: SimpleClosure = {}
}

// MARK: - Public Methods
extension CustomTextView {
    
    func setup(style: TextStyle,
               actions: TextFieldActions = TextFieldActions()) {
        
        guard let typingAttributes = textBuilder.buildAttributedStyle(style: style) else { return }
        self.style = style
        self.typingAttributes = typingAttributes
        
        self.textViewDidChangeAction = actions.didChangeAction
        self.textViewDidBeginEditingAction = actions.textFieldDidBeginEditingAction
        self.textViewDidEndEditing = actions.textFieldDidEndEditingAction
        self.returnKeyAction = actions.returnKeyAction
        
        delegate = self
    }
    
    func set(placeholder: String, style: TextStyle) {
        self.placeholder = placeholder
        attributedText = textBuilder.buildText(text: placeholder, style: style)
    }
}


// MARK: - UITextViewDelegate
extension CustomTextView: UITextViewDelegate {
    
    func textViewDidBeginEditing(_ textField: UITextView) {
        if text == placeholder { text = nil }
        textViewDidBeginEditingAction()
    }
    
    func textViewDidEndEditing(_ textField: UITextView) {
        textViewDidEndEditing()
    }
    
    @objc func textViewDidChange(_ textView: UITextView) {
        
        let text = textView.text ?? ""
        
        textView.text = text
        textViewDidChangeAction(text)
    }
    
    func textViewShouldReturn(_ textField: UITextView) -> Bool {
        returnKeyAction()
        return true
    }
}
