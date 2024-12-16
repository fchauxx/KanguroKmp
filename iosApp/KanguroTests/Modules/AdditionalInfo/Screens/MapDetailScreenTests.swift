@testable import Kanguro
import XCTest
import SwiftUI
import MapKit

class MapDetailScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var mapScreen: MapDetailViewController!
    var viewModel: MapDetailViewModel!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        mapScreen = MapDetailViewController()
        viewModel = MapDetailViewModel()
        mapScreen.viewModel = viewModel
        let _ = self.mapScreen.view
    }
}

// MARK: - Setup
extension MapDetailScreenTests {
    
    func testGoBackActions() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        mapScreen.goBackAction = action
        mapScreen.goBackAction()
        XCTAssertEqual(count, 1)
    }
    
    func testViewModelState() {
        let newState: DefaultViewState = .dataChanged
        viewModel.state = .dataChanged
        XCTAssertEqual(newState, viewModel.state)
    }
    
    func testSetupMap() {
        XCTAssertNotNil(mapScreen.map)
    }
    
    func testSetLabel() {
        let text = "text"
        mapScreen.titleLabel.set(text: text)
        XCTAssertEqual(text, mapScreen.titleLabel.text)
    }
    
    func testSetupLabels() {
        let text = "mapDetail.title.label".localized
        mapScreen.setupLabels()
        XCTAssertEqual(text, mapScreen.titleLabel.text)
    }
}
