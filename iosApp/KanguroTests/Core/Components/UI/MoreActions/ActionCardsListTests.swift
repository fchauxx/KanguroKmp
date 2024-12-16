@testable import Kanguro
import XCTest
import SwiftUI

class ActionCardsListTests: XCTestCase {
    
    // MARK: - Stored Properties
    var actionCardsList: ActionCardsList!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        actionCardsList = ActionCardsList()
    }
}

// MARK: - Setup
extension ActionCardsListTests {
    
    func testSetLabel() {
        let text = "text"
        actionCardsList.titleLabel.set(text: text)
        XCTAssertEqual(text, actionCardsList.titleLabel.text)
    }
    
    func testSetupLabels() {
        let text = "moreActions.titleLabel.label".localized
        actionCardsList.update(title: text)
        actionCardsList.setupLabels()
        XCTAssertEqual(text, actionCardsList.titleLabel.text)
    }
}
