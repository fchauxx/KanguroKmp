@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedDomain
import KanguroPetDomain

class CoverageOptionsScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var coverageOptionsVC: CoverageOptionsViewController!
    var viewModel: CoverageOptionsViewModel!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        coverageOptionsVC = CoverageOptionsViewController()
        viewModel = CoverageOptionsViewModel()
        coverageOptionsVC.viewModel = viewModel
        let _ = coverageOptionsVC.view
    }
}

// MARK: - View State
extension CoverageOptionsScreenTests {
    
    func testDataChanged() {
        let petName = "Scooby"
        let fullName = petName + "preventiveCovered.coverage.label".localized
        viewModel.policy = PetPolicy(pet: Pet(id: 0, name: petName))
        
        coverageOptionsVC.changed(state: .dataChanged)
        XCTAssertEqual(fullName, coverageOptionsVC.titleLabel.text)
    }
    
    func testRequestSucceeded() {
        viewModel.coverageDataList = [KanguroSharedDomain.CoverageData(name: "test")]
        coverageOptionsVC.preventiveCoveredListView = PreventiveCoveredListView()
        coverageOptionsVC.changed(state: .requestSucceeded)
        XCTAssertEqual(viewModel.coverageDataList?.count,
                       coverageOptionsVC.preventiveCoveredListView.preventiveCoveredCardListData.count)
    }
    
    func testRequestSucceededReturn() {
        viewModel.coverageDataList = nil
        coverageOptionsVC.preventiveCoveredListView = PreventiveCoveredListView()
        coverageOptionsVC.changed(state: .requestSucceeded)
        XCTAssertTrue(coverageOptionsVC.preventiveCoveredListView.preventiveCoveredCardListData.isEmpty)
    }
}

// MARK: - Setup
extension CoverageOptionsScreenTests {
    
    func testSetupViews() {
        coverageOptionsVC.setupViews()
        XCTAssertEqual(coverageOptionsVC.preventiveCoveredListView.layer.shadowOpacity,
                       0.3)
    }
}
