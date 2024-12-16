import XCTest

final class Date_ExtensionsTests: XCTestCase {

    func test_UTCFormat_WhenReceiveADate_ShouldReturnAStringConvertedUTCFormat() {

        var sut = Date(timeIntervalSince1970: 1598627222) // "2020-08-28T15:07:02+00:00"
        XCTAssertEqual(sut.UTCFormat, "2020-08-28T15:07:02") // "yyyy-MM-dd'T'HH:mm:ss"
        let dateFormatter = DateFormatter()

        let stringDate = "01/02/2016"
        dateFormatter.dateFormat = "MM/dd/yyyy"
        dateFormatter.timeZone = TimeZone(abbreviation: "UTC")
        sut = dateFormatter.date(from: stringDate)!
        XCTAssertEqual(sut.UTCFormat, "2016-01-02T00:00:00")

        let stringDate2 = "03/08/2016 00:00"
        dateFormatter.dateFormat = "MM-dd-yyyy HH:mm"
        dateFormatter.timeZone = TimeZone(abbreviation: "UTC")
        sut = dateFormatter.date(from: stringDate2)!
        XCTAssertEqual(sut.UTCFormat, "2016-03-08T00:00:00")
    }
}
