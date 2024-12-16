@testable import Kanguro
import XCTest
import UIKit

class AuthCoordinatorTests: XCTestCase {
    
    // MARK: - Stored Properties
    var coordinator: AuthCoordinator!
    var navigation: UINavigationController!
    var homeTabBarCoordinator: HomeTabBarCoordinator!
    
    // MARK: - Computed Properties
    var firstVC: LoginViewController? {
        return navigation.viewControllers.first as? LoginViewController ?? nil
    }
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        navigation = UINavigationController()
        homeTabBarCoordinator = HomeTabBarCoordinator(navigation: navigation)
        coordinator = AuthCoordinator(navigation: navigation, homeTabBarCoordinator: homeTabBarCoordinator)
        coordinator.start()
        let _ = firstVC?.view
    }
}

// MARK: - Navigation
extension AuthCoordinatorTests {
    
    func testStart() {
        //TODO: Ask how to test presented view controllers
    }
}
