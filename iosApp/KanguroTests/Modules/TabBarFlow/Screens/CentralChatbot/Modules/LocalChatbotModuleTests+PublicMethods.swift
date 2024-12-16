import XCTest
@testable import Kanguro
import KanguroSharedDomain
import KanguroPetDomain

extension LocalChatbotModuleTests {

    func test_postStartSession_WhenAPICallIsSuccessful_ShouldReturnInitialMessagesOfNewClaimWithPetOptions() {
        let (sut, services) = makeSUT()
        let expectation = XCTestExpectation(description: "Task")
        sut.postStartSession(parameters: SessionStartParameters(type: .NewClaim)) { response in
            switch response {
            case .failure, .customError:
                XCTFail("Expected success result")
            case .success(let step):
                XCTAssertNotNil(step)
                XCTAssertEqual(step.actions, [
                    ChatAction(order: 0, label: "Test", value: "999", action: .PetSelected, isMainAction: true, userResponseMessage: nil),
                    ChatAction(order: 1, label: "Test_2", value: "777", action: .PetSelected, isMainAction: true, userResponseMessage: nil),
                    ChatAction(order: 2, label: "Test_3", value: "555", action: .PetSelected, isMainAction: true, userResponseMessage: nil)])
                XCTAssertEqual(services.getPets.callCount, 1)
            }
            expectation.fulfill()
        }
        services.getPets.completeSuccessfully(with: [Pet(id: 999, name: "Test"), Pet(id: 777, name: "Test_2"), Pet(id: 555, name: "Test_3")])
        wait(for: [expectation], timeout: 3.0)
    }

    func test_postStartSession_WhenAPICallFails_ShouldReturnError() {
        let (sut, services) = makeSUT()
        let expectation = XCTestExpectation(description: "Task")

        sut.postStartSession(parameters: SessionStartParameters(type: .NewClaim)) { response in
            switch response {
            case .failure(let error), .customError(let error):
                XCTAssertEqual(error.error, "serverError.default".localized)
                XCTAssertEqual(services.getPets.callCount, 1)
            case .success:
                XCTFail("Expected fail result")
            }
            expectation.fulfill()
        }
        services.getPets.completeWithError()
        wait(for: [expectation], timeout: 3.0)
    }
    
    func test_checkIfPolicyIsNotActiveOrHasReminders_WhenPolicyAreNotActive_ShouldReturnStepOfNotActiveAndEndChatbot() {
        let (sut, _) = makeSUT()
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectClaimType)
        sut.chosenPetPolicy = Policy(id: "test_2", status: .CANCELED, petId: 555)

        let nextStep = sut.checkIfPolicyIsNotActiveOrHasReminders()

