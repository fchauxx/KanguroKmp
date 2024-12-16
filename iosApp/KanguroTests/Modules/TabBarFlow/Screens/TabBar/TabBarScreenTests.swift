@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedDomain
import KanguroSharedData
import KanguroPetDomain
import KanguroPetData

class TabBarScreenTests: XCTestCase {

    // MARK: - Stored Properties
    var tabBarVC: HomeTabBarViewController!
    var viewModel: HomeTabBarViewModel!
    var coordinator: Coordinator!
    var mockedData: MockedChatbotViewModelData!

    lazy var localChatbot: LocalChatbotModule = {
        let keychain = StorageMock()
        keychain.cleanAll()
        let claimRepo = PetClaimRepository(network: NetworkMock())
        return LocalChatbotModule(
            delegate: ChatbotViewModel(data: mockedData.data,
                                       chatbotServiceType: .remote),
            mainDispatcher: MainDispatcherMock(),
            type: .NewClaim,
            keychain: keychain,
            getPetsService: GetPetsUseCaseMock(),
            getPoliciesService: GetPoliciesUseCaseMock(),
            getCoverages: GetCoveragesUseCaseMock(),
            getRemindersService: RemindersServiceMock(),
            createClaimService: CreatePetClaim(claimRepo: claimRepo),
            createDocumentsService: CreatePetDocuments(claimRepo: claimRepo)
        )
    }()

    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        mockedData = MockedChatbotViewModelData()

        let dashboardCoordinator = DashboardCoordinator(
            navigation: UINavigationController()
        )
        let chatbotCoordinator = CentralChatbotCoordinator(
            navigation: UINavigationController(),
            chatbotData: ChatbotData(type: .Central),
            serviceType: .remote
        )
        tabBarVC = HomeTabBarViewController(
            dashboardCoordinator: dashboardCoordinator,
            chatbotCoordinator: chatbotCoordinator
        )
        tabBarVC.dashboardCoordinator = DashboardCoordinator(
            navigation: UINavigationController(),
            localChatbot: localChatbot
        )
        viewModel = HomeTabBarViewModel()
        coordinator = Coordinator(navigation: UINavigationController())
        tabBarVC.viewModel = viewModel
        let _ = self.tabBarVC.view
    }
}

// MARK: - View State
extension TabBarScreenTests {

    func testGetPetsSucceeded() {
        let petsList = [Pet(id: 0)]
        var currentPets: [Pet] = []
        let action: PetsClosure = { _ in
            currentPets = petsList
        }
        tabBarVC.goToAdditionalInfoAction = action
        tabBarVC.changedPet(state: .goToPetAdditionalInfoFlow)
        XCTAssertEqual(currentPets.count, petsList.count)
    }

    func testGoToDashboard() {
        viewModel.didSetupTabBar = false
        tabBarVC.changed(state: .goToHome)
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            XCTAssertTrue(self.viewModel.selected?.tabBarMenuItemView.type == .dashboard)
        }
    }
}

// MARK: - Setup
extension TabBarScreenTests {

    func testSetupViews() {
        let view = UIView()
        XCTAssertNotNil(tabBarVC.getContainer(navigationView: view))
    }

    func testSetupTabBar() {
        let currentNumberOfTabBarItems = 5
        tabBarVC.setupTabBar()
        XCTAssertEqual(currentNumberOfTabBarItems, viewModel.menus.count)
    }

    func testCreateNavigation() {
        var navigation: UINavigationController?
        navigation = tabBarVC.createNavigation()
        XCTAssertTrue(navigation is UINavigationController)
        XCTAssertNotNil(navigation)
    }

    func testViewModelAddBarMenuItem() {
        let item = TabBarMenuItem(container: UIView(),
                                  tabBarMenuItemView: TabBarMenuItemView(),
                                  coordinator: coordinator)
        viewModel.addTabBarMenuItem(item)
        XCTAssertTrue(viewModel.menus.contains(item))
    }

    func testAddTabBarMenuItem() {
        let item = TabBarMenuItemData(type: .bot, coordinator: coordinator)
        let tabBarItem = tabBarVC.createTabBarMenuItem(type: item.type,
                                                       coordinator: item.coordinator)
        tabBarVC.addTabBarMenuItems([tabBarItem])
        XCTAssertTrue(viewModel.menus.contains(tabBarItem))
    }

    func testSetTabBarHidden() {
        tabBarVC.setTabBarHidden(false)
        XCTAssertEqual(tabBarVC.view.backgroundColor, .white)

        tabBarVC.setTabBarHidden(true)
        XCTAssertEqual(tabBarVC.view.backgroundColor, .neutralBackground)
    }
}

// MARK: - View Model
extension TabBarScreenTests {

    func testSetupPoliciesWithNilPets() {
        let policies = [Policy()]
        viewModel.setupPetPolicies(policies: policies)
        XCTAssertEqual(policies.count, viewModel.policies.count)
        XCTAssertEqual(viewModel.petsViewState, .didSetPetPolicies)
    }

    func testAddPetPolicy() {
        let pet = Pet(id: 0)
        let policy = Policy(id: "123",
                            status: .ACTIVE,
                            petId: pet.id,
                            preventive: true)
        viewModel.policies = [policy, policy]
        viewModel.policies.forEach { policy in
            viewModel.addPetPolicy(pet: pet, policy: policy)
        }

        XCTAssertEqual(viewModel.petPolicies.count, viewModel.policies.count)
        XCTAssertEqual(viewModel.petsViewState, .didSetPetPolicies)

        let petPoliciesFirstId = viewModel.petPolicies.first?.pet.id
        let policiesFirstId = viewModel.policies.first?.petId

        XCTAssertEqual(policiesFirstId, petPoliciesFirstId)
    }

    func testSetupPetsWithoutAdditionalInfo() {
        let pets = [Pet(id: 0, hasAdditionalInfo: false)]
        viewModel.setupPets(pets: pets)
        XCTAssertEqual(viewModel.incompletedPets.count, pets.count)
        XCTAssertEqual(viewModel.petsViewState, .goToPetAdditionalInfoFlow)
    }

    func testSetupPetsWithAdditionalInfo() {
        let pets = [Pet(id: 0, hasAdditionalInfo: true)]
        viewModel.setupPets(pets: pets)
        XCTAssertTrue(viewModel.incompletedPets.isEmpty)
    }

    func testSetupPetsReturn() {
        let pets = [Pet(id: 0)]
        viewModel.setupPets(pets: pets)
        XCTAssertTrue(viewModel.incompletedPets.isEmpty)
    }

    func testHandleBlockedUserResponse() {
        viewModel.handleBlockedUserResponse(isBlocked: true)
        XCTAssertEqual(viewModel.viewState, .blockedUser)

        viewModel.viewState = .loading
        viewModel.handleBlockedUserResponse(isBlocked: false)
        XCTAssertEqual(viewModel.viewState, .allowedUser)
    }

    func testStartDataRequests() {
        viewModel.fetchPetsData()
        let savedLangString: String = viewModel.userDefaults.get(key: "preferredLanguage") ?? "en"
        XCTAssertEqual(viewModel.user?.language ?? .English,
                       Language(rawValue: savedLangString))
    }
}
