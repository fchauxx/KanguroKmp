@testable import Kanguro
import XCTest
import SwiftUI

class ProfileScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var vc: ProfileViewController!
    var viewModel: ProfileViewModel!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        vc = ProfileViewController()
        viewModel = ProfileViewModel()
        vc.viewModel = viewModel
        vc.loadView()
        vc.viewDidLoad()
    }
}

// MARK: - Computed Properties
extension ProfileScreenTests {
    
    func testAccordionViews() {
        XCTAssertTrue(vc.accordionViews.first is AccordionViewProtocol)
    }
    
    func testAccordionTextFieldViews() {
        XCTAssertTrue(vc.accordionTextFieldViews.first is AccordionTextFieldView)
    }
}

// MARK: - View State
extension ProfileScreenTests {
    
    func testChangedLoadingState() {
        vc.changed(state: .loading)
        XCTAssertFalse(vc.editProfileAccordionTextFieldView.saveButton.loader?.isHidden ?? true)
    }
    
    func testChangedDataChangedState() {
        vc.changed(state: .dataChanged)
        XCTAssertEqual(vc.editProfileAccordionTextFieldView.saveButton.isEnabled,
                       vc.editProfileAccordionTextFieldView.isContentValid)
    }
    
    func testChangedRequestSucceededState() {
        vc.changed(state: .requestSucceeded)
        XCTAssertFalse(vc.editProfileAccordionTextFieldView.saveButton.isEnabled)
    }
}

// MARK: - Setup
extension ProfileScreenTests {
    
    func testSetupLabels() {
        XCTAssertEqual(vc.titleLabel.text,
                       "profile.title.label".localized)
    }
    
    func testSetupProfileAccordionTextFieldView() {
        XCTAssertEqual(vc.editProfileAccordionTextFieldView.title,
                       "profile.editProfile.textFieldView".localized)
        
        vc.editProfileAccordionTextFieldView.didTapSaveAction()
        XCTAssertEqual(viewModel.state, .loading)
    }
    
    func testSetupPasswordAccordionTextFieldView() {
        XCTAssertEqual(vc.editPasswordAccordionTextFieldView.title,
                       "profile.editPassword.textFieldView".localized)
        
        vc.editPasswordAccordionTextFieldView.didTapSaveAction()
        XCTAssertEqual(viewModel.state, .loading)
    }
    
    func testSetupDeleteAccordionView() {
        XCTAssertEqual(vc.deleteAccountAccordionCardView.title,
                       "profile.account.label".localized)
    }
    
    func testSetupDeletePolicyAccordionView() {
        XCTAssertEqual(vc.deletePolicyAccordionCardView.title,
                       "profile.other.label".localized)
    }
    
    func testCloseAccordionViews() {
        vc.editProfileAccordionTextFieldView.isExpanded = true
        vc.editPasswordAccordionTextFieldView.isExpanded = true
        vc.editProfileAccordionTextFieldView.didTapExpandAction()
        XCTAssertFalse(vc.editPasswordAccordionTextFieldView.isExpanded)
    }
}
