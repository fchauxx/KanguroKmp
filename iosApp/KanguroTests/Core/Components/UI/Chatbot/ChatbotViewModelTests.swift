@testable import Kanguro
import XCTest
import SwiftUI
import KanguroUserDomain

class ChatbotViewModelTests: XCTestCase {
    
    // MARK: - Stored Properties
    var viewModel: ChatbotViewModel!
    var mockedData: MockedChatbotViewModelData!
    var actions: [ChatAction] = []
    var messages: [ChatMessage] = []
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        mockedData = MockedChatbotViewModelData()
        viewModel = ChatbotViewModel(data: mockedData.data,
                                     chatbotServiceType: .remote)
        actions = mockedData.actions
        messages = mockedData.messages
        viewModel.filesPath = [1: "value", 2: "secondValue"]
    }
}

// MARK: - Computed Properties
extension ChatbotViewModelTests {
    
    func testSessionId() {
        XCTAssertEqual(mockedData.data.sessionId,
                       viewModel.sessionId)
    }
    
    func testNewSession() {
        XCTAssertFalse(viewModel.isNewSession)
    }
    
    func testCurrentMessagesCount() {
        XCTAssertEqual(mockedData.data.chatInteractionStep?.messages.count,
                       viewModel.data.currentMessagesCount)
    }
    
    func testMessagesCountNil() {
        let newData = ChatbotData(type: .AdditionalInformation,
                                  currentPetId: 0,
                                  sessionId: "0",
                                  chatInteractionStep: nil)
        viewModel = ChatbotViewModel(data: newData,
                                     chatbotServiceType: .remote)
        XCTAssertEqual(viewModel.data.currentMessagesCount,
                       0)
    }
    
    func testChatStepSessionId() {
        XCTAssertEqual(mockedData.data.chatInteractionStep?.sessionId,
                       viewModel.data.chatInteractionStepSessionId)
    }
    
    func testStepSessionIdNil() {
        let newData = ChatbotData(type: .AdditionalInformation,
                                  currentPetId: 0,
                                  sessionId: "0",
                                  chatInteractionStep: nil)
        viewModel = ChatbotViewModel(data: newData,
                                     chatbotServiceType: .remote)
        XCTAssertEqual(viewModel.data.chatInteractionStepSessionId,
                       "")
    }
    
    func testShouldGetPetInfo() {
        viewModel.hasSignature = true
        viewModel.petInfo = nil
        XCTAssertTrue(viewModel.shouldGetPetInfo)
    }
    
    func testConcatenatedFilesPath() {
        XCTAssertTrue(viewModel.concatenatedFilesPath.contains("|"))
        
        viewModel.filesPath = [1: "value"]
        XCTAssertEqual(viewModel.filesPath.values.first,
                       viewModel.concatenatedFilesPath)
    }
}

// MARK: - Methods
extension ChatbotViewModelTests {
    
    func testIsStartedSetup() {
        XCTAssertFalse(viewModel.data.isStarterSetup)
    }
    
    func testIsStarterSetupAddInfo() {
        let emptyActionsStep = ChatInteractionStep(type: .ButtonList,
                                                   actions: [],
                                                   messages: [],
                                                   sessionId: "0")
        let newData = ChatbotData(type: .AdditionalInformation,
                                  currentPetId: 0,
                                  sessionId: "0",
                                  chatInteractionStep: emptyActionsStep)
        viewModel = ChatbotViewModel(data: newData,
                                     chatbotServiceType: .remote)
        XCTAssertTrue(viewModel.data.isStarterSetup)
    }
    
    func testIsStarterSetupNil() {
        let newData = ChatbotData(type: .AdditionalInformation,
                                  currentPetId: 0,
                                  sessionId: "0",
                                  chatInteractionStep: nil)
        viewModel = ChatbotViewModel(data: newData,
                                     chatbotServiceType: .remote)
        XCTAssertFalse(viewModel.data.isStarterSetup)
    }
    
    func testStarterSetupNotNewSession() {
        viewModel.starterSetup()
        XCTAssertTrue(viewModel.state == .loading)
    }
    
    func testIsFirstMessageTrue() {
        viewModel.loadedMessages = messages
        XCTAssertTrue(viewModel.isFirstMessage(index: 0))
    }
    
    func testIsFirstMessageFalse() {
        viewModel.loadedMessages = messages
        XCTAssertFalse(viewModel.isFirstMessage(index: 1))
    }
    
    func testAppendUserNewMessage() {
        let content = "content"
        viewModel.appendUserNewMessage(content: content)
        let messageContent = viewModel.data.chatInteractionStep?.messages.last?.content ?? ""
        XCTAssertEqual(messageContent, content)
    }
    
    func testAddUserAnswer() {
        let nextStepData = NextStepParameters(sessionId: "123",
                                              value: "value",
                                              action: .none)
        viewModel.addUserAnswer(nextStepData)
        let messageContent = viewModel.loadedMessages.first?.content ?? ""
        XCTAssertEqual(messageContent, actions[0].label)
    }
    
