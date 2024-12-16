import UIKit
import Resolver
import Combine
import KanguroUserDomain
import KanguroSharedDomain
import KanguroPetDomain
import KanguroStorageDomain
import KanguroNetworkDomain

enum ChatbotServiceType {
    case local
    case remote
}

enum ChatbotViewState {

    case started
    case dataChanged
    case loading
    case newMessageAdded
    case cleanedMessages
    case removedImage
    case requestFailed
    case finishedPetInfo
    case finishedClaim
    case requestSucceeded
    case getPetIdSucceeded
    case getPetIdFailed
    case imageFormatNotSupported
}

class ChatbotViewModel {

    // MARK: - Dependencies
    @LazyInjected var decoder: JSONDecoder
    @LazyInjected var network: NetworkProtocol
    @LazyInjected var secureStorage: SecureStorage

    // MARK: - Published Properties
    @Published var state: ChatbotViewState = .started

    // MARK: - Stored Properties
    var chatbotService: ChatbotRepositoryProtocol?
    var chatbotServiceType: ChatbotServiceType
    var data: ChatbotData
    var loadedMessages: [ChatMessage] = []
    var partialLoadedMessages: [ChatMessage] = []
    var filesPath: [Int: String] = [:]
    var lastMessageSentIndex: Int = 0
    var isImageEditable = false
    var currentStepInputType: ChatbotInteractionType = .TextInput

    var claimId: String?
    var petInfo: ChatPetInfo?
    var signatureText: String?
    var filesQuality = 0.35
    var requestError: String = ""
    var delay = 1.0
    var hasSignature = false

    var communicationParameter = PetCommunicationParameters()

    // MARK: - Computed Properties
    var sessionId: String? {
        return data.sessionId ?? nil
    }
    var isNewSession: Bool {
        return (sessionId == nil || sessionId == "")
    }
    var shouldGetPetInfo: Bool {
        return ((petInfo == nil) && hasSignature)
    }
    var didFinishMessages: Bool {
        return partialLoadedMessages.isEmpty && (state != .loading)
    }
    var concatenatedFilesPath: String {
        var concatenatedFilesPath = ""
        filesPath.forEach { _, value in
            let concatenatedValue = concatenatedFilesPath.isEmpty ? value : "|\(value)"
            concatenatedFilesPath = concatenatedFilesPath + concatenatedValue
        }
        return concatenatedFilesPath
    }

    // MARK: - Steps
    var addMoreActions: [ChatAction] {
        return [ChatAction(order: 1,
                           label: "chatbot.addMore".localized,
                           value: "chatbot.addMore".localized,
                           action: .UploadFile,
                           isMainAction: false),
                ChatAction(order: 2,
                           label: "chatbot.done".localized,
                           value: "chatbot.done".localized,
                           action: .FinishFilesUpload,
                           isMainAction: true)]
    }
    var addMoreFilesStep: ChatInteractionStep? {
        guard let sessionId = data.chatInteractionStep?.sessionId else { return nil }
        return ChatInteractionStep(type: .ButtonList,
                                   orientation: .Horizontal,
                                   actions: addMoreActions,
                                   messages: [],
                                   sessionId: sessionId)
    }
    var confirmInputActions: [ChatAction] {
        return [ChatAction(order: 1,
                           label: "chatbot.edit".localized,
                           value: "chatbot.edit".localized,
                           action: .EditInput,
                           isMainAction: false),
                ChatAction(order: 2,
                           label: "chatbot.confirm".localized,
                           value: "chatbot.confirm".localized,
                           action: .UserCustomInput,
                           isMainAction: true)]
    }
    var confirmInputFilesStep: ChatInteractionStep? {
        guard let sessionId = data.chatInteractionStep?.sessionId else { return nil }
        return ChatInteractionStep(type: .ButtonList,
                                   orientation: .Horizontal,
                                   actions: confirmInputActions,
                                   messages: [],
                                   sessionId: sessionId)
    }

    // MARK: - Initializers
    init(data: ChatbotData,
         chatbotServiceType: ChatbotServiceType) {
        self.data = data
        self.chatbotServiceType = chatbotServiceType
    }
}

// MARK: - Public Methods
extension ChatbotViewModel {

    func starterSetup() {
        setupChatbotService()
        if isNewSession {
            switch data.type {
            case .Communication:
                return
            case .Central:
                appendChatInteractionStepMessages()
            default:
                clearMessages()
                startSession()
            }
        } else {
            guard let sessionId = sessionId else { return }
            nextStep(NextStepParameters(sessionId: sessionId))
        }
    }

    func appendUserNewMessage(content: String, type: ChatMessageFormat = .Text) {
        let chatMessage = ChatMessage(format: type,
                                      content: content,
                                      order: loadedMessages.count,
                                      sender: .User)

        appendPartialMessages(messages: [chatMessage])
    }

