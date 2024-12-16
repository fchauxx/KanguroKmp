@testable import Kanguro
import XCTest
import UIKit

class CoverageCoordinatorTests: XCTestCase {
    
    // MARK: - Stored Properties
    var coordinator: CoverageCoordinator!
    var navigation: UINavigationController!
    
    // MARK: - Computed Properties
    var firstVC: CoverageViewController? {
        return coordinator.navigation.viewControllers.first as? CoverageViewController ?? nil
    }
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        navigation = UINavigationController()
        coordinator = CoverageCoordinator(navigation: navigation, policies: [], blockedAction: {})
        coordinator.start()
        let _ = self.firstVC?.view
    }
}

// MARK: - Navigation
extension CoverageCoordinatorTests {
    
    func testNavigateToCoverage() {
        XCTAssertEqual(navigation.topViewController, firstVC)
    }
    
    func testNavigateToFaq() {
        firstVC?.didTapFAQAction()
        RunLoop.current.run(until: Date())
        XCTAssertTrue(navigation.topViewController is FAQViewController)
    }
}
