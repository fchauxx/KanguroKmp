import UIKit
import Combine
import Resolver
import KanguroSharedDomain
import KanguroPetDomain
import KanguroAnalyticsDomain

class CommunicationChatbotViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var createPetCommunicationService: CreatePetCommunicationsUseCaseProtocol
    @LazyInjected var getPetCommunicationService: GetPetCommunicationsUseCaseProtocol
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol

    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    var claimId: String
    var uploadFilesStep = ChatInteractionStep(type: .UploadPicture,
                                              actions: [],
                                              messages: [],
                                              sessionId: "")
    
    var requestError = ""
    var communications: [Communication] = []
    var order = 0
    
    // MARK: - Computed Properties
    var communicationsSortedByDate: [Communication] {
        let sortedList: [Communication]? = communications.sorted(by: {
            guard let date1 = $0.createdAt,
                  let date2 = $1.createdAt else { return false }
            return (date1) < (date2)
        })
        return sortedList ?? communications
    }
    var addMoreActions: [ChatAction] {
        return [ChatAction(order: 0,
                           label: "chatbot.addMore".localized,
                           value: "chatbot.addMore".localized,
                           action: .UploadFile,
                           isMainAction: false),
                ChatAction(order: 1,
                           label: "chatbot.done".localized,
                           value: "chatbot.done".localized,
                           action: .FinishCommunication,
                           isMainAction: true)]
    }
    var addMoreFilesStep: ChatInteractionStep {
        return ChatInteractionStep(type: .ButtonList,
                                   orientation: .Horizontal,
                                   actions: addMoreActions,
                                   messages: [],
                                   sessionId: "")
    }
    
    // MARK: - Initializers
    init(claimId: String) {
        self.claimId = claimId
    }
}

// MARK: - Analytics
extension CommunicationChatbotViewModel {

    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .CommunicationChatbot)
    }
}

// MARK: Private Methods
extension CommunicationChatbotViewModel {
    
    func getConvertedMessage(communication: Communication) -> ChatMessage {
        
        let format: ChatMessageFormat = (communication.type == .Text) ? .Text : .Image
        let content = (communication.type == .Text) ? communication.message : communication.fileURL
        
        return ChatMessage(format: format,
                           content: content ?? "",
                           order: order,
                           sender: communication.sender == .User ? .User : .Bot)
    }
    
    func appendMessages() {
        for communication in communicationsSortedByDate {
            if communication.isValidMessage {
                uploadFilesStep.messages.append(getConvertedMessage(communication: communication))
                order += 1
            }
        }
        state = .dataChanged
    }
}

// MARK: Network
extension CommunicationChatbotViewModel {
    
    func getCommunications() {
        state = .loading
        let parameters = PetClaimParameters(id: claimId)
        getPetCommunicationService.execute(parameters) { response in
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let communications):
                self.communications = communications
                self.appendMessages()
            }
        }
    }
    
    func postCommunications(parameters: PetCommunicationParameters) {
        state = .loading
        createPetCommunicationService.execute(parameters) { response in
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let communications):
                self.communications = communications
                self.state = .requestSucceeded
            }
        }
    }
}
