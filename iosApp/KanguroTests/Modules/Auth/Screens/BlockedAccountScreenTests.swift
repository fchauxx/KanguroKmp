@testable import Kanguro
import XCTest
import SwiftUI

class BlockedAccountScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var vc: BlockedAccountViewController!
    var viewModel: BlockedAccountViewModel!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        vc = BlockedAccountViewController()
        viewModel = BlockedAccountViewModel()
        vc.viewModel = viewModel
        vc.loadView()
        vc.viewDidLoad()
    }
}

// MARK: - Setup
extension BlockedAccountScreenTests {
    
    func testSetupLabels() {
        XCTAssertEqual(vc.accountProblemMessageLabel.text,
                       "blockedAccount.problemMessage.label".localized)
    }
    
    func testSetupButtons() {
        XCTAssertEqual(vc.mailButton.titleLabel?.text,
                       "profile.delete.label".localized)
    }
}
