@testable import Kanguro
import XCTest
import UIKit

class UIImageViewExtensionsTests: XCTestCase {
    
    // MARK: - Stored Properties
    var imageView: UIImageView!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        imageView = UIImageView()
    }
}

// MARK: - Setup
extension UIImageViewExtensionsTests {

    func testSetAnimatedImage() {
        let image = UIImage()
        imageView.setAnimatedImage(image, duration: 2)
        
        XCTAssertEqual(image, imageView.image)
    }
}
