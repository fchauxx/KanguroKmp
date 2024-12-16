import XCTest
import KanguroChatbotData
import KanguroChatbotDomain
import KanguroSharedDomain

final class OnboardingChatbotTests: XCTestCase {

    func testStartSession() {
        let (network, sut) = makeSUT()
        let expectedSession = "34ac32af-f8b3-434e-b469-78e1b0fa2025_acb3690d-cb00-437c-aea8-2a8a19d82d00"
        network.result = RemoteSessionId(sessionId: expectedSession)
        let expec = expectation(description: "chatTestExpectations")
        sut.startSession(ChatbotJourney.RentersOnboarding.rawValue,
                         data: ["": ""]) { result in
            guard let session: String = try? result.get() else {
                XCTFail("Could not fetch session")
                expec.fulfill()
                return
            }
            XCTAssertEqual(session, expectedSession)
            expec.fulfill()
        }
        wait(for: [expec], timeout: 1.0)
    }

    func testFirstStep() {
        let (network, sut) = makeSUT()
        let result = RemoteChatbotPayload(
            messages: [
                RemoteChatbotMessage(
                    content: "¡Hola! Soy Javier y estaré ayudándote con el proceso de incorporación hoy.",
                    sender: "Chatbot",
                    type: "Text"
                ),
                RemoteChatbotMessage(
                    content: "Antes de comenzar, permíteme hacerte algunas preguntas.",
                    sender: "Chatbot",
                    type: "Text"
                ),
                RemoteChatbotMessage(
                    content: "¿Tienes cónyuge?",
                    sender: "Chatbot",
                    type: "Text"
                )
            ],
            action: RemoteChatbotAction(
                type: "SingleChoice",
                options: [
                    RemoteChatbotOption(
                        label: "No",
                        id: "No"
                    ),
                    RemoteChatbotOption(
                        label: "Sí",
                        id: "Yes"
                    )
                ]
            ),
            id: "RentersOnboardingSpouseQuestionStep"
        )
        let expectedResult = ChatbotStep(
            botMessages: [
                "¡Hola! Soy Javier y estaré ayudándote con el proceso de incorporación hoy.",
                "Antes de comenzar, permíteme hacerte algunas preguntas.",
                "¿Tienes cónyuge?"
            ],
            expectedUserResponseType: .singleChoice(
                options: ChatbotChoiceOptions(
                    options:  [
                        ChatbotChoice(id: "No", label: "No"),
                        ChatbotChoice(id: "Yes", label: "Sí")
                    ]
                )
            ),
            id: "RentersOnboardingSpouseQuestionStep"
        )
        network.result = result
        let expec = expectation(description: "chatTestExpectations")
        sut.getFirstStep("") { (result: Result<ChatbotStep, RequestError>) in
            guard let step: ChatbotStep = try? result.get() else {
                XCTFail("Could not fetch session")
                expec.fulfill()
                return
            }
            XCTAssertEqual(step, expectedResult)
            expec.fulfill()
        }
        wait(for: [expec], timeout: 1.0)
    }

    func testSpouseNameStep() {
        let (network, sut) = makeSUT()
        let result = RemoteChatbotPayload(
            messages: [
                RemoteChatbotMessage(
                    content: "¿Cuál es el nombre de tu cónyuge?",
                    sender: "Chatbot",
                    type: "Text"
                )
            ],
            action: RemoteChatbotAction(
                type: "Text"
            ),
            id: "RentersOnboardingSpouseNameStep"
        )
        let expectedResult = ChatbotStep(
            botMessages: [
                "¿Cuál es el nombre de tu cónyuge?"
            ],
            expectedUserResponseType: .text,
            id: "RentersOnboardingSpouseNameStep"
        )
        network.result = result
        let expec = expectation(description: "chatTestExpectations")
        sut.getFirstStep("") { (result: Result<ChatbotStep, RequestError>) in
            guard let step: ChatbotStep = try? result.get() else {
                XCTFail("Could not fetch session")
                expec.fulfill()
                return
            }
            XCTAssertEqual(step, expectedResult)
            expec.fulfill()
        }
        wait(for: [expec], timeout: 1.0)
    }

