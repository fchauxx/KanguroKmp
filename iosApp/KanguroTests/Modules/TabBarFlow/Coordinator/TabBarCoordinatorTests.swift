@testable import Kanguro
import XCTest
import UIKit
import KanguroSharedData
import KanguroSharedDomain
import KanguroPetDomain
import KanguroPetData

class TabBarCoordinatorTests: XCTestCase {
    
    var coordinator: HomeTabBarCoordinator!
    var navigation: UINavigationController!
    var mockedData: MockedChatbotViewModelData!

    lazy var localChatbot: LocalChatbotModule = {
        let keychain = StorageMock()
        keychain.cleanAll()
        let network = NetworkMock()
        let claimRepo = PetClaimRepository(network: network)
        let remindersRepo = RemindersRepository(network: network)
        return LocalChatbotModule(
            delegate: ChatbotViewModel(data: mockedData.data,
                                       chatbotServiceType: .remote),
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
        mockedData = MockedChatbotViewModelData()
        navigation = UINavigationController()
        coordinator = HomeTabBarCoordinator(navigation: navigation)
    }
}

// MARK: - Navigation
extension TabBarCoordinatorTests {
    
    func testNavigateToTabBar() {
        let controller: HomeTabBarViewController = HomeTabBarViewController(
            dashboardCoordinator: DashboardCoordinator(
                navigation: UINavigationController()
            ),
            chatbotCoordinator: CentralChatbotCoordinator(
                navigation: UINavigationController(),
                chatbotData: ChatbotData(type: .Central),
                serviceType: .remote
            )
        )
        navigation.addChild(controller)
        let goalController =  coordinator.navigation.popToViewController(viewControllerType: HomeTabBarViewController.self)
        XCTAssertEqual(goalController, controller)
    }
    
    func testNavigateToAddInfo() {
        let controller: AdditionalInfoViewController = AdditionalInfoViewController()
        navigation.addChild(controller)
        let goalController =  coordinator.navigation.popToViewController(viewControllerType: AdditionalInfoViewController.self)
        XCTAssertEqual(goalController, controller)
    }
}
