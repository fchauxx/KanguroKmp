@testable import Kanguro
import XCTest
import UIKit
import SwiftUI
import KanguroPetPresentation
import KanguroNetworkDomain

class CentralChatbotCoordinatorTests: XCTestCase {
    
    // MARK: - Stored Properties
    var coordinator: CentralChatbotCoordinator!
    var navigation: UINavigationController!
    var network: NetworkMock = NetworkMock()
    
    // MARK: - Computed Properties
    var firstVC: CentralChatbotViewController? {
        return coordinator.navigation.viewControllers.first as? CentralChatbotViewController ?? nil
    }
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        navigation = UINavigationController()
        coordinator = CentralChatbotCoordinator(navigation: navigation,                 
                                                chatbotData: ChatbotData(type: .Central),
                                                serviceType: .remote)
        coordinator.start()
        let _ = self.firstVC?.view
    }
}

// MARK: - Navigation
extension CentralChatbotCoordinatorTests {
    
    func testNavigateToCentralChatbot() {
        XCTAssertEqual(navigation.topViewController, firstVC)
    }
    
    func testNavigateToClaims() {
        firstVC?.didTapClaimsAction()
        RunLoop.current.run(until: Date())
        XCTAssertTrue(navigation.topViewController is UIHostingController<EnvironmentWrapperView<TrackYourClaimsView>>)
    }

    func testNavigateToVetAdviceAction() {
        firstVC?.didTapVetAdviceAction()
        RunLoop.current.run(until: Date())
        XCTAssertTrue(navigation.topViewController is VetAdviceBaseViewController)
    }
    
    func testNavigateToFAQAction() {
        firstVC?.didTapFAQAction()
        RunLoop.current.run(until: Date())
        XCTAssertTrue(navigation.topViewController is FAQViewController)
    }
    
    func testNavigateToPetParentAction() {
        firstVC?.didTapPetParentAction()
        RunLoop.current.run(until: Date())
        XCTAssertTrue(navigation.topViewController is PetParentsViewController)
    }
}
