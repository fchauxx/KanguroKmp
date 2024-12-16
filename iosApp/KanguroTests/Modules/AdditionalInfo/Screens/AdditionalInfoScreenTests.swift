@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedDomain
import KanguroPetDomain

class AdditionalInfoScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var vc: AdditionalInfoViewController!
    var viewModel: AdditionalInfoViewModel!
    var pets: [Pet] = [Pet(id: 0, hasAdditionalInfo: false),
                       Pet(id: 1, hasAdditionalInfo: false)]
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        vc = AdditionalInfoViewController()
        viewModel = AdditionalInfoViewModel(pets: pets)
        vc.viewModel = viewModel
        vc.loadViewIfNeeded()
    }
}

// MARK: - Setup
extension AdditionalInfoScreenTests {
    
    func testDidTapMapAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        vc.didTapMapCellAction = action
        vc.didTapMapCellAction()
        XCTAssertEqual(count, 1)
    }
    
    func testGoNextAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        vc.goNextAction = action
        vc.goNextAction()
        XCTAssertEqual(count, 1)
    }
    
    func testSetupChatbotView() {
        guard let vcPetId = vc.viewModel.currentPet?.id else { return }
        let chatbotPetId = vc.chatbotView.viewModel.data.currentPetId
        XCTAssertEqual(vcPetId, chatbotPetId)
    }
    
    func testViewModelInitializer() {
        guard let vcPetId = vc.viewModel.currentPet?.id else { return }
        XCTAssertEqual(vcPetId, pets.first?.id)
    }
    
    func testViewModelRemoveCompletedPet() {
        XCTAssertTrue(vc.viewModel.pets.count == 2)
        vc.viewModel.removeCompletedPet()
        XCTAssertTrue(vc.viewModel.pets.count == 1)
        vc.viewModel.removeCompletedPet()
        XCTAssertTrue(vc.viewModel.pets.isEmpty)
    }
}
