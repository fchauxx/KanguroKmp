@testable import Kanguro
import XCTest
import UIKit

class UIViewExtensionsTests: XCTestCase {
    
    // MARK: - Stored Properties
    var view: UIView!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        view = UIView()
    }
}

// MARK: - Setup
extension UIViewExtensionsTests {

    func testCornerRadius() {
        let radius: CGFloat = 8
        view.cornerRadius = CGFloat(radius)
        XCTAssertEqual(radius, view.cornerRadius)
    }
    
    func testBorderWidth() {
        let width: CGFloat = 8
        view.borderWidth = CGFloat(width)
        XCTAssertEqual(width, view.borderWidth)
    }
    
    func testBorderColor() {
        let color: UIColor = .red
        view.borderColor = color
        XCTAssertEqual(color, view.borderColor)
    }
    
    func testSetAsCircle() {
        let radius = UIView().frame.height/2
        view.setAsCircle()
        XCTAssertEqual(radius, view.cornerRadius)
    }
}
