import XCTest
@testable import Kanguro

class CustomLabelTests: XCTestCase {
    
    // MARK: - Stored Properties
    var label: CustomLabel!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        label = CustomLabel()
    }
}

// MARK: - Setup
extension CustomLabelTests {
    
    func testLabelDefaultSetup() {
        let text = "test"
        let style = TextStyle()
        label.set(text: text, style: style)
        XCTAssertEqual(text, label.text)
    }
    
    func testSetChangingLabels() {
        let list = ["string"]
        label.setChangingLabels(list: list, duration: 3, style: TextStyle())
        XCTAssertEqual(label.text, list.first)
    }
    
    func testSetTypingText() {
        let text = ""
        label.setTypingText(text: text)
        XCTAssertEqual(label.text, text)
    }
}
