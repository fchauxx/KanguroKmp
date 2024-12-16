@testable import Kanguro
import XCTest
import SwiftUI

class AccordionDeleteUserAccountViewTests: XCTestCase {
    
    // MARK: - Stored Properties
    var view: AccordionDeleteUserAccountView!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        view = AccordionDeleteUserAccountView()
        view.viewModel = AccordionDeleteUserAccountViewModel()
    }
}

// MARK: - Setup
extension AccordionDeleteUserAccountViewTests {
    
    func testDidTapExpandAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        view.didTapExpandAction = action
        view.didTapExpandAction()
        XCTAssertEqual(count, 1)
    }
    
    func testClose() {
        view.isExpanded = true
        view.close()
        XCTAssertTrue(!view.isExpanded)
    }
}
