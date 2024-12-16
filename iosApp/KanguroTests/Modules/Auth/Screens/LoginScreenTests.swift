@testable import Kanguro
import XCTest
import SwiftUI

class LoginScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var vc: LoginViewController!
    var viewModel: LoginViewModel!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        vc = LoginViewController()
        viewModel = LoginViewModel()
        vc.viewModel = viewModel
        let _ = self.vc.view
    }
}

// MARK: - Setup
extension LoginScreenTests {
    
    func testActions() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        vc.goBackAction = action
        vc.goBackAction()
        XCTAssertEqual(count, 1)
    }
    
    func testStringAction () {
        var count = 0
        let stringAction: StringClosure = { _ in count += 1 }
        vc.goNextAction = stringAction
        vc.goNextAction("")
        XCTAssertEqual(count, 1)
    }
    
    func testSetLabel() {
        let text = "text"
        vc.titleLabel.set(text: text)
        XCTAssertEqual(text, vc.titleLabel.text)
    }
    
    func testSetTextField() {
        let type = TextFieldType.email
        vc.emailTextField.set(type: type)
        XCTAssertEqual(type, vc.emailTextField.type)
    }
    
    func testSetupLabels() {
        let text = "login.hello.label".localized + ","
        vc.setupLabels()
        XCTAssertEqual(text, vc.titleLabel.text)
    }
    
    func testSetupButtons() {
        vc.continueButton.isEnabled = true
        vc.setupButtons()
        XCTAssertNotEqual(true, vc.continueButton.isEnabled)
    }
    
    func testUpdate() {
        let text = "text"
        vc.update(email: text)
        XCTAssertEqual(text, viewModel.email)
    }
    
    func testChangedStarted() {
        let text = "login.hello.label".localized + ","
        vc.changed(state: .started)
        XCTAssertTrue(vc.titleLabel.text == text)
    }
    
    func testChangedDataChanged() {
        vc.changed(state: .dataChanged)
        XCTAssertEqual(viewModel.isValidData, vc.continueButton.isEnabled)
    }
    
    func testSetupTextFields() {
        viewModel.email = "email"
        vc.setupTextFields()
        XCTAssertEqual(vc.emailTextField.textField.text, viewModel.email)
    }
    
    func testIsValidData() {
        let rightEmail = "tales.souza@poatek.com"
        viewModel.email = rightEmail
        XCTAssertEqual(true, viewModel.isValidData)
        let wrongEmail1 = "tales.souza"
        viewModel.email = wrongEmail1
        XCTAssertEqual(false, viewModel.isValidData)
        let wrongEmail2 = "tales.souza@"
        viewModel.email = wrongEmail2
        XCTAssertEqual(false, viewModel.isValidData)
    }
    
    func testIBBackAction() {
        let button = UIButton()
        XCTAssertNotNil(vc.goBackTouchUpInside(button))
    }
    
    func testIBSignWithApple() {
        let button = UIButton()
        XCTAssertNotNil(vc.signInWithAppleTouchUpInside(button))
    }
    
    func testIBSignWithGoogle() {
        let button = UIButton()
        XCTAssertNotNil(vc.signInWithGoogleTouchUpInside(button))
    }
}

