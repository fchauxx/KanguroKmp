@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedDomain
import KanguroSharedData
import KanguroPetDomain

class ClaimDetailsScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var vc: ClaimDetailsViewController!
    var viewModel: ClaimDetailsViewModel!
    let claim = PetClaim(id: "1", description: "description", prefixId: "#UP10000")
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        vc = ClaimDetailsViewController()
        viewModel = ClaimDetailsViewModel(claim: claim, communicationParameter: PetCommunicationParameters())
        vc.viewModel = viewModel
        vc.loadView()
        vc.viewDidLoad()
    }
}

// MARK: - Life Cycle
extension ClaimDetailsScreenTests {
    
    func testViewDidLoad() {
        XCTAssertEqual(vc.claimIDTitleLabel.text, "claimDetails.IDtitle.label".localized + claim.prefixId!)
    }
}

// MARK: - Setup
extension ClaimDetailsScreenTests {
    
    func testSetupLabels() {
        XCTAssertEqual(vc.claimDescriptionTitleLabel.text,
                       "claimDetails.claimDescription.label".localized)
    }
    
    func testSetupSummaryList() {
        XCTAssertEqual(vc.summaryList.titleLabel.text,
                       "claimDetails.summary.label".localized)
    }
    
    func testSetupWarningView() {
        XCTAssertTrue(vc.warningView.isHidden)
    }
    
    func testSetupAttachments() {
        viewModel.attachments = [KanguroSharedDomain.Attachment(id: 1, fileName: "", fileSize: 1)]
        vc.setupAttachments()
        XCTAssertEqual(vc.attachmentsListView.attachmentsQty,
                       viewModel.attachments?.count)
    }
}

// MARK: - Computed Properties
extension ClaimDetailsScreenTests {
    
    func test_petName_WhenWeGetPetOfAClaimAndPetNameIsNotNil_ShouldReturnPetName() {
        let claim: PetClaim = PetClaim(id: "test", pet: Pet(id: 1, name: "Tester"), status: .InReview, isPendingCommunication: true)
        viewModel.claim = claim
        
        let petName = viewModel.petName
        XCTAssertNotNil(petName)
        XCTAssertEqual(petName, "Tester")
    }
    
    func test_petName_WhenWeGetPetOfAClaimAndPetNameIsNil_ShouldReturnEmptyString() {
        let claim: PetClaim = PetClaim(id: "test", pet: Pet(id: 1),  status: .InReview, isPendingCommunication: true)
        viewModel.claim = claim
        
        XCTAssertEqual(viewModel.petName, "")
    }
    
    func test_isClaimInReviewAndHasPendingCommunication_WhenClaimHasPendingCommunicationAndIsOnReviewStatus_ShouldReturnTrue() {
        let claim: PetClaim = PetClaim(id: "test", status: .InReview, isPendingCommunication: true)
        viewModel.claim = claim
        XCTAssertTrue(viewModel.isClaimInReviewAndHasPendingCommunication)
    }
    
