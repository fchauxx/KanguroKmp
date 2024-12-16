import XCTest
@testable import Kanguro
import KanguroSharedDomain
import KanguroPetDomain

extension LocalChatbotModuleTests {

    func test_handleClaimTypeSelection_WhenStepIsSelectClaimTypeAndChooseAccidentOrIllnessWithNoAnnualLimit_ShouldReturnNextStepNoAnnualLimitAndEndChatbot() {
        let (sut, _) = makeSUT()
        let expectation = XCTestExpectation(description: "Task")
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectClaimType)
        sut.chosenPetPolicy = Policy(id: "test_2", sumInsured: SumInsured(id: 0, limit: 200, consumed: 200, remainingValue: 0), status: .CANCELED, petId: 555)

        sut.handleClaimTypeSelection(parameters: NextStepParameters(sessionId: "", value: "Accident", action: .Accident)) { response in
            switch response {
            case .failure, .customError:
                XCTFail("Expected success result")
            case .success(let step):
                XCTAssertNotNil(step)
                XCTAssertEqual(sut.chatInteractionData?.currentId, .NoAnnualLimit)
                XCTAssertEqual(step.actions, [ChatAction(order: 1, label: "chatbot.action.finish.label".localized, value: "StopClaim", action: .StopClaim, isMainAction: false, userResponseMessage: "chatbot.action.finish.label".localized)])
            }
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 10.0)

    }

    func test_handleClaimTypeSelection_WhenStepIsSelectClaimTypeAndChooseAccidentOrIllnessWithAnnualLimit_ShouldReturnNextStep() {
        let (sut, _) = makeSUT()
        let expectation = XCTestExpectation(description: "Task")
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectClaimType)
        sut.chosenPetPolicy = Policy(id: "test_2", sumInsured: SumInsured(id: 0, limit: 200, consumed: 200, remainingValue: 500), status: .CANCELED, petId: 555)

        sut.handleClaimTypeSelection(parameters: NextStepParameters(sessionId: "", value: "Accident", action: .Accident)) { response in
            switch response {
            case .failure, .customError:
                XCTFail("Expected success result")
            case .success(let step):
                XCTAssertNotNil(step)
                XCTAssertEqual(sut.chatInteractionData?.currentId, .TypeDescription)
                XCTAssertEqual(step.actions, [])
            }
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 10.0)

    }

    func test_handleClaimTypeSelection_WhenStepIsSelectClaimTypeAndChooseOtherWithNoBenefits_ShouldReturnNextStepPWNoMoreBenefitsAndEndChatbot() {
        let (sut, services) = makeSUT()
        let expectation = XCTestExpectation(description: "Task")
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectClaimType)
        sut.chosenPetPolicy = Policy(id: "test_2",
                                          reimbursment: Reimbursment(id: 44, amount: 800),
                                          status: .ACTIVE,
                                          petId: 555,
                                          preventive: true,
                                          policyOfferId: 77)

        sut.handleClaimTypeSelection(parameters: NextStepParameters(sessionId: "", value: "Other", action: .Other)) { response in

            switch response {
            case .failure, .customError:
                XCTFail("Expected success result")
            case .success(let step):
                XCTAssertNotNil(step)
                XCTAssertEqual(sut.chatInteractionData?.currentId, .PWNoMoreBenefits)
                XCTAssertEqual(step.actions, [ChatAction(order: 1, label: "chatbot.action.finish.label".localized, value: "StopClaim", action: .StopClaim, isMainAction: false, userResponseMessage: "chatbot.action.finish.label".localized)])
                XCTAssertEqual(services.getCoverages.callCount, 1)
            }
            expectation.fulfill()
        }
        services.getCoverages.completeSuccessfully(with: [])
        wait(for: [expectation], timeout: 3.0)

    }

    func test_handleClaimTypeSelection_WhenStepIsSelectClaimTypeAndChooseOtherAndRequestFails_ShouldReturnError() {
        let (sut, services) = makeSUT()
        let expectation = XCTestExpectation(description: "Task")
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectClaimType)
        sut.chosenPetPolicy = Policy(id: "test_2",
                                          reimbursment: Reimbursment(id: 44, amount: 800),
                                          status: .ACTIVE,
                                          petId: 555,
                                          preventive: true,
                                          policyOfferId: 77)

        sut.handleClaimTypeSelection(parameters: NextStepParameters(sessionId: "", value: "Other", action: .Other)) { response in

            switch response {
            case .failure(let error), .customError(let error):
                XCTAssertEqual(error.error, "serverError.default".localized)
                XCTAssertEqual(services.getCoverages.callCount, 1)
            case .success:
                XCTFail("Expected failure result")
            }
            expectation.fulfill()
        }
        services.getCoverages.completeWithError()
        wait(for: [expectation], timeout: 3.0)

    }
}
