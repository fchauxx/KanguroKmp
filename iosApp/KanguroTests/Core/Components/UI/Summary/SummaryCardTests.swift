@testable import Kanguro
import XCTest
import SwiftUI

class SummaryCardTests: XCTestCase {
    
    // MARK: - Stored Properties
    var summaryCard: SummaryCard!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        summaryCard = SummaryCard()
    }
}

// MARK: - Setup
extension SummaryCardTests {
    
    func testSetupLabels() {
        let data = SummaryData(leadingTitle: "leadingTitle", traillingTitle: "traillingTitle")
        summaryCard.data = data
        summaryCard.setupLabels()
        XCTAssertEqual(data.leadingTitle, summaryCard.leadingTitleLabel.text)
    }
    
    func testSetupSummaryType() {
        let data = SummaryData(summaryType: .statusView)
        summaryCard.data = data
        summaryCard.setupSummaryType()
        var isStatusView: Bool {
            summaryCard.traillingTitleLabel.isHidden && !summaryCard.statusView.isHidden
        }
        XCTAssertTrue(isStatusView)
    }
    
    func testUpdate() {
        let data = SummaryData()
        summaryCard.update(data: data)
        XCTAssertEqual(data.summaryType, summaryCard.data?.summaryType)
    }
}
