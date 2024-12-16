import Foundation
import Combine
import KanguroDesignSystem
import KanguroSharedDomain
import KanguroChatbotDomain

public class ChatbotViewModel: ObservableObject, ChatbotExternalFlowDelegate {
    
    // MARK: - Dependencies
    let createSessionService: CreateChatbotSessionUseCaseProtocol
    let getFirstChatbotStepService: GetFirstChatbotStepUseCaseProtocol
    let getNextChatbotStepService: GetNextChatbotStepUseCaseProtocol
    
    // MARK: - Wrapped Properties
    @Published var messages: [ChatMessage] = []
    @Published var userInputType: ChatMessageInputType = .freeText
    @Published var userChoices: [MultipleChoiceViewData] = []
    @Published var isLoadingNextStep: Bool = false
    
    // MARK: - Stored Properties
    weak var delegate: ChatbotLifeCycleDelegate?
    var choosenDate: Date?
    var chatbotSessionId: String?
    var chatbotJourney: ChatbotJourney
    var policyId: String
    var botMessageQueue: ChatMessageQueue
    var userMessageQueue: ChatMessageQueue
    var botMessageCancellable: AnyCancellable?
    var userMessageCancellable: AnyCancellable?
    var isBotFirstMessage = true
    var isOnExternalFlow: Bool = false
    var currentFlow: ChatbotExternalFlow?
    
    // MARK: - Computed Properties
    var canChatbotInputShow: Bool {
        return botMessageQueue.isEmpty && chatbotSessionId != nil
    }
    
    // MARK: - Initializers
    public init(delegate: ChatbotLifeCycleDelegate?,
                createSessionService: CreateChatbotSessionUseCaseProtocol,
                getFirstChatbotStepService: GetFirstChatbotStepUseCaseProtocol,
                getNextChatbotStepService: GetNextChatbotStepUseCaseProtocol,
                chatbotJourney: ChatbotJourney,
                policyId: String,
                botMessageQueue: ChatMessageQueue,
                userMessageQueue: ChatMessageQueue,
                currentFlow: ChatbotExternalFlow? = nil) {
        self.delegate = delegate
        self.createSessionService = createSessionService
        self.getFirstChatbotStepService = getFirstChatbotStepService
        self.getNextChatbotStepService = getNextChatbotStepService
        self.chatbotJourney = chatbotJourney
        self.policyId = policyId
        self.botMessageQueue = botMessageQueue
        self.userMessageQueue = userMessageQueue
        self.currentFlow = currentFlow
        
        botMessageCancellable = self.botMessageQueue.messagePublisher
            .sink() {
                guard !$0.isEmpty else { return }
                self.messages.append(ChatMessage(message: $0, sender: .bot, isFirstMessage: self.isBotFirstMessage))
                guard $0 != "...WRITING..." else { return }
                self.isBotFirstMessage = false
            }
        
        userMessageCancellable = self.userMessageQueue.messagePublisher
            .sink() {
                guard !$0.isEmpty else { return }
                let isUserFirstMessage = self.messages.last?.sender == ChatMessageSender.bot
                self.messages.append(ChatMessage(message: $0, sender: .user, isFirstMessage: isUserFirstMessage))
            }
    }
    
    // MARK: - Public Methods
    public func userResponse(_ response: String, responseId: String? = nil) {
        
        guard !response.isEmpty, botMessageQueue.isEmpty else { return }
        
        if !isOnExternalFlow {
            switch userInputType {
            case .choiceButton:
                guard !userChoices.filter({ $0.choice == response }).isEmpty else { return }
            default: break
            }
            currentFlow = nil
            appendUserNewMessage(response)
            getNextChatbotStep(id: responseId, response: response)
        } else {
            guard let delegate, let currentFlow else { return }
            delegate.didAskExternalFlow(currentFlow)
        }
    }
    
    public func appendUserNewMessage(_ text: String) {
        userMessageQueue.addMessages([text])
    }
    
    // MARK: - Network
    public func createChatbotSession(completion: @escaping SimpleClosure) {
        assert(self.chatbotSessionId == nil)
        
        self.isLoadingNextStep = true
        
        createSessionService.execute(
            ChatbotJourneyParameters(
                journey: chatbotJourney,
                data: ["policyId": policyId]
            )
        ) { [weak self] result in
            guard let self else { return }
            
            switch result {
            case .success(let sessionId):
                self.chatbotSessionId = sessionId
                completion()
            case .failure(let error):
                debugPrint(error)
            }
        }
    }
    
    public func getFirstChatbotStep() {
        assert(self.chatbotSessionId != nil)
        guard let chatbotSessionId else { return }
        
        self.isLoadingNextStep = true
        
        getFirstChatbotStepService.execute(UserInputParameters(chatbotSession: chatbotSessionId)) { [weak self] result in
            guard let self else { return }
            
            switch result {
            case .success(let step):
                processNextStep(step)
            case .failure(let error):
                debugPrint(error)
            }
        }
    }
    
    public func getNextChatbotStep(id: Any? = nil, response: Any) {
        assert(self.chatbotSessionId != nil)
        guard let chatbotSessionId else { return }
        
        self.isLoadingNextStep = true
        
        getNextChatbotStepService.execute(
            UserInputParameters(
                input: id ?? response,
                chatbotSession: chatbotSessionId
            )
        ) { [weak self] result in
            
            guard let self else {return}
            
            switch result {
            case .success(let step):
                processNextStep(step)
            case .failure(let error):
                debugPrint(error) // TODO: Show error dialog
            }
        }
    }
    
    public func processNextStep(_ step: ChatbotStep) {
        isBotFirstMessage = true
        botMessageQueue.addMessages(mapChatbotStep(step: step))
        self.isLoadingNextStep = false
        
        // If do not contains inputType, its external flow (Pledge of Honor, scheduled item, ...)
        if let inputType = mapChatbotInputType(step: step) {
            self.userInputType = inputType
        } else {
            mapChatbotExternalFlow(step: step)
        }
    }
}

extension ChatbotViewModel {
    
    public func didReceiveExternalFlowResponse(_ response: Any, chatText: String?) {
        if let chatText {
            appendUserNewMessage(chatText)
        }
        
        getNextChatbotStep(response: response)
    }
}
