@testable import Kanguro
import XCTest
import SwiftUI

class NavigationBackButtonTests: XCTestCase {
    
    // MARK: - Stored Properties
    var navButton: NavigationBackButton!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        let cd = NSKeyedUnarchiver(forReadingWith: NSMutableData() as Data)
        navButton = NavigationBackButton(coder: cd)
    }
}

// MARK: - Setup
extension NavigationBackButtonTests {
    
    func testSetupLayout() {
        let text = "text"
        navButton.title = text
        navButton.setupLayout()
        XCTAssertEqual(text, navButton.titleLabel.text)
    }
    
    func testUpdate() {
        let text = "text"
        navButton.update(title: text)
        XCTAssertEqual(text, navButton.title)
    }
    
    func testDidTapAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        navButton.didTapAction = action
        navButton.didTapAction()
        XCTAssertEqual(count, 1)
    }
}

