@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedDomain
import KanguroPetDomain

class CoverageListTests: XCTestCase {
    
    // MARK: - Stored Properties
    var coverageList: CoverageCardList!
    let policies: [PetPolicy] = [PetPolicy(pet: Pet(id: 0)),
                                 PetPolicy(pet: Pet(id: 1))]
    
    // MARK: - Computed Properties
    var firstCell: CoverageCardCell? {
        let index = IndexPath(item: 1, section: 0)
        return coverageList.cardsListCollectionView.cellForItem(at: index) as? CoverageCardCell
    }
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        coverageList = CoverageCardList()
    }
}

// MARK: - Computed Properties
extension CoverageListTests {
    
    private func setupAllPoliciesStatus(status: KanguroSharedDomain.PolicyStatus) {
        policies.forEach { $0.status = status }
        coverageList.setup(policies: policies)
    }
    
    func testActivePolicies() {
        setupAllPoliciesStatus(status: .ACTIVE)
        XCTAssertEqual(coverageList.policies.count, coverageList.activePolicies.count)
        XCTAssertFalse(coverageList.activePolicies.contains { $0.status != .ACTIVE })
    }
    
    func testInactivePolicies() {
        setupAllPoliciesStatus(status: .CANCELED)
        XCTAssertEqual(coverageList.policies.count, coverageList.inactivePolicies.count)
        XCTAssertFalse(coverageList.inactivePolicies.contains { $0.status != .CANCELED })
    }
}

// MARK: - Setup
extension CoverageListTests {
    
    func testSetupCardsByStatus() {
        setupAllPoliciesStatus(status: .ACTIVE)
        XCTAssertEqual(coverageList.pets.count, policies.count)
    }
    
    func testDidTapButtonAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        coverageList.didTapAddButtonAction = action
        coverageList.didTapAddButtonAction()
        XCTAssertEqual(count, 1)
    }
    
    func testSetLabel() {
        let text = "text"
        coverageList.titleLabel.set(text: text)
        XCTAssertEqual(text, coverageList.titleLabel.text)
    }
    
    func testSelectionView() {
        setupAllPoliciesStatus(status: .CANCELED)
        coverageList.setupSelectionView()
        coverageList.statusSelectionView.didTapButtonAction(.inactive)
        XCTAssertEqual(coverageList.policies.count, coverageList.inactivePolicies.count)
        XCTAssertFalse(coverageList.inactivePolicies.contains { $0.status != .CANCELED })
    }
}

// MARK: - CollectionView Protocols
extension CoverageListTests {
    
    func setupAndReloadCollectionView() {
        setupAllPoliciesStatus(status: .ACTIVE)
        coverageList.cardsListCollectionView.reloadData()
    }
    
    func testCollectionViewCount() {
        setupAndReloadCollectionView()
        XCTAssertEqual(coverageList.policies.count,
                       coverageList.cardsListCollectionView.numberOfItems(inSection: 1) // Policies Section
        )
    }
}