    func appendChatInteractionStepMessages() {
        var messages = data.chatInteractionStep?.messages ?? []
        for index in 0..<messages.count {
            if messages[index].sender == nil {
                messages[index].sender = .Bot
            }
        }
        appendPartialMessages(messages: messages)
    }

    func addUserAnswer(_ nextStepData: NextStepParameters) {
        if let chatAction = data.chatInteractionStep?.actions.first(where: {
            $0.value == nextStepData.value
        }) {
            appendUserNewMessage(content: chatAction.label)
        }
    }

    func customNextStepCall(content: String, action: ChatbotAction) {
        guard let step = data.chatInteractionStep else { return }

        let formattedContent = !(step.type == .CurrencyInput) ? content : content.onlyNumbersAndPuntuaction
        nextStep(NextStepParameters(sessionId: step.sessionId,
                                    value: formattedContent,
                                    action: action))
    }

    func update(step: ChatInteractionStep) {
        data.chatInteractionStep = step
        appendPartialMessages(messages: step.messages)
        state = .requestSucceeded
    }

    func update(communicationParameterId: String) {
        self.communicationParameter.id = communicationParameterId
    }

    func isFirstMessage(index: Int) -> Bool {
        let currentMessage = loadedMessages[index]
        let previousMessage = loadedMessages[safe: index - 1]
        return (currentMessage.sender != previousMessage?.sender)
    }

    func clearMessages() {
        loadedMessages = []
        state = .cleanedMessages
    }

    func postFiles() {
        guard let step = data.chatInteractionStep else { return }
        let action: ChatbotAction = (step.type == .UploadPetPicture) ? .UploadImage : .UploadFile
        let nextStepParameters = NextStepParameters(sessionId: step.sessionId,
                                                    value: concatenatedFilesPath,
                                                    action: action)
        nextStep(nextStepParameters)
        filesPath = [:]
    }

    func updatePet(_ petId: Int) {
        data.currentPetId = petId
        startSession()
    }

    func setupCustomInputConfirmStep(content: String) {
        appendUserNewMessage(content: content)
        data.chatInteractionStep = confirmInputFilesStep
        state = .requestSucceeded
    }
}

// MARK: - Private Methods
extension ChatbotViewModel {
    
    func setupChatbotService() {
        switch chatbotServiceType {
        case .local:
            chatbotService = ChatbotRepository(service: ChatbotServiceFactory.makeLocal(delegate: self,
                                                                                        mainDispatcher: MainDispatcher(),
                                                                                        network: network,
                                                                                        secureStorage: secureStorage))
        case .remote:
            chatbotService = ChatbotRepository(service: ChatbotServiceFactory.makeRemote())
        }
    }

    func updateCommunicationParameterMessages(message: String) {
        communicationParameter.message = message
    }

    func updateCommunicationParameterFiles(file: String) {
        communicationParameter.files.append(file)
        state = .dataChanged
    }

    private func appendPartialMessages(messages: [ChatMessage]) {
        partialLoadedMessages.append(contentsOf: messages)
        appendMessagesWithDelay()
    }

    private func appendMessagesWithDelay() {
        guard let firstItem = partialLoadedMessages.first else { return }
        loadedMessages.append(firstItem)
        partialLoadedMessages.remove(at: 0)
        state = .newMessageAdded

        DispatchQueue.main.asyncAfter(deadline: .now() + delay) { [weak self] in
            guard let self else { return }
            self.appendMessagesWithDelay()
        }
    }

    func handleNextStepResponse(_ newStep: ChatInteractionStep) {
        data.chatInteractionStep = newStep
        appendChatInteractionStepMessages()
        lastMessageSentIndex = loadedMessages.count
        state = data.isPetInfoFinished ? .finishedPetInfo : .requestSucceeded
    }
}

// MARK: - File Handler Methods
extension ChatbotViewModel {

    func manageSignature(image: UIImage) {
        let encodedImage = image.jpegData(compressionQuality: filesQuality)?.base64EncodedString() ?? ""
        signatureText = encodedImage
        appendUserNewMessage(content: "newClaim.pencil.emoji".localized)
        customNextStepCall(content: "{\"fileInBase64\": \"\(encodedImage)\", \"fileExtension\": \".jpg\"}",
                           action: .Signature)
        hasSignature = true
    }

    func manageBankAccount(account: BankAccount) {
        guard let accountFormatted = account.jsonFormatted else { return }
        appendUserNewMessage(content: "newClaim.bank.emoji".localized)
        customNextStepCall(content: accountFormatted,
                           action: .Reimbursement)
    }

