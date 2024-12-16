import XCTest
import Resolver
@testable import Kanguro
import KanguroSharedDomain
import KanguroPetDomain
import KanguroFeatureFlagDomain

struct LocalCBServices {
    var getFeatureFlagBoolService: GetFeatureFlagBoolUseCaseMock = GetFeatureFlagBoolUseCaseMock()
    var getPets: GetPetsUseCaseMock = GetPetsUseCaseMock()
    var getPolicies: GetPoliciesUseCaseMock = GetPoliciesUseCaseMock()
    var getCoverages: GetCoveragesUseCaseMock = GetCoveragesUseCaseMock()
    var remindersService: RemindersServiceMock = RemindersServiceMock()
    var claimsService: ClaimsServiceMock = ClaimsServiceMock()
}

final class LocalChatbotModuleTests: XCTestCase {

    var mockedData: MockedChatbotViewModelData!

    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        mockedData = MockedChatbotViewModelData()
    }

    override class func tearDown() {
        super.tearDown()
    }

    let pets: [Pet] = [
        Pet(id: 999, name: "Clifford"),
        Pet(id: 777, name: "Hakuna"),
        Pet(id: 555, name: "Fox")
    ]

    func makeSUT() -> (LocalChatbotModule, LocalCBServices) {
        let keychain = StorageMock()
        keychain.cleanAll()
        let services = LocalCBServices()
        Resolver.register { services.getFeatureFlagBoolService as GetFeatureFlagBoolUseCaseProtocol }
        Resolver.register { services.claimsService as CreatePetClaimUseCaseProtocol }
        Resolver.register { services.claimsService as CreatePetCommunicationsUseCaseProtocol }
        Resolver.register { services.claimsService as CreatePetClaimUseCaseProtocol }
        Resolver.register { services.claimsService as CreatePetCommunicationsUseCaseProtocol }
        Resolver.register { services.claimsService as CreatePetDocumentsUseCaseProtocol }
        Resolver.register { services.claimsService as GetPetClaimAttachmentsUseCaseProtocol }
        Resolver.register { services.claimsService as GetPetClaimAttachmentUseCaseProtocol }
        Resolver.register { services.claimsService as GetPetClaimsUseCaseProtocol }
        Resolver.register { services.claimsService as GetPetClaimUseCaseProtocol }
        Resolver.register { services.claimsService as GetPetCommunicationsUseCaseProtocol }
        Resolver.register { services.claimsService as PetUpdateFeedbackUseCaseProtocol }


        return (
            LocalChatbotModule(
                delegate: ChatbotViewModel(data: mockedData.data,
                                           chatbotServiceType: .local),
                mainDispatcher: MainDispatcherMock(),
                type: .NewClaim,
                keychain: keychain,
                getPetsService: services.getPets,
                getPoliciesService: services.getPolicies,
                getCoverages: services.getCoverages,
                getRemindersService: services.remindersService,
                createClaimService: services.claimsService,
                createDocumentsService: services.claimsService
            ),
            services
        )
    }
}

// MARK: - Handlers Methods tests
extension LocalChatbotModuleTests {

