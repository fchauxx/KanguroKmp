@testable import Kanguro
import XCTest
import SwiftUI

class MoreActionsCardTests: XCTestCase {
    
    // MARK: - Stored Properties
    var moreActionsCard: ActionCard!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        let cd = NSKeyedUnarchiver(forReadingWith: NSMutableData() as Data)
        moreActionsCard = ActionCard(coder: cd)
    }
}

// MARK: - Setup
extension MoreActionsCardTests {
    
    func testDidTapCardAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        moreActionsCard.didTapCardAction = action
        moreActionsCard.didTapCardAction()
        XCTAssertEqual(count, 1)
    }
    
    func testSetLabel() {
        let text = "text"
        moreActionsCard.leadingTitleLabel.set(text: text)
        XCTAssertEqual(text, moreActionsCard.leadingTitleLabel.text)
    }
}
