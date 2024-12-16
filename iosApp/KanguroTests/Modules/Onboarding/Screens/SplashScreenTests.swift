@testable import Kanguro
import XCTest
import SwiftUI

class SplashScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var splashScreen: SplashViewController!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        splashScreen = SplashViewController()
    }
}

// MARK: - Setup
extension SplashScreenTests {
    
    func testDidFinishAnimationAction() {
        var didFinish = false
        splashScreen.didFinishAnimationAction = { didFinish = $0 }
        splashScreen.didFinishAnimationAction(true)
        XCTAssertTrue(didFinish)
    }
    
    func testSetupImages() {
        let _ = self.splashScreen.view
        let image = UIImage(named: "javier")
        splashScreen.kanguroImageView.image = image
        XCTAssertEqual(image, splashScreen.kanguroImageView.image)
    }
}
