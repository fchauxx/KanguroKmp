@testable import Kanguro
import XCTest
import SwiftUI

class AccordionTextFieldViewTests: XCTestCase {
    
    // MARK: - Stored Properties
    var view: AccordionTextFieldView!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        view = AccordionTextFieldView()
    }
}

// MARK: - Setup
extension AccordionTextFieldViewTests {
    
    func testDidTapExpandAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        view.didTapExpandAction = action
        view.didTapExpandAction()
        XCTAssertEqual(count, 1)
    }
    
    func testSetupActionCard() {
        view.setupActionCard()
        XCTAssertEqual(view.title, view.actionCard.data?.leadingTitle)
    }
    
    func testSetupButtons() {
        let title = "accordionTextFieldsView.save.button".localized
        view.setupButtons()
        XCTAssertEqual(view.saveButton.originalText, title)
        XCTAssertTrue(!view.saveButton.isEnabled)
    }
    
    func testUpdateViews() {
        view.isExpanded = false
        view.updateViews()
        XCTAssertTrue(view.stackView.isHidden)
    }
    
    func testChangeItemsStackStatus() {
        view.isExpanded = false
        view.changeItemsStackStatus()
        XCTAssertTrue(view.isExpanded)
    }
    
    func testClose() {
        view.isExpanded = true
        view.close()
        XCTAssertTrue(!view.isExpanded)
    }
    
    func testSetup() {
        let title = "title"
        view.setup(title: title)
        XCTAssertEqual(view.title, title)
    }
    
    func testSetButtonLoading() {
        let isLoading = true
        view.setButtonLoading(isLoading)
        XCTAssertEqual(view.saveButton.loader?.isHidden, !isLoading)
    }
}
