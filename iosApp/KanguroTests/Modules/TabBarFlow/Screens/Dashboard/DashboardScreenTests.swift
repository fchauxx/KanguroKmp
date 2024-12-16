@testable import Kanguro
import XCTest
import SwiftUI

class DashboardScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var dashboardScreen: DashboardViewController!
    var viewModel: DashboardViewModel!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        dashboardScreen = DashboardViewController()
        viewModel = DashboardViewModel(policies: [PetPolicy(pet: Pet(id: 530))])
        dashboardScreen.viewModel = viewModel
        let _ = self.dashboardScreen.view
    }
    
    override class func tearDown() {
        super.tearDown()
    }
}

// MARK: - Setup
extension DashboardScreenTests {
    
    func testGoBackAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        dashboardScreen.goBackAction = action
        dashboardScreen.goBackAction()
        XCTAssertEqual(1, count)
    }
    
    func testDidTapBannerButtonAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        dashboardScreen.goBackAction = action
        dashboardScreen.goBackAction()
        XCTAssertEqual(1, count)
    }
    
    func testDidTapFileClaimAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        dashboardScreen.goBackAction = action
        dashboardScreen.goBackAction()
        XCTAssertEqual(1, count)
    }
    
    func testDidTapVetAdviceAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        dashboardScreen.goBackAction = action
        dashboardScreen.goBackAction()
        XCTAssertEqual(1, count)
    }
    
    func testDidTapFAQAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        dashboardScreen.goBackAction = action
        dashboardScreen.goBackAction()
        XCTAssertEqual(1, count)
    }
    
    func testDidTapPetParentsAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        dashboardScreen.goBackAction = action
        dashboardScreen.goBackAction()
        XCTAssertEqual(1, count)
    }
    
    func testSetupLabels() {
        let text = "dashboard.hello.label".localized + ","
        XCTAssertEqual(text, dashboardScreen.helloLabel.text)
    }
}
