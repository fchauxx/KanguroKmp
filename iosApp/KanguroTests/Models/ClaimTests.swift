@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedDomain
import KanguroPetDomain

class ClaimTests: XCTestCase {
    
    // MARK: - Stored Properties
    var claim: PetClaim!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        claim = PetClaim(id: "1", status: .Draft)
    }
}

// MARK: - Setup
extension ClaimTests {
    
    func testClaimID() {
        let newClaim = PetClaim(id: "1", status: .Draft)
        XCTAssertEqual(newClaim.id, claim.id)
    }
}
