@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedDomain

class PreventiveCoveredListTests: XCTestCase {
    
    // MARK: - Stored Properties
    var listView: PreventiveCoveredListView!
    let data = [KanguroSharedDomain.CoverageData(name: "test")]
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        listView = PreventiveCoveredListView()
        listView.setupData(data, isEditable: false)
    }
}

// MARK: - Computed Properties
extension PreventiveCoveredListTests {
    
    func testConcatenatedVarNames() {
        XCTAssertTrue(listView.concatenatedNames.isEmpty)
    }
}

// MARK: - Setup
extension PreventiveCoveredListTests {
    
    func testSetupData() {
        XCTAssertEqual(data.first?.name,
                       listView.preventiveCoveredCardListData.first?.data?.name)
    }
}
