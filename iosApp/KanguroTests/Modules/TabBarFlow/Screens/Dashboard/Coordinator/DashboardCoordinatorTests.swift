@testable import Kanguro
import XCTest
import UIKit
import SwiftUI
import KanguroSharedDomain
import KanguroSharedData
import KanguroPetDomain
import KanguroPetData

class DashboardCoordinatorTests: XCTestCase {
    
    // MARK: - Stored Properties
    var coordinator: DashboardCoordinator!
    var navigation: UINavigationController!
    
    // MARK: - Computed Properties
    var firstVC: DashboardViewController? {
        return coordinator.navigation.viewControllers.first as? DashboardViewController ?? nil
    }

    lazy var localChatbot: LocalChatbotModule = {
        let keychain = StorageMock()
        keychain.cleanAll()
        let network = NetworkMock()
        let claimRepo = PetClaimRepository(network: network)
        let remindersRepo = RemindersRepository(network: network)
        return LocalChatbotModule(
            delegate: ChatbotViewModel(data: ChatbotData(), chatbotServiceType: .local),
            mainDispatcher: MainDispatcherMock(),
            type: .NewClaim,
            keychain: keychain,
            getPetsService: GetPetsUseCaseMock(),
            getPoliciesService: GetPoliciesUseCaseMock(),
            getCoverages: GetCoveragesUseCaseMock(),
            getRemindersService: GetReminders(remindersRepo: remindersRepo),
            createClaimService: CreatePetClaim(claimRepo: claimRepo),
            createDocumentsService: CreatePetDocuments(claimRepo: claimRepo)
        )
    }()
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        navigation = UINavigationController()
        coordinator = DashboardCoordinator(
            navigation: navigation,
            logoutAction: {},
            blockedAction: {},
            localChatbot: localChatbot
        )
        coordinator.start()
        let _ = self.firstVC?.view
    }
}

// MARK: - Navigation
extension DashboardCoordinatorTests {
    
    func testNavigateToDashboard() {
        XCTAssertEqual(navigation.topViewController, firstVC)
    }
    
    func testNavigateToVetAdvice() {
        firstVC?.didTapVetAdviceAction()
        RunLoop.current.run(until: Date())
        guard let vetAdviceVC = navigation.topViewController as? VetAdviceBaseViewController else {
            XCTFail()
            return
        }
        vetAdviceVC.didTapVetAdviceCardAction(.cat)
        RunLoop.current.run(until: Date())
        XCTAssertTrue(navigation.topViewController is VetAdviceViewController)
    }
    
    func testNavigateToPetParents() {
        firstVC?.didTapPetParentsAction()
        RunLoop.current.run(until: Date())
        XCTAssertTrue(navigation.topViewController is PetParentsViewController)
    }
    
    func testNavigateToCentralChatbot() {
        let pet = Pet(id: 1)
        firstVC?.didTapMedicalHistoryCardAction(pet)
        RunLoop.current.run(until: Date())
        XCTAssertTrue(navigation.topViewController is CentralChatbotViewController)
    }
}
