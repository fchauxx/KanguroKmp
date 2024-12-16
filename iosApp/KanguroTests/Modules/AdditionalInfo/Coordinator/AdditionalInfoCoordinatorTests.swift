@testable import Kanguro
import XCTest
import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class AdditionalInfoCoordinatorTests: XCTestCase {
    
    // MARK: - Stored Properties
    var coordinator: AdditionalInfoCoordinator!
    var navigation: UINavigationController!
    var pets: [Pet] = [Pet(id: 0)]
    
    // MARK: - Computed Properties
    var firstVC: AdditionalInfoViewController? {
        return coordinator.navigation.viewControllers.first as? AdditionalInfoViewController ?? nil
    }
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        navigation = UINavigationController()
        coordinator = AdditionalInfoCoordinator(navigation: navigation, pets: pets)
        coordinator.start()
        let _ = self.firstVC?.view
    }
}

// MARK: - Navigation
extension AdditionalInfoCoordinatorTests {
    
    func testStart() {
        XCTAssertEqual(firstVC?.viewModel.pets.count, pets.count)
    }
    
    func testNavigateAddInfo() {
        XCTAssertTrue(navigation.topViewController is AdditionalInfoViewController)
    }
}