    func testSpouseBirthdateStep() {
        let (network, sut) = makeSUT()
        let result = RemoteChatbotPayload(
            messages: [
                RemoteChatbotMessage(
                    content: "¿Cuál es la fecha de nacimiento de tu cónyuge?",
                    sender: "Chatbot",
                    type: "Text"
                )
            ],
            action: RemoteChatbotAction(
                type: "Date",
                dateRange: RemoteChatbotDateRange(startDate: nil, endDate: "2020-08-28T15:07:02+00:00")
            ),
            id: "RentersOnboardingSpouseBirthdateStep"
        )
        let expectedResult = ChatbotStep(
            botMessages: [
                "¿Cuál es la fecha de nacimiento de tu cónyuge?"
            ],
            expectedUserResponseType: .date(constraints: .init(minDate: nil, maxDate: Date(timeIntervalSince1970: 1598627222))),
            id: "RentersOnboardingSpouseBirthdateStep"
        )
        network.result = result
        let expec = expectation(description: "chatTestExpectations")
        sut.getFirstStep("") { (result: Result<ChatbotStep, RequestError>) in
            guard let step: ChatbotStep = try? result.get() else {
                XCTFail("Could not fetch session")
                expec.fulfill()
                return
            }
            XCTAssertEqual(step, expectedResult)
            expec.fulfill()
        }
        wait(for: [expec], timeout: 1.0)
    }

    func testChildrenStep() {
        let (network, sut) = makeSUT()
        let result = RemoteChatbotPayload(
            messages: [
                RemoteChatbotMessage(
                    content: "¡Gracias! Ahora, ¿tienes hijos?",
                    sender: "Chatbot",
                    type: "Text"
                )
            ],
            action: RemoteChatbotAction(
                type: "SingleChoice",
                options: [
                    RemoteChatbotOption(
                        label: "No",
                        id: "No"
                    ),
                    RemoteChatbotOption(
                        label: "Sí",
                        id: "Yes"
                    )
                ]
            ),
            id: "RentersOnboardingChildrenQuestionStep"
        )
        let expectedResult = ChatbotStep(
            botMessages: [
                "¡Gracias! Ahora, ¿tienes hijos?"
            ],
            expectedUserResponseType: .singleChoice(options: ChatbotChoiceOptions(options: [
                ChatbotChoice(id: "No", label: "No"),
                ChatbotChoice(id: "Yes", label: "Sí")
            ])),
            id: "RentersOnboardingChildrenQuestionStep"
        )
        network.result = result
        let expec = expectation(description: "chatTestExpectations")
        sut.getFirstStep("") { (result: Result<ChatbotStep, RequestError>) in
            guard let step: ChatbotStep = try? result.get() else {
                XCTFail("Could not fetch session")
                expec.fulfill()
                return
            }
            XCTAssertEqual(step, expectedResult)
            expec.fulfill()
        }
        wait(for: [expec], timeout: 1.0)
    }

    func testChildBirthdateStep() {
        let (network, sut) = makeSUT()
        let result = RemoteChatbotPayload(
            messages: [
                RemoteChatbotMessage(
                    content: "¿Cuál es la fecha de nacimiento de tu hijo?",
                    sender: "Chatbot",
                    type: "Text"
                )
            ],
            action: RemoteChatbotAction(
                type: "Date",
                dateRange: RemoteChatbotDateRange(startDate: nil, endDate: "2020-08-28T15:07:02+00:00")
            ),
            id: "RentersOnboardingChildBirthdateStep"
        )
        let expectedResult = ChatbotStep(
            botMessages: [
                "¿Cuál es la fecha de nacimiento de tu hijo?"
            ],
            expectedUserResponseType: .date(constraints: .init(minDate: nil, maxDate: Date(timeIntervalSince1970: 1598627222))),
            id: "RentersOnboardingChildBirthdateStep"
        )
        network.result = result
        let expec = expectation(description: "chatTestExpectations")
        sut.getFirstStep("") { (result: Result<ChatbotStep, RequestError>) in
            guard let step: ChatbotStep = try? result.get() else {
                XCTFail("Could not fetch session")
                expec.fulfill()
                return
            }
            XCTAssertEqual(step, expectedResult)
            expec.fulfill()
        }
        wait(for: [expec], timeout: 1.0)
    }
}
