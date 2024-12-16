@testable import Kanguro
import XCTest
import SwiftUI

class WelcomeScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var welcomeScreen: WelcomeViewController!
    var viewModel: WelcomeViewModel!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        welcomeScreen = WelcomeViewController()
        viewModel = WelcomeViewModel()
        welcomeScreen.viewModel = viewModel
        let _ = self.welcomeScreen.view
    }
}

// MARK: - Setup
extension WelcomeScreenTests {
    
    func testGoToSignInAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        welcomeScreen.goToSignInAction = action
        welcomeScreen.goToSignInAction()
        XCTAssertEqual(count, 1)
    }
    
    func testSetLabel() {
        let text = "text"
        welcomeScreen.slide1DescriptionLabel.set(text: text)
        XCTAssertEqual(text, welcomeScreen.slide1DescriptionLabel.text)
    }
    
    func testSetupLabels() {
        let text = "welcome.description1.label".localized
        welcomeScreen.setupLabels()
        XCTAssertEqual(text, welcomeScreen.slide1DescriptionLabel.text)
    }
    
    func testSetupImages() {
        let imageView = UIImageView()
        welcomeScreen.scrollingImages.append(imageView)
        XCTAssertNotNil(welcomeScreen.scrollingImages)
    }
    
    func testLabelsStyle() {
        welcomeScreen.setupLabels()
        let slide1 = welcomeScreen.slide1DescriptionLabel
        let slide2 = welcomeScreen.slide2DescriptionLabel
        XCTAssertEqual(slide1?.textColor, slide2?.textColor)
    }
    
    func testSetupButton() {
        XCTAssertNotNil(welcomeScreen.signInButton.onTap)
    }
}
