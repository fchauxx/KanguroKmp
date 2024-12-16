@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedDomain

class CommunicationScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var vc: CommunicationChatbotViewController!
    var viewModel: CommunicationChatbotViewModel!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        vc = CommunicationChatbotViewController()
        viewModel = CommunicationChatbotViewModel(claimId: "id")
        vc.viewModel = viewModel
        vc.loadView()
        vc.viewDidLoad()
    }
}

// MARK: - View State
extension CommunicationScreenTests {
    
    func testChangedStartedState() {
        vc.changed(state: .started)
        XCTAssertEqual(viewModel.state, .loading)
    }
    
    func testChangedDataChangedState() {
        vc.changed(state: .dataChanged)
        XCTAssertEqual(vc.chatbotView.viewModel.data.chatInteractionStep?.messages,
                       viewModel.uploadFilesStep.messages)
    }
}

// MARK: - Setup
extension CommunicationScreenTests {
    
    func testSetupNavigationBackButton() {
        XCTAssertEqual(vc.navigationBackButton.titleLabel.text,
                       "common.back".localized)
    }
    
    func testSetupChatbotView() {
        XCTAssertTrue(vc.chatbotView.viewModel.data.isCommunicationType)
        
        vc.chatbotView.didUpdateCommunicationStep()
        XCTAssertEqual(vc.chatbotView.viewModel.data.chatInteractionStep?.messages,
                       viewModel.addMoreFilesStep.messages)
    }
    
    func testUpdateChatbotData() {
        viewModel.claimId = "2"
        vc.updateChatbotData()
        XCTAssertEqual(vc.chatbotView.viewModel.communicationParameter.id,
                       viewModel.claimId)
    }
}

// MARK: - View Model
extension CommunicationScreenTests {
    
    func testAppendMessages() {
        let communication = Communication(id: 0,
                                          type: .Text,
                                          sender: .Javier,
                                          message: "message",
                                          fileURL: "",
                                          createdAt: nil)
        viewModel.communications = [communication]
        
        viewModel.appendMessages()
        let converted = viewModel.getConvertedMessage(communication: communication)
        XCTAssertEqual(viewModel.uploadFilesStep.messages.first?.sender,
                       converted.sender)
    }
}
