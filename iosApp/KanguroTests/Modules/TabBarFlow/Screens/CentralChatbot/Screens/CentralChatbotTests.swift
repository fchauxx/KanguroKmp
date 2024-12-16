@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedData
import KanguroSharedDomain
import KanguroPetData
import KanguroPetDomain

class CentralChatbotScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var chatbotVC: CentralChatbotViewController!
    var viewModel: CentralChabotViewModel!
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
        chatbotVC = CentralChatbotViewController()
        viewModel = CentralChabotViewModel(
            data: ChatbotData(type: .Central),
            chatbotServiceType: .remote)
        chatbotVC.viewModel = viewModel
        chatbotVC.loadViewIfNeeded()
    }
}

// MARK: - Life Cycle
extension CentralChatbotScreenTests {
    
    func testViewWillAppearSetup() {
        chatbotVC.viewWillAppear(false)
        chatbotVC.shouldSetupChatbot = false
        viewModel.data.type = .PetMedicalHistoryDocuments
        chatbotVC.viewWillAppear(false)
        XCTAssertEqual(viewModel.data.type, chatbotVC.chatbotView.viewModel.data.type)
    }
    
    func testSetupChatbotViewModelType() {
        checkSetupChatbotViewModelType(type: .Central)
        checkSetupChatbotViewModelType(type: .AdditionalInformation)
        checkSetupChatbotViewModelType(type: .PetMedicalHistoryDocuments)
    }
    
    func checkSetupChatbotViewModelType(type: SessionType) {
        viewModel.data.type = type
        chatbotVC.viewWillAppear(false)
        XCTAssertEqual(type, chatbotVC.chatbotView.viewModel.data.type)
    }
}