        XCTAssertEqual(sut.chatInteractionData?.currentId, .PetPolicyNotActive)
        XCTAssertEqual(nextStep?.actions, [ChatAction(order: 1, label: "chatbot.action.finish.label".localized, value: "StopClaim", action: .StopClaim, isMainAction: false, userResponseMessage: "chatbot.action.finish.label".localized)])
    }

    func test_checkIfPolicyIsNotActiveOrHasReminders_WhenPolicyHasReminders_ShouldReturnStepOfHasRemindersAndEndOrProcceedChatbot() {
        let (sut, _) = makeSUT()
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectClaimType)
        sut.chosenPetPolicy = Policy(id: "test_2", status: .ACTIVE, petId: 555)
        sut.hasMedicalReminders = true

        let nextStep = sut.checkIfPolicyIsNotActiveOrHasReminders()

        XCTAssertEqual(sut.chatInteractionData?.currentId, .PetPendingMedicalHistory)
        XCTAssertEqual(nextStep?.actions, [ChatAction(order: 1, label: "chatbot.action.finish.label".localized, value: "StopClaim", action: .StopClaim, isMainAction: false, userResponseMessage: "chatbot.action.finish.label".localized),
                                           ChatAction(order: 2, label: "chatbot.action.continue.label".localized, value: "Continue", action: .Skip, isMainAction: false, userResponseMessage: "chatbot.action.continue.label".localized)])
    }

    func test_checkIfPolicyIsNotActiveOrHasReminders_WhenPolicyIsActiveAndHasNoReminders_ShouldReturnNil() {
        let (sut, _) = makeSUT()
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectClaimType)
        sut.chosenPetPolicy = Policy(id: "test_2", status: .ACTIVE, petId: 555)
        sut.hasMedicalReminders = false

        let nextStep = sut.checkIfPolicyIsNotActiveOrHasReminders()

        XCTAssertNil(nextStep)
    }

    func test_checkIfPolicyIsOnWaitingPeriodOrHasNoAnnualLimit_WhenPolicyHasWaitingPeriod_ShouldReturnStepOfWaitingPeriodAndEndChatbot() {
        let (sut, _) = makeSUT()
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectClaimType)
        sut.chosenPetPolicy = Policy(id: "test_2", waitingPeriodRemainingDays: 4, sumInsured: SumInsured(id: 0, limit: 200, consumed: 200, remainingValue: 200), petId: 555)

        let nextStep = sut.checkIfPolicyIsOnWaitingPeriodOrHasNoAnnualLimit()

        XCTAssertEqual(sut.chatInteractionData?.currentId, .PetPolicyOnWaitingPeriod)
        XCTAssertEqual(nextStep?.actions, [ChatAction(order: 1, label: "chatbot.action.finish.label".localized, value: "StopClaim", action: .StopClaim, isMainAction: false, userResponseMessage: "chatbot.action.finish.label".localized)])
    }

    func test_checkIfPolicyIsOnWaitingPeriodOrHasNoAnnualLimit_WhenPolicyHasNoAnnualLimit_ShouldReturnStepOfNoAnnualLimitAndEndChatbot() {
        let (sut, _) = makeSUT()
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectClaimType)
        sut.chosenPetPolicy = Policy(id: "test_2", waitingPeriodRemainingDays: 0, sumInsured: SumInsured(id: 0, limit: 200, consumed: 200, remainingValue: 0), petId: 555)

        let nextStep = sut.checkIfPolicyIsOnWaitingPeriodOrHasNoAnnualLimit()

        XCTAssertEqual(sut.chatInteractionData?.currentId, .NoAnnualLimit)
        XCTAssertEqual(nextStep?.actions, [ChatAction(order: 1, label: "chatbot.action.finish.label".localized, value: "StopClaim", action: .StopClaim, isMainAction: false, userResponseMessage: "chatbot.action.finish.label".localized)])
    }

    func test_checkIfPolicyIsOnWaitingPeriodOrHasNoAnnualLimit_WhenPolicyAreNotOnWaitingAndHasAnnual_ShouldReturnNil() {
        let (sut, _) = makeSUT()
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectClaimType)
        sut.chosenPetPolicy = Policy(id: "test_2", waitingPeriodRemainingDays: 0, sumInsured: SumInsured(id: 0, limit: 200, consumed: 200, remainingValue: 44), petId: 555)

        let nextStep = sut.checkIfPolicyIsOnWaitingPeriodOrHasNoAnnualLimit()

        XCTAssertNil(nextStep)
    }

    func test_createSummary_WhenUserIsAlmostFinishingChatbot_ShouldCreateSummaryWithAllProvidedInputs() {
        let (sut, _) = makeSUT()
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .AttachDocuments)
        sut.answer[.SelectPet] = "Fox"
        sut.answer[.PledgeOfHonour] = "{\"fileInBase64\": \"/test//Z\", \"fileExtension\": \".jpg\"}"
        sut.answer[.SelectClaimType] = "Accident"
        sut.answer[.SelectDate] = Date().description
        sut.answer[.AttachDocuments] = "2"
        sut.answer[.PetHealthStatePicture] = "1"
        sut.answer[.TypeTotalAmount] = "77.77"
        sut.answer[.SelectBankAccount] = "Bank Account"

        sut.createSummary()

        XCTAssertNotNil(sut.chatInteractionData?.claimSummary)
    }

    func test_processUserAnswer_WhenUserInputAnswerDuringChatbot_ShouldSaveTheInputData() {
        let (sut, _) = makeSUT()
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectPet)
        let parameters = NextStepParameters(sessionId: "", value: "test_value", action: nil)

        sut.saveUserAnswer(parameters)
        XCTAssertNotNil(sut.answer[.SelectPet])

        sut.chatInteractionData?.currentId = .PledgeOfHonour
        sut.saveUserAnswer(parameters)
        XCTAssertNotNil(sut.answer[.PledgeOfHonour])

        sut.chatInteractionData?.currentId = .SelectClaimType
        sut.saveUserAnswer(parameters)
        XCTAssertNotNil(sut.answer[.SelectClaimType])

        sut.chatInteractionData?.currentId = .TypeDescription
        sut.saveUserAnswer(parameters)
        XCTAssertNotNil(sut.answer[.TypeDescription])

        sut.chatInteractionData?.currentId = .SelectDate
        sut.saveUserAnswer(parameters)
        XCTAssertNotNil(sut.answer[.SelectDate])

        sut.chatInteractionData?.currentId = .AttachDocuments
        sut.saveUserAnswer(parameters)
        XCTAssertNotNil(sut.answer[.AttachDocuments])

        sut.chatInteractionData?.currentId = .PetHealthStatePicture
        sut.saveUserAnswer(parameters)
        XCTAssertNotNil(sut.answer[.PetHealthStatePicture])

        sut.chatInteractionData?.currentId = .TypeTotalAmount
        sut.saveUserAnswer(parameters)
        XCTAssertNotNil(sut.answer[.TypeTotalAmount])

        sut.chatInteractionData?.currentId = .SelectBankAccount
        sut.saveUserAnswer(parameters)
        XCTAssertNotNil(sut.answer[.SelectBankAccount])
    }
}
