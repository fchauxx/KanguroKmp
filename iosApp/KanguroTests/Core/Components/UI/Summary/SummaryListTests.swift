@testable import Kanguro
import XCTest
import SwiftUI

class SummaryListTests: XCTestCase {
    
    // MARK: - Stored Properties
    var summaryList: SummaryList!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        let cd = NSKeyedUnarchiver(forReadingWith: NSMutableData() as Data)
        summaryList = SummaryList(coder: cd)
    }
}

// MARK: - Setup
extension SummaryListTests {
    
    func testSetupLabels() {
        let text = "text"
        summaryList.title = text
        summaryList.setupLabels()
        XCTAssertEqual(text, summaryList.titleLabel.text)
    }
    
    func testUpdate() {
        let text = "text"
        summaryList.update(title: text)
        XCTAssertEqual(text, summaryList.title)
    }
}
