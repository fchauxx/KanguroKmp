@testable import Kanguro
import XCTest
import SwiftUI

class PasswordScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var vc: PasswordViewController!
    var viewModel: PasswordViewModel!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        vc = PasswordViewController()
        viewModel = PasswordViewModel(email: "tales.souza@poatek.com")
        vc.viewModel = viewModel
        let _ = self.vc.view
    }
}

// MARK: - Setup
extension PasswordScreenTests {
    
    func testGoBackAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        vc.goBackAction = action
        vc.goBackAction()
        XCTAssertEqual(count, 1)
    }
    
    func testGoRootAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        vc.goToRootAction = action
        vc.goToRootAction()
        XCTAssertEqual(count, 1)
    }
    
    func testSetLabel() {
        let text = "text"
        vc.titleLabel.set(text: text)
        XCTAssertEqual(text, vc.titleLabel.text)
    }
    
    func testSetTextField() {
        let text = "text"
        vc.passwordTextField.set(text: text)
        XCTAssertEqual(text, vc.passwordTextField.textField.text)
    }
    
    func testSetupLabels() {
        let text = "password.hello.label".localized + ","
        vc.setupLabels()
        XCTAssertEqual(text, vc.titleLabel.text)
    }
    
    func testSetupButtons() {
        viewModel.type = .create
        vc.backStackView.isHidden = false
        vc.setupButtons()
        XCTAssertNotEqual(false, vc.backStackView.isHidden)
    }
    
    func testPasswordScreenUpdate() {
        let text = "text"
        vc.update(password: text)
        XCTAssertEqual(text, viewModel.password)
    }
    
    func testIsValidData() {
        let rightPassword = "1c3d5S!"
        viewModel.password = rightPassword
        XCTAssertEqual(true, viewModel.isValidData)
        let wrongPassword = "1c3d5"
        viewModel.password = wrongPassword
        XCTAssertEqual(false, viewModel.isValidData)
        let createdRightPassword = "1c3d5S!"
        viewModel.password = createdRightPassword
        viewModel.type = .create
        XCTAssertEqual(true, viewModel.isValidData)
    }
    
    func testViewModelUpdate() {
        var VMState = viewModel.state
        VMState = .started
        viewModel.update(password: "1234")
        XCTAssertNotEqual(VMState, viewModel.state)
    }
    
    func testChangedStarted() {
        let title = viewModel.type == .`default` ? "password.hello.label".localized : "password.welcome.label".localized
        let text = title + ","
        vc.changed(state: .started)
        XCTAssertTrue(vc.titleLabel.text == text)
    }
    
    func testChangedDataChanged() {
        vc.changed(state: .dataChanged)
        XCTAssertEqual(viewModel.isValidData, vc.signInButton.isEnabled)
    }
    
    func testChangedLoading() {
        vc.changed(state: .loading)
        let isLoading = vc.signInButton.loader?.isHidden ?? true
        XCTAssertTrue(!isLoading)
    }
    
    func testSetupTextFields() {
        viewModel.type = .create
        vc.setupTextFields()
        XCTAssertNotNil(vc.passwordTextField.textField.text)
    }
    
    func testIBBackAction() {
        let button = UIButton()
        XCTAssertNotNil(vc.goBackTouchUpInside(button))
    }
    
    func testIBGoToRoot() {
        let button = UIButton()
        XCTAssertNotNil(vc.goToRootTouchUpInside(button))
    }
    
    func testIBForgotPasswordAction() {
        let button = UIButton()
        XCTAssertNotNil(vc.forgotPassowrdTouchUpInside(button))
    }
}
