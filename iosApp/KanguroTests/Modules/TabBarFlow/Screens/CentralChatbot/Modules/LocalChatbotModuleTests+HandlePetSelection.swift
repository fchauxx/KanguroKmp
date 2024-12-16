import XCTest
@testable import Kanguro
import KanguroSharedDomain
import KanguroPetDomain

extension LocalChatbotModuleTests {
    
    func test_handlePetSelection_WhenStepIsSelectPetAndUserChooseOne_ShouldSaveTheChosenPet() {
        let (sut, services) = makeSUT()
        services.getPolicies.success = [Policy(id: "1", petId: 555), Policy(id: "2", petId: 555)]
        services.remindersService.requestShouldFail = false
        let expectation = XCTestExpectation(description: "Task")
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectPet)

        sut.handlePetSelection(queue: MainDispatcherMock(), NextStepParameters(sessionId: "", value: "555")) { response in
            switch response {
            case .failure, .customError:
                XCTFail("Expected success result")
            case .success:
                XCTAssertEqual(sut.answer[.SelectPet], "Fox")
                XCTAssertNotNil(sut.chosenPetPolicy)
                XCTAssertTrue(sut.hasMedicalReminders!)
                XCTAssertEqual(services.getPolicies.callCount, 1)
                XCTAssertEqual(services.remindersService.calledMethods, [.getReminders])
            }
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 3.0)
    }

    func test_handlePetSelection_WhenStepIsSelectPetAndUserChooseOneAndGetPoliciesRequestFailsButGetReminderSucceeds_ShouldReturnError() {
        let (sut, services) = makeSUT()
        services.getPolicies.failure = RequestError(errorType: .couldNotMap, errorMessage: "serverError.default".localized)
        services.remindersService.requestShouldFail = false
        let expectation = XCTestExpectation(description: "Task")
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectPet)

        sut.handlePetSelection(queue: MainDispatcherMock(), NextStepParameters(sessionId: "", value: "555")) { response in                switch response {
                case .failure(let error), .customError(let error):
                    XCTAssertEqual(error.error, "serverError.default".localized)
                    XCTAssertNil(sut.chosenPetPolicy)
                    XCTAssertTrue(sut.hasMedicalReminders!)
                    XCTAssertEqual(services.getPolicies.callCount, 1)
                    XCTAssertEqual(services.remindersService.calledMethods, [.getReminders])
                case .success:
                    XCTFail("Expected failure result")
                }
                expectation.fulfill()
            }
        wait(for: [expectation], timeout: 3.0)
    }

    func test_handlePetSelection_WhenStepIsSelectPetAndUserChooseOneAndGetRemindersRequestFailsButGetPoliciesSucceeds_ShouldReturnError() {
        let (sut, services) = makeSUT()
        services.getPolicies.success = [Policy(id: "1", petId: 555), Policy(id: "2", petId: 555)]
        services.remindersService.requestShouldFail = true
        let expectation = XCTestExpectation(description: "Task")
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectPet)

        sut.handlePetSelection(queue: MainDispatcherMock(), NextStepParameters(sessionId: "", value: "555")) { response in
            switch response {
            case .failure(let error), .customError(let error):
                XCTAssertEqual(error.statusCode, 500)
                XCTAssertNotNil(sut.chosenPetPolicy)
                XCTAssertFalse(sut.hasMedicalReminders!)
                XCTAssertEqual(services.getPolicies.callCount, 1)
                XCTAssertEqual(services.remindersService.calledMethods, [.getReminders])
            case .success:
                XCTFail("Expected failure result")
            }
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 3.0)

    }

    func test_handlePetSelection_WhenStepIsSelectPetAndUserChooseOneButBothRequestsFails_ShouldReturnError() {
        let (sut, services) = makeSUT()
        services.getPolicies.failure = RequestError(errorType: .serverError, errorMessage: "serverError.default".localized)
        services.remindersService.requestShouldFail = true
        let expectation = XCTestExpectation(description: "Task")
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectPet)

        sut.handlePetSelection(queue: MainDispatcherMock(), NextStepParameters(sessionId: "", value: "555")) { response in

            switch response {
            case .failure(let error), .customError(let error):
                XCTAssertEqual(error.statusCode, 500)
                XCTAssertNil(sut.chosenPetPolicy)
                XCTAssertFalse(sut.hasMedicalReminders!)
                XCTAssertEqual(services.getPolicies.callCount, 1)
                XCTAssertEqual(services.remindersService.calledMethods, [.getReminders])
            case .success:
                XCTFail("Expected failure result")
            }
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 3.0)
    }
}
