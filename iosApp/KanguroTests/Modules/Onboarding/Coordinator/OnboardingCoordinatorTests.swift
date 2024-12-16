@testable import Kanguro
import XCTest
import UIKit

class OnboardingCoordinatorTests: XCTestCase {
    
    var coordinator: OnboardingCoordinator!
    var navigation: UINavigationController!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        navigation = UINavigationController()
        coordinator = OnboardingCoordinator(navigation: navigation)
    }
}

// MARK: - Setup
extension OnboardingCoordinatorTests {
    
    func testNavigateToSplash() {
        let controller: SplashViewController = SplashViewController()
        navigation.addChild(controller)
        let goalController =  coordinator.navigation.popToViewController(viewControllerType: SplashViewController.self)
        XCTAssertEqual(goalController, controller)
    }
    
    func testNavigateToWelcome() {
        let controller: WelcomeViewController = WelcomeViewController()
        navigation.addChild(controller)
        let goalController =  coordinator.navigation.popToViewController(viewControllerType: WelcomeViewController.self)
        XCTAssertEqual(goalController, controller)
    }
    
    func testNavigateToGetQuote() {
        guard let url = AppURLs.getPetQuote.url else { return }
        let controller: WebviewViewController = WebviewViewController(url: url)
        navigation.addChild(controller)
        let goalController =  coordinator.navigation.popToViewController(viewControllerType: WebviewViewController.self)
        XCTAssertEqual(goalController, controller)
    }
}
