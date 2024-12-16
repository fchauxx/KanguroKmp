@testable import Kanguro
import XCTest
import SwiftUI

class ChatbotViewTests: XCTestCase {
    
    // MARK: - Stored Properties
    var view: ChatbotView!
    var viewModel: ChatbotViewModel!
    var mockedData: MockedChatbotViewModelData!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        view = ChatbotView()
        mockedData = MockedChatbotViewModelData()
        viewModel = ChatbotViewModel(data: mockedData.data,
                                     chatbotServiceType: .remote)
        view.viewModel = viewModel
    }
}

// MARK: - Setup
extension ChatbotViewTests {
    
    // MARK: - Actions
    func testDidTapAnswerButtonsAction() {
        var firstData = NextStepParameters(sessionId: "1")
        let secondData = NextStepParameters(sessionId: "2")
        
        let action: NextStepClosure = { data in
            guard let data = data else { return }
            firstData = data
        }
        
        view.didTapAnswerButtonsAction = action
        view.didTapAnswerButtonsAction(secondData)
        
        XCTAssertEqual(firstData.sessionId, secondData.sessionId)
    }
    
    func testDidTapIndexAction() {
        var firstIndex = 1
        let secondIndex = 2
        
        let action: IntClosure = { index in
            firstIndex = index
        }
        
        view.didTapIndexAction = action
        view.didTapIndexAction(secondIndex)
        
        XCTAssertEqual(firstIndex, secondIndex)
    }
    
    func testDidFinishClaim() {
        var firstText = "1"
        let secondText = "2"
        
        let action: StringClosure = { text in
            firstText = text
        }
        
        view.didFinishClaim = action
        view.didFinishClaim(secondText)
        
        XCTAssertEqual(firstText, secondText)
    }
    
