@testable import Kanguro
import XCTest
import SwiftUI

class TabBarMenuItemViewTests: XCTestCase {
    
    // MARK: - Stored Properties
    var tabBarMenuItemView: TabBarMenuItemView!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        let cd = NSKeyedUnarchiver(forReadingWith: NSMutableData() as Data)
        tabBarMenuItemView = TabBarMenuItemView(coder: cd)
    }
}

// MARK: - Setup
extension TabBarMenuItemViewTests {
    
    func testDidTapPetParentsAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        tabBarMenuItemView.didTapMenuAction = action
        tabBarMenuItemView.didTapMenuAction()
        XCTAssertEqual(count, 1)
    }
    
    func testSetupLabel() {
        let text = "text"
        tabBarMenuItemView.label.set(text: text)
        XCTAssertEqual(text, tabBarMenuItemView.label.text)
    }
    
    func testSetupBotPhoto() {
        let image = UIImage(named: "dog-oliver")
        tabBarMenuItemView.botImageView.image = image
        XCTAssertEqual(image, tabBarMenuItemView.botImageView.image)
    }
    
    func testButtonAction() {
        XCTAssertNotNil(tabBarMenuItemView.menuTouchUpInside(UIButton()))
    }
}
