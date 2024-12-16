@testable import Kanguro
import XCTest
import SwiftUI

class ForgotPasswordScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var vc: ForgotPasswordViewController!
    var viewModel: ForgotPasswordViewModel!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        vc = ForgotPasswordViewController()
        viewModel = ForgotPasswordViewModel()
        vc.viewModel = viewModel
        viewModel.email = "email@email.com"
        vc.loadView()
        vc.viewDidLoad()
    }
}

// MARK: - View State
extension ForgotPasswordScreenTests {
    
    func testChangedLoadingState() {
        vc.changed(state: .loading)
        XCTAssertTrue(vc.sendButton.imageView?.isHidden ?? false)
    }
    
    func testChangedDataChangedState() {
        vc.changed(state: .dataChanged)
        XCTAssertEqual(vc.sendButton.isEnabled,
                       viewModel.isValidData)
    }
    
    func testChangedRequestSucceededState() {
        vc.changed(state: .requestSucceeded)
        XCTAssertFalse(vc.instructionsView.isHidden)
    }
}

// MARK: - Setup
extension ForgotPasswordScreenTests {
    
    func testSetupLabels() {
        XCTAssertEqual(vc.forgotPasswordTitleLabel.text,
                       "forgotPassword.forgotPassword.label".localized)
    }
    
    func testSetupTextFields() {
        XCTAssertEqual(vc.emailTextField.type, .email)
    }
    
    func testSetupButtons() {
        XCTAssertEqual(vc.sendButton.isEnabled,
                       viewModel.isValidData)
    }
}

// MARK: - Public Methods
extension ForgotPasswordScreenTests {
    
    func testUpdateEmail() {
        let email = "test@email.com"
        vc.update(email: email)
        XCTAssertEqual(viewModel.email, email)
    }
    
    func testShowInstructionsView() {
        vc.showInstructionsView()
        XCTAssertFalse(vc.instructionsView.isHidden)
    }
}
