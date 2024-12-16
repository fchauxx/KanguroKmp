@testable import Kanguro
import XCTest
import SwiftUI

class TextFieldTests: XCTestCase {
    
    // MARK: - Stored Properties
    var textField: CustomTextFieldView!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        textField = CustomTextFieldView()
    }
}

// MARK: - Setup
extension TextFieldTests {
    
    func testSetupOnlyTitle() {
        let text = "text"
        textField.set(title: text)
        XCTAssertEqual(text, textField.titleLabel.text)
    }
    
    func testSetupPlaceholder() {
        let text = "Text"
        textField.set(placeholder: text)
        XCTAssertEqual(text, textField.textField.placeholder)
    }
    
    func testError() {
        let text = "text"
        XCTAssertTrue(textField.errorLabel.isHidden)
        textField.showError(text: text)
        XCTAssertEqual(text, textField.errorLabel.text)
        XCTAssertFalse(textField.errorLabel.isHidden)
    }
}
