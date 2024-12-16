@testable import Kanguro
import XCTest
import SwiftUI

class CodeConfirmationTests: XCTestCase {
    
    // MARK: - Stored Properties
    var codeConfirmation: CodeConfirmationView!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        codeConfirmation = CodeConfirmationView()
        codeConfirmation.setup(numberOfDigits: 3)
    }
}

// MARK: - Setup
extension CodeConfirmationTests {
    
    func testSetupEnterCodeLabel() {
        let text = "text"
        codeConfirmation.enterCodeLabel.set(text: text)
        XCTAssertEqual(text, codeConfirmation.enterCodeLabel.text)
    }
    
    func testSetupCountdownLabel() {
        let text = "text"
        codeConfirmation.countdownLabel.set(text: text)
        XCTAssertEqual(text, codeConfirmation.countdownLabel.text)
    }
    
    func testSetupInvalidLabel() {
        let text = "text"
        codeConfirmation.invalidCodeLabel.set(text: text)
        XCTAssertEqual(text, codeConfirmation.invalidCodeLabel.text)
    }
}