    func manageSingleImage(_ image: UIImage) {
        let targetSize = CGSize(width: 600, height: 600)
        let scaledImage = image.scalePreservingAspectRatio(targetSize: targetSize)

        guard let encodedImage = scaledImage.jpegData(compressionQuality: filesQuality)?.base64EncodedString() else { return }
        let base64Image = "{\"fileInBase64\": \"\(encodedImage)\", \"fileExtension\": \".jpg\"}"
        let type: ChatMessageFormat = (data.chatInteractionStep?.type == .UploadPetPicture) ? .PetImage : .Image
        appendUserNewMessage(content: encodedImage, type: type)

        appendFile(base64Image)

        if data.isCommunicationType {
            updateCommunicationParameterFiles(file: base64Image)
            appendFile(getBase64ImageFormatted(image))
        }
    }

    func manageImages(images: [UIImage]) {
        images.forEach { manageSingleImage($0) }
        if data.chatInteractionStep?.type == .UploadPetPicture {
            postFiles()
        } else {
            data.chatInteractionStep = addMoreFilesStep
            state = .requestSucceeded
        }
    }

    func manageFile(file: URL) {
        do {
            let fileData = try Data.init(contentsOf: file)
            let encodedFile: String = fileData.base64EncodedString(options: NSData.Base64EncodingOptions.init(rawValue: 0))

            appendUserNewMessage(content: "newClaim.file.emoji".localized)
            let base64File = "{\"fileInBase64\": \"\(encodedFile)\", \"fileExtension\": \".pdf\"}"

            appendFile(base64File)

            if data.isCommunicationType {
                updateCommunicationParameterFiles(file: base64File)
            } else {
                data.chatInteractionStep = addMoreFilesStep
                state = .requestSucceeded
            }
        } catch {
            print("Error: Failed trying to initialize data")
        }
    }

    private func appendFile(_ filePath: String) {
        filesPath[loadedMessages.count - 1] = filePath
    }

    func updateFilesPathIndex() {
        var newFilesPath: [Int: String] = [:]
        var filesCount = filesPath.count

        filesPath.forEach { _, value in
            newFilesPath[loadedMessages.count - filesCount] = value
            filesCount -= 1
        }
        filesPath = newFilesPath
    }

    func removeImage(index: Int) {
        filesPath.removeValue(forKey: index)
        loadedMessages.remove(at: index)
        updateFilesPathIndex()
        state = .removedImage
    }

    func isDeletableImage(index: Int) -> Bool {
        return (index >= lastMessageSentIndex)
    }

    func getBase64ImageFormatted(_ image: UIImage) -> String {
        return "{\"fileInBase64\": \"\(image)\", \"fileExtension\": \".jpg\"}"
    }

    private func getBase64Image(_ image: UIImage) -> String {
        let targetSize = CGSize(width: 600, height: 600)
        let scaledImage = image.scalePreservingAspectRatio(targetSize: targetSize)
        return scaledImage.jpegData(compressionQuality: filesQuality)?.base64EncodedString() ?? ""
    }

    func getConvertedImage(encodedImage: String) -> UIImage {
        guard let imageData = Data(base64Encoded: encodedImage),
              let image = UIImage(data: imageData) else { return UIImage() }
        return image
    }

    func contentAsSummary(content: String) -> ChatSummary? {
        guard let data = content.data(using: .utf8) else { return nil }
        return try? decoder.decode(ChatSummary.self, from: data)
    }
}

// MARK: Network
extension ChatbotViewModel {

    func startSession() {
        guard let type = data.type else { return }
        state = .loading
        let parameters = SessionStartParameters(petId: data.currentPetId, 
                                                type: type)
        chatbotService?.postStartSession(parameters: parameters) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error), .customError(let error):
                self.requestError = error.error ?? "serverError.default".localized
                self.state = .requestFailed
            case .success (let newChatInteractionStep):
                self.data.chatInteractionStep = newChatInteractionStep
                self.appendChatInteractionStepMessages()
                self.state = .requestSucceeded
            }
        }
    }

    func nextStep(_ parameters: NextStepParameters?) {
        guard let parameters else { return }
        state = .loading
        chatbotService?.postNextStep(parameters: parameters) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error), .customError(let error):
                self.requestError = error.error ?? "serverError.default".localized
                self.state = .requestFailed
            case .success (let newChatInteractionStep):
                self.handleNextStepResponse(newChatInteractionStep)
            }
        }
    }

    func getPetInfo() {
        guard let sessionId = data.chatInteractionStep?.sessionId else { return }
        chatbotService?.getPetInfo(parameters: GetPetIdParameters(sessionId: sessionId)) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error), .customError(let error):
                self.requestError = error.error ?? "serverError.default".localized
                self.state = .getPetIdFailed
            case .success (let petInfo):
                self.petInfo = petInfo
                self.state = .getPetIdSucceeded
            }
        }
    }
}

extension ChatbotViewModel: ChatbotDelegate {

    func didReceivedClaim(claimId: String) {
        self.claimId = claimId
    }
}
