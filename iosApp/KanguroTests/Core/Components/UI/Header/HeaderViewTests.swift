@testable import Kanguro
import XCTest
import SwiftUI

class HeaderViewTests: XCTestCase {
    
    // MARK: - Stored Properties
    var header: AdditionalInfoHeaderView!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        let cd = NSKeyedUnarchiver(forReadingWith: NSMutableData() as Data)
        header = AdditionalInfoHeaderView(coder: cd)
    }
}

// MARK: - Setup
extension HeaderViewTests {
    
    func testSetLabel() {
        let text = "text"
        header.titleMessageLabel.set(text: text)
        XCTAssertEqual(text, header.titleMessageLabel.text)
    }
    
    func testSetupLabels() {
        let text = "header.title.label".localized
        header.setupLabels()
        XCTAssertEqual(text, header.titleMessageLabel.text)
    }
    
    func testSetupImages() {
        let image = UIImage(named: "javier")
        header.setupImages()
        XCTAssertEqual(image, header.botPhoto.image)
    }
}