    @available(iOS 16.0, *)
    func testUpdateStep() {
        guard let step = viewModel.confirmInputFilesStep else { return }
        viewModel.update(step: step)
        XCTAssertTrue(viewModel.loadedMessages.contains(step.messages))
    }
    
    func testUpdateCommunicationParameterId() {
        let id = "id"
        viewModel.update(communicationParameterId: id)
        XCTAssertEqual(id, viewModel.communicationParameter.id)
    }
    
    func testPostFiles() {
        viewModel.postFiles()
        XCTAssertEqual(viewModel.filesPath, [:])
    }
    
    func testUpdatePetId() {
        let petId = 1
        viewModel.updatePet(petId)
        XCTAssertEqual(viewModel.data.currentPetId, petId)
    }
    
    func testSetupCustomInputConfirmStep() {
        viewModel.setupCustomInputConfirmStep(content: "")
        XCTAssertEqual(viewModel.data.chatInteractionStep?.messages,
                       viewModel.confirmInputFilesStep?.messages)
    }
    
    func testUpdateCommunicationParameterMessages() {
        let message = "message"
        viewModel.updateCommunicationParameterMessages(message: message)
        XCTAssertEqual(viewModel.communicationParameter.message, message)
    }
    
    func testUpdateCommunicationParameterFiles() {
        let file = "file"
        viewModel.updateCommunicationParameterFiles(file: file)
        XCTAssertEqual(viewModel.communicationParameter.files.last,
                       file)
    }
    
    func testHandleNextStepResponse() {
        guard let step = viewModel.confirmInputFilesStep else { return }
        viewModel.handleNextStepResponse(step)
        XCTAssertEqual(step.messages,
                       viewModel.data.chatInteractionStep?.messages)
    }
    
    @available(iOS 16.0, *)
    func testUpdateFilesPathIndex() {
        viewModel.updateFilesPathIndex()
        XCTAssertTrue(viewModel.filesPath.keys.contains([-1]))
    }
    
    func testRemoveImage() {
        let index = viewModel.filesPath.count - 1
        let chatMessage = ChatMessage(format: .Text,
                                      content: "", order: 0,
                                      sender: .User)
        viewModel.loadedMessages = [chatMessage, chatMessage]
        
        viewModel.removeImage(index: index)
        XCTAssertNil(viewModel.filesPath[index])
    }
    
    func testIsDeletableImage() {
        XCTAssertTrue(viewModel.isDeletableImage(index: 1))
    }
    
    func testGetBase64ImageFormatted() {
        let image = UIImage()
        let finalString = "{\"fileInBase64\": \"\(image)\", \"fileExtension\": \".jpg\"}"
        XCTAssertEqual(finalString, viewModel.getBase64ImageFormatted(image))
    }
}

// MARK: - File Handler Methods
extension ChatbotViewModelTests {
    
    func testManageSignature() {
        guard let image = UIImage(named: "ic-close") else { return }
        let emoji = "newClaim.pencil.emoji".localized
        viewModel.manageSignature(image: image)
        XCTAssertEqual(viewModel.loadedMessages.first?.content,
                       emoji)
    }
    
    func testManageBankAccount() {
        let account = BankAccount()
        let emoji = "newClaim.bank.emoji".localized
        viewModel.manageBankAccount(account: account)
        XCTAssertEqual(viewModel.loadedMessages.first?.content,
                       emoji)
    }
    
    func testManageImage() {
        guard let image = UIImage(named: "ic-close") else { return }
        let targetSize = CGSize(width: 600, height: 600)
        let scaledImage = image.scalePreservingAspectRatio(targetSize: targetSize)
        let encodedImage = scaledImage.jpegData(compressionQuality: 0.35)?.base64EncodedString()
        viewModel.manageImages(images: [image])
        XCTAssertEqual(viewModel.loadedMessages.first?.content,
                       encodedImage)
    }
    
    func testGetConvertedImage() {
        guard let image = UIImage(named: "ic-close") else { return }
        let encodedImage = image.jpegData(compressionQuality: 0.1)?.base64EncodedString()
        let convertedImage = viewModel.getConvertedImage(encodedImage: encodedImage!)
        XCTAssertTrue(convertedImage != UIImage())
    }
    
    func testGetConvertedImageNil() {
        let nilEncodedImage = ""
        let convertedImage = viewModel.getConvertedImage(encodedImage: nilEncodedImage)
        XCTAssertTrue(convertedImage == UIImage())
    }
    
    func testManageFileCatch() {
        let content = "newClaim.file.emoji".localized
        viewModel.manageFile(file: URL(fileURLWithPath: ""))
        XCTAssertNotEqual(viewModel.loadedMessages.first?.content,
                          content)
    }
    
    func testContentSummary() {
        let petName = "Fluffy"
        let content = "{\"pet\":\"\(petName)\",\"claim\":\"Accident\",\"claimId\":\"1\",\"date\":\"2022-09-07T00:00:00+00:00\",\"attachments\":1,\"amount\":23.23}"
        
        let resultSummary = viewModel.contentAsSummary(content: content)
        XCTAssertEqual(resultSummary?.pet, petName)
    }
}
