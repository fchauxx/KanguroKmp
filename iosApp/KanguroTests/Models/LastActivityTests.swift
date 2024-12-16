@testable import Kanguro
import XCTest
import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class PetLastActivityTests: XCTestCase {
    
    // MARK: - Stored Properties
    var petLastActivity: PetLastActivity!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        petLastActivity = PetLastActivity(placeName: "petshop")
    }
}

// MARK: - Initializers
extension PetLastActivityTests {
    func testPetlastActivity() {
        let newLastActivity = PetLastActivity(placeName: "petshop")
        XCTAssertEqual(newLastActivity.placeName, petLastActivity.placeName)
    }
}