    func testDidTapFinishAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        view.didTapFinishAction = action
        view.didTapFinishAction()
        XCTAssertEqual(count, 1)
    }
    
    // MARK: - View State
    func testStartingState() {
        view.changed(state: .started)
        if viewModel.isNewSession && viewModel.data.type == .Central {
            XCTAssertTrue(viewModel.state == .requestSucceeded)
        } else {
            XCTAssertTrue(viewModel.state == .loading)
        }
    }
    
    func testLoadingState() {
        view.changed(state: .loading)
        XCTAssertTrue(view.dualButtonsView.isHidden)
    }
    
    func testFailedState() {
        view.changed(state: .requestFailed)
        XCTAssertTrue(view.dualButtonsView.isHidden)
    }
    
    // MARK: - Setup
    func testSetupObserver() {
        view.setupObserver()
        view.viewModel.state = .loading
        XCTAssertTrue(view.dualButtonsView.isHidden)
    }
    
    func testSetupButtonsReturns() {
        viewModel.data.chatInteractionStep = nil
        view.setupButtons()
        XCTAssertTrue(view.dualButtonsView.isHidden)
    }
    
    func testSetupButtonsInputCases() {
        viewModel.data.chatInteractionStep?.type = .TextInput
        view.setupButtons()
        XCTAssertFalse(view.inputContainerView.isHidden)
    }
    
    func testSetupButtonsUploadCases() {
        let numberOfUploadButtons = 3
        viewModel.data.chatInteractionStep?.type = .UploadPicture
        view.setupButtons()
        XCTAssertEqual(view.stackButtonsView.stackViewCount,
                       numberOfUploadButtons)
    }
    
    func testSetupButtonsFinishCases() {
        viewModel.data.chatInteractionStep?.type = .Finish
        view.setupButtons()
        XCTAssertTrue(view.dualButtonsView.isHidden)
    }
    
    func testAddButtonsReturns() {
        viewModel.data.chatInteractionStep = nil
        view.addButtons(.Horizontal)
        XCTAssertTrue(view.dualButtonsView.stackView.arrangedSubviews.isEmpty)
    }
    
    func testAddButtons() {
        let actionsCount = viewModel.data.chatInteractionStep?.actions.count
        view.addButtons(.Horizontal)
        XCTAssertEqual(view.dualButtonsView.stackView.arrangedSubviews.count,
                       actionsCount)
    }
    
    func testAddButtonsByActionsReturns() {
        viewModel.data.chatInteractionStep = nil
        view.addButtonsByActions(index: 0, orientation: .Horizontal)
        XCTAssertTrue(view.dualButtonsView.stackView.arrangedSubviews.isEmpty)
    }
    
    func testAddButtonsByActionsSignatureType() {
        let text = "Sign"
        viewModel.data.chatInteractionStep?.actions[0].action = .Signature
        viewModel.data.chatInteractionStep?.actions[0].label = text
        
        view.addButtonsByActions(index: 0, orientation: .Horizontal)
        
        let button = view.dualButtonsView.stackView.arrangedSubviews[0] as? ChatButtonItem
        XCTAssertEqual(button?.data?.title, text)
    }
    
    func testAddButtonsByActionsLocalType() {
        let text = "Local"
        viewModel.data.chatInteractionStep?.actions[0].action = .LocalAction
        viewModel.data.chatInteractionStep?.actions[0].label = text
        
        view.addButtonsByActions(index: 0, orientation: .Vertical)
        
        let button = view.stackButtonsView.stackView.arrangedSubviews[0] as? StackButtonItem
        XCTAssertEqual(button?.data?.title, text)
    }
    
    func testAddButtonsByActionsFinishType() {
        let text = "Finish"
        viewModel.data.chatInteractionStep?.actions[0].action = .Finish
        viewModel.data.chatInteractionStep?.actions[0].label = text
        
        view.addButtonsByActions(index: 0, orientation: .Horizontal)
        
        let button = view.dualButtonsView.stackView.arrangedSubviews[0] as? ChatButtonItem
        XCTAssertEqual(button?.data?.title, text)
    }
    
    func testAddButtonsByActionsFinishAndRedirectType() {
        let text = "FinishAndRedirect"
        viewModel.data.chatInteractionStep?.actions[0].action = .FinishAndRedirect
        viewModel.data.chatInteractionStep?.actions[0].label = text
        
        view.addButtonsByActions(index: 0, orientation: .Horizontal)
        
        let button = view.dualButtonsView.stackView.arrangedSubviews[0] as? ChatButtonItem
        XCTAssertEqual(button?.data?.title, text)
    }
    
    func testAddButtonsByActionsStopClaimType() {
        let text = "StopClaim"
        viewModel.data.chatInteractionStep?.actions[0].action = .StopClaim
        viewModel.data.chatInteractionStep?.actions[0].label = text
        
        view.addButtonsByActions(index: 0, orientation: .Horizontal)
        
        let button = view.dualButtonsView.stackView.arrangedSubviews[0] as? ChatButtonItem
        XCTAssertEqual(button?.data?.title, text)
    }
    
    func testAddButtonsByActionsUploadType() {
        let text = "Upload"
        viewModel.data.chatInteractionStep?.actions[0].action = .UploadFile
        viewModel.data.chatInteractionStep?.actions[0].label = text
        
        view.addButtonsByActions(index: 0, orientation: .Horizontal)
        
        let button = view.dualButtonsView.stackView.arrangedSubviews[0] as? ChatButtonItem
        XCTAssertEqual(button?.data?.title, text)
    }
    
    func testAddButtonsByActionsReimbursementType() {
        let text = "Reimbursement"
        viewModel.data.chatInteractionStep?.actions[0].action = .Reimbursement
        viewModel.data.chatInteractionStep?.actions[0].label = text
        
        view.addButtonsByActions(index: 0, orientation: .Horizontal)
        
        let button = view.dualButtonsView.stackView.arrangedSubviews[0] as? ChatButtonItem
        XCTAssertEqual(button?.data?.title, text)
    }
    
    func testAddButtonsByActionsDefault() {
        let text = "Default"
        viewModel.data.chatInteractionStep?.actions[0].action = .Yes
        viewModel.data.chatInteractionStep?.actions[0].label = text
        
        view.addButtonsByActions(index: 0, orientation: .Vertical)
        
        let button = view.stackButtonsView.stackView.arrangedSubviews[0] as? StackButtonItem
        XCTAssertEqual(button?.data?.title, text)
    }
    
    func testGetNextStepData() {
        let step = view.getNextStepData(index: 0)
        XCTAssertEqual(step.sessionId,
                       viewModel.data.chatInteractionStep?.sessionId)
    }
    
    func testSetupTextField() {
        let type: TextFieldType = .cellphone
        view.setupTextField(type: type)
        XCTAssertEqual(type, view.inputTextFieldView.type)
    }
    
    func testSetupDefaultButtons() {
        viewModel.data.chatInteractionStep?.orientation = nil
        view.setupDefaultButtons()
        XCTAssertFalse(view.dualButtonsView.stackView.arrangedSubviews.isEmpty)
    }
    
    // MARK: - Private Methods
    func testAddDualButton() {
        let buttonData = ChatbotButtonData(title: "dual", isMainAction: true)
        view.addDualButton(buttonData)
        XCTAssertFalse(view.dualButtonsView.stackView.arrangedSubviews.isEmpty)
    }
    
    func testAddStackButton() {
        let buttonData = ChatbotButtonData(title: "stack", isMainAction: false)
        view.addStackButton(buttonData)
        XCTAssertFalse(view.stackButtonsView.stackView.arrangedSubviews.isEmpty)
    }
    
    func testShowInputTextField() {
        var isJustInputShown: Bool {
            return !view.inputContainerView.isHidden &&
            view.dualButtonsView.isHidden &&
            view.stackButtonsView.isHidden
        }
        view.showInputTextFieldView(type: .TextInput)
        XCTAssertTrue(isJustInputShown)
    }
    
    func testSetChatButtonsHidden() {
        var stackButtonIsEmptyAndClean: Bool {
            return view.stackButtonsView.isHidden &&
            view.stackButtonsView.stackView.arrangedSubviews.isEmpty
        }
        view.setChatButtonsHidden(true, type: .stack)
        XCTAssertTrue(stackButtonIsEmptyAndClean)
    }
    
    func testHideAllInputs() {
        var isAllInputsHidden: Bool {
            return view.inputContainerView.isHidden &&
            view.dualButtonsView.isHidden &&
            view.stackButtonsView.isHidden
        }
        view.hideAllInputs()
        XCTAssertTrue(isAllInputsHidden)
    }
    
    func testSetInputTextFieldHiddenCalendarType() {
        viewModel.data.chatInteractionStep?.type = .DateInput
        view.setInputTextFieldViewHidden(false, chatType: viewModel.data.chatType)
        let dateInputTextFieldType: TextFieldType = .calendarChatbot
        XCTAssertEqual(dateInputTextFieldType, view.inputTextFieldView.type)
    }
    
    func testSetInputTextFieldHiddenDefault() {
        let type: TextFieldType = .chatbot
        viewModel.data.chatInteractionStep?.type = .BankAccountInput
        view.inputContainerView.isHidden = true
        view.setInputTextFieldViewHidden(false)
        XCTAssertEqual(type, view.inputTextFieldView.type)
        XCTAssertFalse(view.inputTextFieldView.isHidden)
    }
    
    func testReloadAfterRequest() {
        viewModel.data.chatInteractionStep?.type = .TextInput
        view.reloadAfterRequest()
        XCTAssertFalse(view.inputContainerView.isHidden)
    }
    
    // MARK: - Public Methods
    func testUpdateStopAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        view.update(stopClaimAction: action)
        view.didTapStopClaimAction()
        XCTAssertEqual(count, 1)
    }
    
    // MARK: - Setup File Handlers
    func testSetupUploadStackButtons() {
        view.setupUploadStackButtons()
        let firstButton = view.stackButtonsView.stackView.arrangedSubviews.first as? StackButtonItem
        XCTAssertEqual(firstButton?.data?.title, "addInfo.takePicture.button".localized)
    }
    
    func testCloseUploadStackButtons() {
        view.closeUploadStackButtons()
        XCTAssertTrue(view.stackButtonsView.isHidden)
    }
}
