@testable import Kanguro
import XCTest
import SwiftUI

class BlotchyLabelsCardTests: XCTestCase {
    
    // MARK: - Stored Properties
    var card: BlotchyLabelsCardView!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        card = BlotchyLabelsCardView()
    }
}

// MARK: - Setup
extension BlotchyLabelsCardTests {
    
    func testSetupLabels() {
        let title = "title"
        card.setup(title: title, topImage: UIImage())
        XCTAssertEqual(title, card.titleLabel.text)
    }
    
    func testSetupImages() {
        let image = UIImage()
        card.setup(title: "", topImage: image)
        XCTAssertEqual(image, card.topImageView.image)
    }
    
    func testSetupStackViews() {
        let data = [BlotchyLabelData(text: "first")]
        card.setupStackViews(stackViewSide: .left, blotchyStringViewData: data, spacing: 2)
        XCTAssertEqual(card.leftStackView.arrangedSubviews.count,
                       data.count)
    }
}
