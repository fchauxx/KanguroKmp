@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedDomain
import KanguroPetDomain

class ClaimsScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var vc: ClaimsViewController!
    var viewModel: ClaimsViewModel!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        vc = ClaimsViewController()
        viewModel = ClaimsViewModel()
        vc.viewModel = viewModel
        viewModel.claims = [
             PetClaim(id: "claimApproved", status: .Approved),
             PetClaim(id: "claimSubmitted", status: .Submitted),
             PetClaim(id: "claimDraft", status: .Draft),
             PetClaim(id: "claimAssigned", status: .Assigned),
             PetClaim(id: "claimInReview", status: .InReview),
             PetClaim(id: "claimPaid", status: .Paid),
             PetClaim(id: "claimDenied", status: .Denied)
         ]
        vc.loadView()
        vc.viewDidLoad()
    }
}

// MARK: - Computed Properties
extension ClaimsScreenTests {
    
    func testClaimLists() {
        XCTAssertNotNil(vc.claimLists)
        XCTAssertTrue(vc.claimLists.first is ClaimStatusList)
    }
    
    func test_openClaims_WhenReceivesAClaimLists_ShouldReturnListOfClaimsFilteredByOpenStatus() {
        XCTAssertEqual(viewModel.openClaims, [PetClaim(id: "claimSubmitted", status: .Submitted),
                                              PetClaim(id: "claimAssigned", status: .Assigned),
                                              PetClaim(id: "claimInReview", status: .InReview)])
    }
    
    func test_openClaims_WhenReceivesAEmptyClaimListOrListWithNoClaimInOpenStatus_ShouldReturnEmptyList() {
        viewModel.claims = []
        XCTAssertEqual(viewModel.openClaims, [])
        
        viewModel.claims = [
            PetClaim(id: "claimApproved", status: .Approved),
            PetClaim(id: "claimPaid", status: .Paid),
            PetClaim(id: "claimDraft", status: .Draft),
            PetClaim(id: "claimDenied", status: .Denied)
        ]
        XCTAssertEqual(viewModel.openClaims, [])
    }
    
    func test_openClaims_WhenReceivesNil_ShouldReturnNil() {
        viewModel.claims = nil
        XCTAssertNil(viewModel.openClaims)
    }
    
    func test_closedClaims_WhenReceivesAClaimLists_ShouldReturnListOfClaimsFilteredByClosedStatus() {
        XCTAssertEqual(viewModel.closedClaims, [PetClaim(id: "claimApproved", status: .Approved),
                                                PetClaim(id: "claimPaid", status: .Paid),
                                                PetClaim(id: "claimDenied", status: .Denied)])
    }
    
    func test_closedClaims_WhenReceivesAEmptyClaimListOrListWithNoClaimInClosedStatus_ShouldReturnEmptyList() {
        viewModel.claims = []
        XCTAssertEqual(viewModel.closedClaims, [])
        
        viewModel.claims = [
            PetClaim(id: "claimSubmitted", status: .Submitted),
            PetClaim(id: "claimDraft", status: .Draft),
            PetClaim(id: "claimAssigned", status: .Assigned),
            PetClaim(id: "claimInReview", status: .InReview)
            ]
        XCTAssertEqual(viewModel.closedClaims, [])
    }
    
    func test_closedClaims_WhenReceivesNil_ShouldReturnNil() {
        viewModel.claims = nil
        XCTAssertNil(viewModel.closedClaims)
    }
    
    func test_draftClaims_WhenReceivesAClaimLists_ShouldReturnListOfClaimsFilteredByDraftStatus() {
        XCTAssertEqual(viewModel.draftClaims, [PetClaim(id: "claimDraft", status: .Draft)])
    }
    
    func test_draftClaims_WhenReceivesAEmptyClaimListOrListWithNoClaimInDraftStatus_ShouldReturnEmptyList() {
        viewModel.claims = []
        XCTAssertEqual(viewModel.draftClaims, [])
        
        viewModel.claims = [
             PetClaim(id: "claimPaid", status: .Paid),
             PetClaim(id: "claimDenied", status: .Denied),
             PetClaim(id: "claimApproved", status: .Approved),
             PetClaim(id: "claimSubmitted", status: .Submitted)
             ]
        XCTAssertEqual(viewModel.draftClaims, [])
    }
    
    func test_draftClaims_WhenReceivesNil_ShouldReturnNil() {
        viewModel.claims = nil
        XCTAssertNil(viewModel.draftClaims)
    }
}

// MARK: - Life Cycle
extension ClaimsScreenTests {
    
    func testViewDidLoad() {
        XCTAssertEqual(vc.titleLabel.text, "claims.title.label".localized)
    }
    
    func testViewWillAppear() {
        vc.viewWillAppear(false)
        XCTAssertEqual(vc.viewModel.state, .loading)
    }
}

// MARK: - Setup
extension ClaimsScreenTests {
    
    func testSetupLabels() {
        XCTAssertEqual(vc.titleLabel.text, "claims.title.label".localized)
    }
    
    func testSetupNavigationButton() {
        XCTAssertEqual(vc.navigationBackButton.titleLabel.text,
                       "claims.title.navigationBackButton".localized)
    }
    
    func testSetupStatusLists() {
        let openClaim = PetClaim(id: "1", status: .Submitted)
        viewModel.claims = [openClaim]
        vc.setupStatusLists()
        XCTAssertEqual(vc.openClaimStatusList.petClaims?.first?.status, openClaim.status)
    }
}
