@testable import Kanguro
import XCTest
import SwiftUI

class CustomButtonTests: XCTestCase {
    
    // MARK: - Stored Properties
    var button: CustomButton!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        button = CustomButton()
    }
}

// MARK: - Setup
extension CustomButtonTests {
    
    func testSetupOnlyTitle() {
        let text = "text"
        button.set(title: text)
        XCTAssertEqual(text, button.titleLabel?.text)
    }
    
    func testLoader() {
        let loader = button.loader
        XCTAssertNil(loader)
    }
    
    func testIsEnabled() {
        let enable = true
        button.isEnabled(enable)
        XCTAssertEqual(enable, button.isEnabled)
    }
    
    func testOnTap() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        button.onTap(action)
        button.sendActions(for: .touchUpInside)
        XCTAssertEqual(count, 1)
    }
    
    func testSecondaryStyle() {
        button.set(style: .secondary)
        XCTAssertEqual(button.backgroundColor, .white)
    }
    
    func testOutlinedStyle() {
        button.set(style: .outlined)
        XCTAssertEqual(button.backgroundColor, .clear)
    }
}