    func test_isClaimInReviewAndHasPendingCommunication_WhenClaimHasPendingCommunicationAndIsOnSubmittedStatus_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test",status: .Submitted, isPendingCommunication: true)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.isClaimInReviewAndHasPendingCommunication)
    }
    
    func test_isClaimInReviewAndHasPendingCommunication_WhenClaimHasPendingCommunicationAndIsOnAssignedStatus_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test",status: .Assigned, isPendingCommunication: true)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.isClaimInReviewAndHasPendingCommunication)
    }
    
    func test_isClaimInReviewAndHasPendingCommunication_WhenClaimHasPendingCommunicationAndIsOnClosedStatus_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test",status: .Closed, isPendingCommunication: true)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.isClaimInReviewAndHasPendingCommunication)
    }
    
    func test_isClaimInReviewAndHasPendingCommunication_WhenClaimHasPendingCommunicationAndIsOnApprovedStatus_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test",status: .Approved, isPendingCommunication: true)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.isClaimInReviewAndHasPendingCommunication)
    }
    
    func test_isClaimInReviewAndHasPendingCommunication_WhenClaimHasPendingCommunicationAndIsOnPaidStatus_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test",status: .Paid, isPendingCommunication: true)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.isClaimInReviewAndHasPendingCommunication)
    }
    
    func test_isClaimInReviewAndHasPendingCommunication_WhenClaimHasPendingCommunicationAndIsOnDeniedStatus_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test",status: .Denied, isPendingCommunication: true)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.isClaimInReviewAndHasPendingCommunication)
    }
    
    func test_isClaimInReviewAndHasPendingCommunication_WhenClaimHasPendingCommunicationAndIsOnDraftStatus_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test",status: .Draft, isPendingCommunication: true)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.isClaimInReviewAndHasPendingCommunication)
    }
    
    func test_isClaimInReviewAndHasPendingCommunication_WhenClaimIsInReviewStatusAndHasNotPendingCommunication_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test", status: .InReview, isPendingCommunication: false)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.isClaimInReviewAndHasPendingCommunication)
    }
    
    func test_isClaimInReviewAndHasPendingCommunication_WhenClaimIsOnAssignedStatusAndHasNotPendingCommunication_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test", status: .Assigned, isPendingCommunication: false)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.isClaimInReviewAndHasPendingCommunication)
    }
    
    func test_isClaimInReviewAndHasPendingCommunication_WhenClaimIsOnSubmittedStatusAndHasNotPendingCommunication_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test", status: .Submitted, isPendingCommunication: false)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.isClaimInReviewAndHasPendingCommunication)
    }
    
    func test_isClaimInReviewAndHasPendingCommunication_WhenClaimIsOnClosedStatusAndHasNotPendingCommunication_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test", status: .Closed, isPendingCommunication: false)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.isClaimInReviewAndHasPendingCommunication)
    }
    
    func test_isClaimInReviewAndHasPendingCommunication_WhenClaimIsOnApprovedStatusAndHasNotPendingCommunication_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test", status: .Approved, isPendingCommunication: false)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.isClaimInReviewAndHasPendingCommunication)
    }
    
    func test_isClaimInReviewAndHasPendingCommunication_WhenClaimIsOnPaidStatusAndHasNotPendingCommunication_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test", status: .Paid, isPendingCommunication: false)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.isClaimInReviewAndHasPendingCommunication)
    }
    
    func test_isClaimInReviewAndHasPendingCommunication_WhenClaimIsOnDeniedStatusAndHasNotPendingCommunication_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test", status: .Denied, isPendingCommunication: false)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.isClaimInReviewAndHasPendingCommunication)
    }
    
    func test_isClaimInReviewAndHasPendingCommunication_WhenClaimIsOnDraftStatusAndHasNotPendingCommunication_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test", status: .Draft, isPendingCommunication: false)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.isClaimInReviewAndHasPendingCommunication)
    }
    
    
    func test_isClaimInReviewOrSubmittedStatus_WhenClaimIsInReviewStatus_ShouldReturnTrue() {
        let claim: PetClaim = PetClaim(id: "test", status: .InReview, isPendingCommunication: false)
        viewModel.claim = claim
        XCTAssertTrue(viewModel.shouldShowSubmitDocumentButton)
    }
    
    func test_isClaimInReviewOrSubmittedStatus_WhenClaimIsOnSubmittedStatus_ShouldReturnTrue() {
        let claim: PetClaim = PetClaim(id: "test", status: .Submitted, isPendingCommunication: false)
        viewModel.claim = claim
        XCTAssertTrue(viewModel.shouldShowSubmitDocumentButton)
    }
    
    func test_isClaimInReviewOrSubmittedStatus_WhenClaimIsOnAssignedStatus_ShouldReturnTrue() {
        let claim: PetClaim = PetClaim(id: "test", status: .Assigned, isPendingCommunication: false)
        viewModel.claim = claim
        XCTAssertTrue(viewModel.shouldShowSubmitDocumentButton)
    }
    
    func test_isClaimInReviewOrSubmittedStatus_WhenClaimIsOnClosedStatus_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test", status: .Closed, isPendingCommunication: false)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.shouldShowSubmitDocumentButton)
    }
    
    func test_isClaimInReviewOrSubmittedStatus_WhenClaimIsOnPaidStatus_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test", status: .Paid, isPendingCommunication: false)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.shouldShowSubmitDocumentButton)
    }
    
    func test_isClaimInReviewOrSubmittedStatus_WhenClaimIsOnApprovedStatus_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test", status: .Approved, isPendingCommunication: false)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.shouldShowSubmitDocumentButton)
    }
    
    func test_isClaimInReviewOrSubmittedStatus_WhenClaimIsOnDeniedStatus_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test", status: .Denied, isPendingCommunication: false)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.shouldShowSubmitDocumentButton)
    }
    
    func test_isClaimInReviewOrSubmittedStatus_WhenClaimIsOnDraftStatus_ShouldReturnFalse() {
        let claim: PetClaim = PetClaim(id: "test", status: .Draft, isPendingCommunication: false)
        viewModel.claim = claim
        XCTAssertFalse(viewModel.shouldShowSubmitDocumentButton)
    }
}

