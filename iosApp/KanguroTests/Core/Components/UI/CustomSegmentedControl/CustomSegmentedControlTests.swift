@testable import Kanguro
import XCTest
import SwiftUI

class CustomSegmentedControlTests: XCTestCase {
    
    // MARK: - Stored Properties
    var segmentedControl: CustomSegmentedControl!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        let cd = NSKeyedUnarchiver(forReadingWith: NSMutableData() as Data)
        segmentedControl = CustomSegmentedControl(coder: cd)
    }
}

// MARK: - Setup
extension CustomSegmentedControlTests {
    
    func testUpdateColor() {
        let color: UIColor = .black
        segmentedControl.update(borderColor: color)
        XCTAssertEqual(color, segmentedControl.backgroundView.borderColor)
    }
    
    func testUpdateTitleColor() {
        let color: UIColor = .black
        segmentedControl.update(defaultColor: color, selectedColor: color)
        XCTAssertEqual(color, segmentedControl.defaultTitleColor)
    }
    
    func testUpdateSelectedTint() {
        let color: UIColor = .black
        segmentedControl.update(selectedTint: color)
        XCTAssertEqual(color, segmentedControl.segmentedControl.selectedSegmentTintColor)
    }
    
    func testSet() {
        let titles: [String] = ["first", "second"]
        segmentedControl.set(titles: titles)
        let firstTitle = segmentedControl.segmentedControl.titleForSegment(at: 1)
        XCTAssertEqual(titles[1], firstTitle)
    }
    
    func testDidTapAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        segmentedControl.didChangeSegmentedIndexAction = action
        segmentedControl.didChangeSegmentedIndexAction()
        XCTAssertEqual(count, 1)
    }
}
