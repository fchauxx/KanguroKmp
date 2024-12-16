import XCTest
import SwiftUI
import KanguroRentersDomain
import KanguroRentersPresentation

final class ScheduleNewItemAddPictureViewModelTests: XCTestCase {

    var sut: ScheduleItemAddPictureViewModel!

    override func setUp() {
        sut = ScheduleItemAddPictureViewModel(updateScheduledItemsImagesService: nil,
                                              createTemporaryPictureService: nil,
                                              receiptOrAppraisalImages: [],
                                              itemImages: [],
                                              itemWithReceiptOrAppraisalImages: [])
    }
}

// MARK: - Computed Properties Tests
extension ScheduleNewItemAddPictureViewModelTests {

    func test_hasAtLeastOneImageForType_WhenOneOfTheListContainsImage_ShouldReturnFalse() {
        sut.receiptOrAppraisalImages = [
            ScheduledItemImage(id: 99, fileName: "", type: .ReceiptOrAppraisal)
         ]

        let expectation = sut.hasAtLeastOneImageForType
        XCTAssertEqual(expectation, false)
    }

    func test_hasAtLeastOneImageForType_WhenAllImageListsAreEmpty_ShouldReturnFalse() {
        let expectation = sut.hasAtLeastOneImageForType
        XCTAssertEqual(expectation, false)
    }

    func test_hasAtLeastOneImageForType_WhenAllImageListsAreNotEmpty_ShouldReturnTrue() {
        sut.receiptOrAppraisalImages = [
            ScheduledItemImage(id: 99, fileName: "", type: .ReceiptOrAppraisal)
         ]
        sut.itemImages = [
            ScheduledItemImage(id: 99, fileName: "", type: .Item)
         ]
        sut.itemWithReceiptOrAppraisalImages = [
            ScheduledItemImage(id: 99, fileName: "", type: .ItemWithReceiptOrAppraisal)
         ]
        let expectation = sut.hasAtLeastOneImageForType
        XCTAssertEqual(expectation, true)
    }
}
