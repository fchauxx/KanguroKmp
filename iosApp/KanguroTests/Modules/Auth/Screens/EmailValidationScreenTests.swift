@testable import Kanguro
import XCTest
import SwiftUI

class EmailValidationScreenTests: XCTestCase {
    
    //MARK: - Stored Properties
    var vc: OTPViewController!
    var viewModel: OTPViewModel!
    
    //MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        vc = OTPViewController()
        viewModel = OTPViewModel()
        vc.viewModel = viewModel
        let _ = self.vc.view
    }
}

// MARK: - Setup
extension EmailValidationScreenTests {
    
    func testGoBackAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        vc.backAction = action
        vc.backAction()
        XCTAssertEqual(count, 1)
    }
    
    func testGoNextAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        vc.didCodeSucceedAction = action
        vc.didCodeSucceedAction()
        XCTAssertEqual(count, 1)
    }
    
    func testChangedStarted() {
        vc.changed(state: .started)
        XCTAssertTrue(vc.titleLabel.text == "emailValidation.title.label".localized)
    }
    
    func testChangedFailed() {
        vc.changed(state: .codeValidationFailed)
        XCTAssertTrue(vc.codeConfirmationView.viewModel.state == .failed)
    }
    
    func testChangedSucceeded() {
        vc.changed(state: .codeValidationSucceeded)
        XCTAssertTrue(vc.codeConfirmationView.viewModel.state == .succeeded)
    }
    
    func testSetLabel() {
        let text = "text"
        vc.titleLabel.set(text: text)
        XCTAssertEqual(text, vc.titleLabel.text)
    }
    
    func testSetupLabels() {
        let text = "emailValidation.title.label".localized
        vc.setupLabels()
        XCTAssertEqual(text, vc.titleLabel.text)
    }
    
    func testIBGoToRootAction() {
        let button = UIButton()
        XCTAssertNotNil(vc.backActionTouchUpInside(button))
    }
}
