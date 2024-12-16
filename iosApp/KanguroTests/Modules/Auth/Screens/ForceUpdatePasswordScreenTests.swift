//
//  ForceUpdatePasswordScreenTests.swift
//  KanguroTests
//
//  Created by Rodrigo Okido on 05/12/22.
//
@testable import Kanguro
import UIKit
import XCTest

class ForceUpdatePasswordScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var vc: ForceUpdatePasswordViewController!
    var viewModel: ForceUpdatePasswordViewModel!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        vc = ForceUpdatePasswordViewController()
        viewModel = ForceUpdatePasswordViewModel(email: "tales.souza@poatek.com", currentPassword: "Abcde123!")
        vc.viewModel = viewModel
        let _ = self.vc.view
    }
}

// MARK: - Setup
extension ForceUpdatePasswordScreenTests {
    
    func testGoRootAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        vc.goToRootAction = action
        vc.goToRootAction()
        XCTAssertEqual(count, 1)
    }
    
    func testSetLabel() {
        let text = "label_test"
        vc.titleLabel.set(text: text)
        XCTAssertEqual(text, vc.titleLabel.text)
    }
    
    func testSetTextField() {
        let pwd_test = "textfield_test"
        vc.newPasswordTextfield.set(text: pwd_test)
        XCTAssertEqual(pwd_test, vc.newPasswordTextfield.textField.text)
        
        let confirmPwd_test = "confirm_textfield_test"
        vc.confirmNewPasswordTextfield.set(text: confirmPwd_test)
        XCTAssertEqual(confirmPwd_test, vc.confirmNewPasswordTextfield.textField.text)
    }
    
    func testSetupLabels() {
        let title = "forceNewPassword.letstart.label".localized
        let descrption = "forceNewPassword.description.label".localized
        vc.setupLabels()
        XCTAssertEqual(title, vc.titleLabel.text)
        XCTAssertEqual(descrption, vc.passwordChangeDescription.text)
    }
    
    func testPasswordScreenUpdate() {
        let text = "text"
        vc.update(newPassword: text)
        vc.update(repeatedPassword: text)
        XCTAssertEqual(text, viewModel.newPassword)
        XCTAssertEqual(viewModel.newPassword, viewModel.repeatedPassword)
    }
    
    func testIsValidPassword() {
        let wrongPassword = "1c3d5"
        viewModel.newPassword = wrongPassword
        XCTAssertFalse(viewModel.isValidPassword)
        
        let newPassword = "1c3d5S!"
        let repeatedPassword = "1c3d5S!"
        viewModel.newPassword = newPassword
        viewModel.repeatedPassword = repeatedPassword
        XCTAssertTrue(viewModel.isValidPassword)
    }
    
    func testViewModelUpdate() {
        var VMState = viewModel.state
        VMState = .started
        viewModel.update(newPassword: "1234")
        XCTAssertNotEqual(VMState, viewModel.state)
    }
}
