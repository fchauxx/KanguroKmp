import Foundation
import UIKit

// MARK: - Errors
extension CustomTextFieldView {

    func showError(text: String) {
        errorLabel.set(text: text, style: TextStyle(color: .negativeDarkest))
        errorLabel.isHidden = false
        textFieldView.borderColor = .negativeDarkest

        if !leadingIconImageView.isHidden { leadingIconImageView.tintColor = .disabledBlue }
        traillingIconImageView.image = UIImage(named: "ic-clear")
        traillingIconView.isHidden = false
        traillingButtonAction = clearText
    }

    func hideError() {
        errorLabel.isHidden = true
        setLayout()
        textFieldView.borderColor = .disabledBlue
        if !leadingIconImageView.isHidden { leadingIconImageView.tintColor = .disabledBlue }
    }

    func clearText() {
        textField.text = ""
        hideError()
        setLayout()
    }
}

