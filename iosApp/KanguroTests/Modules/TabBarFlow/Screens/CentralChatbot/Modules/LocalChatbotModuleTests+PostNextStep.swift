import XCTest
@testable import Kanguro
import KanguroSharedDomain
import KanguroPetDomain

extension LocalChatbotModuleTests {
    func test_postNextStep_WhenStepIsPledgeOfHonour_ShouldReturnNextStepSelectClaimType() {

        let (sut, _) = makeSUT()
        let expectation = XCTestExpectation(description: "Task")
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .PledgeOfHonour)

        sut.postNextStep(parameters: NextStepParameters(sessionId: "")) { response in
            switch response {
            case .failure, .customError:
                XCTFail("Expected success result")
            case .success(let step):
                XCTAssertNotNil(step)
                XCTAssertEqual(step.actions, [
                    ChatAction(order: 1, label: "chatbot.action.accident.label".localized, value: "chatbot.action.accident.label".localized, action: .Accident, isMainAction: nil, userResponseMessage: nil),
                    ChatAction(order: 2, label: "chatbot.action.illness.label".localized, value: "chatbot.action.illness.label".localized, action: .Illness, isMainAction: nil, userResponseMessage: nil)])
            }
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 3.0)

    }

    func test_postNextStep_WhenStepIsSelectClaimTypeAndUserChooseAccidentAndPolicyIsFine_ShouldReturnNextStepTypeDescription() {

        let (sut, _) = makeSUT()
        let expectation = XCTestExpectation(description: "Task")
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .SelectClaimType)
        sut.chosenPetPolicy = Policy(id: "test", sumInsured: SumInsured(id: 10, limit: 300, consumed: 250, remainingValue: 50), petId: 777)

        sut.postNextStep(parameters: NextStepParameters(sessionId: "", value: "Accident", action: .Accident)) { response in
            switch response {
            case .failure, .customError:
                XCTFail("Expected success result")
            case .success(let step):
                XCTAssertNotNil(step)
                XCTAssertEqual(sut.chatInteractionData?.currentId, .TypeDescription)
                XCTAssertEqual(step.type, .TextInput)
                XCTAssertEqual(step.actions, [])
            }
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 3.0)

    }

    func test_postNextStep_WhenStepIsTypeDescription_ShouldReturnNextStepInvoiceDate() {

        let (sut, _) = makeSUT()
        let expectation = XCTestExpectation(description: "Task")
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .TypeDescription)

        sut.postNextStep(parameters: NextStepParameters(sessionId: "")) { response in
            switch response {
            case .failure, .customError:
                XCTFail("Expected success result")
            case .success(let step):
                XCTAssertNotNil(step)
                XCTAssertEqual(sut.chatInteractionData?.currentId, .SelectDate)
                XCTAssertEqual(step.type, .DateInput)
                XCTAssertEqual(step.actions, [])
            }
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 3.0)

    }

    func test_postNextStep_WhenStepIsAttachDocuments_ShouldReturnNextStepSendPetPicture() {
        let (sut, _) = makeSUT()
        let expectation = XCTestExpectation(description: "Task")
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .AttachDocuments)

        sut.postNextStep(parameters: NextStepParameters(sessionId: "")) { response in
            switch response {
            case .failure, .customError:
                XCTFail("Expected success result")
            case .success(let step):
                XCTAssertNotNil(step)
                XCTAssertEqual(sut.chatInteractionData?.currentId, .PetHealthStatePicture)
                XCTAssertEqual(step.actions, [])
            }
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 3.0)

    }

    func test_postNextStep_WhenStepIsSendPetPicture_ShouldReturnNextStepInvoiceTotal() {
        let (sut, _) = makeSUT()
        let expectation = XCTestExpectation(description: "Task")
        sut.chatInteractionData = ChatInteractionData(pets: pets, currentId: .PetHealthStatePicture)

        sut.postNextStep(parameters: NextStepParameters(sessionId: "")) { response in
            switch response {
            case .failure, .customError:
                XCTFail("Expected success result")
            case .success(let step):
                XCTAssertNotNil(step)
                XCTAssertEqual(sut.chatInteractionData?.currentId, .TypeTotalAmount)
                XCTAssertEqual(step.type, .CurrencyInput)
                XCTAssertEqual(step.actions, [])
            }
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 3.0)

    }

}
