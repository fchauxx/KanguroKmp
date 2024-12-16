import XCTest
import SwiftUI
import KanguroRentersDomain
import KanguroRentersPresentation

final class ScheduleNewItemViewModelTests: XCTestCase {

    var sut: ScheduleNewItemViewModel!

    override func setUp() {
        sut = ScheduleNewItemViewModel(updatePricingService: nil,
                                       createScheduledItemService: nil)
    }
}

// MARK: - Computed Properties Tests
extension ScheduleNewItemViewModelTests {

    func test_newEndorsementPolicyPrice_WhenPriceContainsManyDecimals_ShouldReturnOnlyTheFirstTwoNumbersAfterDecimals() {
        let pricing = TestPricingFactory.makePricingWithDecimals()
        sut.scheduledItemPricing = pricing

        let expectation = sut.newEndorsementPolicyPrice
        XCTAssertEqual(expectation, "+$499.41")
    }

    func test_newEndorsementPolicyPrice_WhenPriceIsWithNoDecimals_ShouldReturnTheValueItselfWithTwoZerosAfterDecilmals() {
        let pricing = TestPricingFactory.makePricingWithNoDecimals()
        sut.scheduledItemPricing = pricing

        let expectation = sut.newEndorsementPolicyPrice
        XCTAssertEqual(expectation, "+$499.00")
    }

    func test_newEndorsementPolicyPrice_WhenPriceReturnsNil_ShouldReturnZero() {
        let pricing = TestPricingFactory.makePricingWithNilValues()
        sut.scheduledItemPricing = pricing

        let expectation = sut.newEndorsementPolicyPrice
        XCTAssertEqual(expectation, "+$0")
    }


    func test_newDifferenceValuePrice_WhenPriceContainsManyDecimals_ShouldReturnOnlyTheFirstTwoNumbersAfterDecimals() {
        let pricing = TestPricingFactory.makePricingWithDecimals()
        sut.scheduledItemPricing = pricing

        let expectation = sut.newDifferenceValuePrice
        XCTAssertEqual(expectation, "$1008.43")
    }

    func test_newDifferenceValuePrice_WhenPriceIsWithNoDecimals_ShouldReturnTheValueItselfWithTwoZerosAfterDecimals() {
        let pricing = TestPricingFactory.makePricingWithNoDecimals()
        sut.scheduledItemPricing = pricing

        let expectation = sut.newDifferenceValuePrice
        XCTAssertEqual(expectation, "$1000.00")
    }

    func test_newDifferenceValuePrice_WhenPriceReturnsNil_ShouldReturnZero() {
        let pricing = TestPricingFactory.makePricingWithNilValues()
        sut.scheduledItemPricing = pricing

        let expectation = sut.newDifferenceValuePrice
        XCTAssertEqual(expectation, "$0")
    }
}
