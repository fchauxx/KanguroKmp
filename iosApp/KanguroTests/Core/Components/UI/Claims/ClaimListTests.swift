@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedDomain
import KanguroPetDomain

class ClaimListTests: XCTestCase {
    
    // MARK: - Stored Properties
    var claimList: ClaimStatusList!
    let claims: [PetClaim] = [PetClaim(id: "123")]
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        claimList = ClaimStatusList()
    }
}

// MARK: - Setup
extension ClaimListTests {
    
    func testSetupLabels() {
        let text = "claimStatus.noClaim.label".localized
        claimList.setupLabels()
        XCTAssertEqual(text, claimList.centerLabel.text)
    }
    
    func testSetupClaims() {
        claimList.setup(claims: claims)
        XCTAssertEqual(claims.count, claimList.petClaims?.count)
    }
    
    func testIsClaimsEmpty() {
        claimList.isClaimsEmpty(true)
        XCTAssertTrue(!claimList.centerLabel.isHidden)
    }
    
    func testTableViewCount() {
        claimList.setup(claims: claims)
        XCTAssertEqual(claims.count,
                       claimList.mainTableView.numberOfRows(inSection: 0))
    }
}
