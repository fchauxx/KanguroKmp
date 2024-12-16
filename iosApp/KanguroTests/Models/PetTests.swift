@testable import Kanguro
import XCTest
import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class PetTests: XCTestCase {
    
    // MARK: - Stored Properties
    var pet: Pet!
    let petName = "Oliver"
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        pet = Pet(id: 0, name: "Oliver")
    }
}

// MARK: - Initializers
extension PetTests {
    
    func testPet() {
        let pet = Pet(id: 0, name: "Oliver")
        XCTAssertEqual(pet.name, pet.name)
    }
}