    func test_handlePledgeOfHonor_WhenUserSignsPledgeOfHonor_ShouldUploadSignatureAndReturnPledgeIdAndNextStep() {
        let (sut, services) = makeSUT()
        let expectation = XCTestExpectation(description: "Task")
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectClaimType)
        sut.chosenPetPolicy = Policy(id: "test_2",
                                     sumInsured: SumInsured(id: 0,
                                                            limit: 200,
                                                            consumed: 200,
                                                            remainingValue: 0),
                                     status: .CANCELED,
                                     petId: 555)
        sut.handlePledgeOfHonor(parameters: NextStepParameters(sessionId: "",
                                                               value: "{\"fileInBase64\": \"/test//Z\", \"fileExtension\": \".jpg\"}",
                                                               action: nil)) { response in
            switch response {
            case .failure, .customError:
                XCTFail("Expected success result")
            case .success(let step):
                XCTAssertNotNil(step)
                XCTAssertEqual(services.claimsService.calledMethods, [.postUploadDocuments])
            }
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 3.0)

    }

    func test_handlePledgeOfHonor_WhenUserSignsPledgeOfHonorAndRequestFails_ShouldReturnError() {
        let (sut, services) = makeSUT()
        services.claimsService.requestShouldFail = true
        let expectation = XCTestExpectation(description: "Task")
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectClaimType)
        sut.chosenPetPolicy = Policy(id: "test_2",
                                     sumInsured: SumInsured(id: 0,
                                                            limit: 200,
                                                            consumed: 200,
                                                            remainingValue: 0),
                                     status: .CANCELED,
                                     petId: 555)
        sut.handlePledgeOfHonor(parameters: NextStepParameters(sessionId: "",
                                                               value: "{\"fileInBase64\": \"/test//Z\", \"fileExtension\": \".jpg\"}",
                                                               action: nil)) { response in
            switch response {
            case .failure(let error), .customError(let error):
                XCTAssertEqual(error.error, "serverError.default".localized)
                XCTAssertEqual(services.claimsService.calledMethods, [.postUploadDocuments])
            case .success:
                XCTFail("Expected failure result")
            }
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 3.0)
    }

    func test_handleAttachments_WhenStepIsSendAttachments_ShouldUploadAndReturnNextStep() {
        let (sut, services) = makeSUT()
        services.claimsService.requestShouldFail = false
        let expectation = XCTestExpectation(description: "Task")
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectClaimType)

        sut.handleAttachments(parameters: NextStepParameters(sessionId: "",
                                                             value: "{\"fileInBase64\": \"/test//Z\", \"fileExtension\": \".jpg\"}",
                                                             action: nil)) { response in
            switch response {
            case .failure, .customError:
                XCTFail("Expected success result")
            case .success(let step):
                XCTAssertNotNil(step)
                XCTAssertEqual(services.claimsService.calledMethods, [.postUploadDocuments])
            }
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 3.0)

    }

    func test_handleAttachments_WhenStepIsSendAttachmentsAndRequestFails_ShouldReturnError() {
        let (sut, services) = makeSUT()
        services.claimsService.requestShouldFail = true
        let expectation = XCTestExpectation(description: "Task")
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectClaimType)

        sut.handleAttachments(parameters: NextStepParameters(sessionId: "",
                                                             value: "{\"fileInBase64\": \"/test//Z\", \"fileExtension\": \".jpg\"}",
                                                             action: nil)) { response in

            switch response {
            case .failure(let error), .customError(let error):
                XCTAssertEqual(error.error, "serverError.default".localized)
                XCTAssertEqual(services.claimsService.calledMethods, [.postUploadDocuments])
            case .success:
                XCTFail("Expected failure result")
            }
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 3.0)

    }

    func test_handleNewClaim_WhenUserIsUpToSubmitAClaim_ShouldUploadNewClaimAndFinishClaimCreation() {
        let (sut, services) = makeSUT()
        services.claimsService.requestShouldFail = false
        let expectation = XCTestExpectation(description: "Task")
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .Summary)
        sut.newClaim = NewPetClaimParameters(description: "",
                                             invoiceDate: Date(),
                                             amount: 1,
                                             petId: 2,
                                             type: .Accident,
                                             pledgeOfHonorId: 1,
                                             reimbursementProcess: .UserReimbursement,
                                             documentIds: [])

        sut.handleNewClaim { response in

            switch response {
            case .failure, .customError:
                XCTFail("Expected success result")
            case .success(let claim):
                XCTAssertNotNil(claim)
                XCTAssertEqual(services.claimsService.calledMethods, [.postClaims])
            }
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 3.0)

    }

    func test_handleNewClaim_WhenUserIsUpToSubmitAClaimAndRequestFails_ShouldReturnError() {
        let (sut, services) = makeSUT()
        services.claimsService.requestShouldFail = true
        let expectation = XCTestExpectation(description: "Task")
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .Summary)
        sut.newClaim = NewPetClaimParameters(description: "",
                                             invoiceDate: Date(),
                                             amount: 1,
                                             petId: 2,
                                             type: .Accident,
                                             pledgeOfHonorId: 1,
                                             reimbursementProcess: .UserReimbursement,
                                             documentIds: [])

        sut.handleNewClaim { response in

            switch response {
            case .failure(let error), .customError(let error):
                XCTAssertEqual(error.error, "serverError.default".localized)
                XCTAssertEqual(services.claimsService.calledMethods, [.postClaims])
            case .success:
                XCTFail("Expected failure result")
            }
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 3.0)
    }
}
