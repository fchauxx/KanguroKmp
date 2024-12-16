@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedDomain
import KanguroPetDomain

class PaymentSettingsScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var vc: PaymentSettingsViewController!
    var viewModel: PaymentSettingsViewModel!
    let policies = [PetPolicy(pet: Pet(id: 1))]
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        vc = PaymentSettingsViewController()
        viewModel = PaymentSettingsViewModel(policies: policies)
        vc.viewModel = viewModel
        vc.loadView()
        vc.viewDidLoad()
    }
}

// MARK: - Setup
extension PaymentSettingsScreenTests {
    
    func testSetupLabels() {
        XCTAssertEqual(vc.paymentTitleLabel.text,
                       "paymentSettings.payment.label".localized)
    }
    
    func testSetupNavigationBackButton() {
        XCTAssertEqual(vc.navigationBackButton.titleLabel.text,
                       "paymentSettings.title.label".localized)
    }
    
    func testSetupActionCards() {
        XCTAssertEqual(vc.paymentMethodActionCard.data?.leadingTitle,
                       "paymentSettings.paymentMethod.label".localized)
    }
    
    func testSetupCoverageCards() {
        if let firstCard = vc.stackView.arrangedSubviews.first as? CoverageDetailsCard {
            XCTAssertEqual(firstCard.policy?.id, policies.first?.id)
        }
    }
}
