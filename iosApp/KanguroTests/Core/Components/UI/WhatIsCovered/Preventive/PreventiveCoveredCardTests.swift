@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedDomain

class PreventiveCoveredCardTests: XCTestCase {
    
    // MARK: - Stored Properties
    var card: PreventiveCoveredCardView!
    let data = KanguroSharedDomain.CoverageData(name: "test",
                            remainingValue: 10,
                            annualLimit: 20.0,
                            remainingLimit: 15,
                            uses: 3,
                            remainingUses: 2,
                            usesLimit: 3)
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        card = PreventiveCoveredCardView()
        card.setupData(data, isEditable: true)
    }
}

// MARK: - Computed Properties
extension PreventiveCoveredCardTests {
    
    func testIsCheckboxSelectedDefaultValue() {
        card.checkboxView.viewModel = CheckboxViewModel()
        XCTAssertFalse(card.isCheckboxSelected)
    }
}

// MARK: - Setup
extension PreventiveCoveredCardTests {
    
    func testSetupLabels() {
        let annualLimitText = "preventiveCovered.annualLimitUpTo.label".localized + (data.annualLimit?.getCurrencyFormatted(fractionDigits: 2) ?? "")
        XCTAssertEqual(annualLimitText, card.annualLimitLabel.text)
        
        let remainingAvaliableText = (data.remainingAvailableText ?? "") + "preventiveCovered.available.label".localized
        XCTAssertEqual(remainingAvaliableText, card.remainingValueLabel.text)
    }
    
    func testSetupViewsAlpha() {
        XCTAssertNotEqual(0.5, card.checkboxView.alpha)
    }
}
