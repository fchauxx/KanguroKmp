@testable import Kanguro
import XCTest
import SwiftUI

class AccordionCardViewTests: XCTestCase {
    
    // MARK: - Stored Properties
    var view: AccordionCardView!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        view = AccordionCardView()
    }
}

// MARK: - Setup
extension AccordionCardViewTests {
    
    func testSetupActionCard() {
        view.setupActionCard()
        XCTAssertEqual(view.title, view.actionCard.data?.leadingTitle)
    }
    
    func testSetupItemsStackView() {
        view.setupItemsStackView()
        XCTAssertNotEqual(view.itemsStackView.isHidden,
                          view.isExpanded)
    }
    
    func testSetupView() {
        view.setupView()
        XCTAssertEqual(view.backgroundView.backgroundColor,
                       view.cardBGColor?.color)
    }
    
    func testSetColoredBorderIsHidden() {
        view.setColoredBorderIsHidden(false)
        XCTAssertTrue(view.borderWidth == 2)
    }
    
    func testChangeItemsStackStatus() {
        view.isExpanded = false
        view.changeItemsStackStatus()
        XCTAssertTrue(view.isExpanded)
    }
    
    func testUpdate() {
        let title = "title"
        view.update(title: title)
        XCTAssertEqual(view.title, title)
    }
    
    func testAddItems() {
        let data = [AccordionCardItemData(leadingTitle: "leadingTitle",
                                          traillingTitle: "traillingTitle")]
        view.addItems(accordionItemsData: data)
        XCTAssertTrue(view.itemsStackView.subviews.count == data.count)
    }
}
