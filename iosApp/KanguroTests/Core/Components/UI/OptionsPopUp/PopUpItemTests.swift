@testable import Kanguro
import XCTest
import SwiftUI

class PopUpItemTests: XCTestCase {
    
    // MARK: - Stored Properties
    var popUpItem: PopUpItem!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        popUpItem = PopUpItem()
    }
}

// MARK: - Setup
extension PopUpItemTests {
    
    func testSetupLabels() {
        let title = "text"
        let image = UIImage()
        let action: SimpleClosure = {}
        let data = PopUpData(title: title, image: image, action: action)
        popUpItem.data = data
        popUpItem.setupLabels()
        XCTAssertEqual(title, popUpItem.titleLabel.text)
    }
    
    func testSetupImages() {
        let title = "text"
        let image = UIImage()
        let action: SimpleClosure = {}
        let data = PopUpData(title: title, image: image, action: action)
        popUpItem.data = data
        popUpItem.setupImages()
        XCTAssertEqual(image, popUpItem.imageView.image)
    }
    
    func testUpdate() {
        let title = "text"
        let image = UIImage()
        let action: SimpleClosure = {}
        let data = PopUpData(title: title, image: image, action: action)
        popUpItem.update(data: data)
        XCTAssertEqual(data.title, popUpItem.data?.title)
    }
}
