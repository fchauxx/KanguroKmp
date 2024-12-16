@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedDomain
import KanguroPetDomain

class BillingPreferencesScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var vc: BillingPreferencesViewController!
    var viewModel: BillingPreferencesViewModel!
    let policy = PetPolicy(pet: Pet(id: 1))
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        vc = BillingPreferencesViewController()
        viewModel = BillingPreferencesViewModel(policy: policy)
        vc.viewModel = viewModel
        vc.loadView()
        vc.viewDidLoad()
    }
}

// MARK: - Setup
extension BillingPreferencesScreenTests {
    
    func testSetupLabels() {
        XCTAssertEqual(vc.billingPreferencesLabel.text,
                       "billingPreferences.billingPreferences.label".localized)
    }
    
    func testSetupBillingHistoryStackView() {
        if let billingHistoryView = vc.billingHistoryStackView.arrangedSubviews.first as? BillingHistoryView {
            XCTAssertEqual(billingHistoryView.policy?.pet.id,
                           policy.pet.id)
        }
    }
    
    func testSetupCreditCardActionCard() {
        let cardImage = UIImage(named: "ic-mastercard")
        XCTAssertEqual(vc.creditCardActionCard.data?.leadingImage,
                       cardImage)
    }
}
