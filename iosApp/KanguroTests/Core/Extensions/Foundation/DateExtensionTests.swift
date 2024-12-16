//
//  DateExtensionTests.swift
//  KanguroTests
//
//  Created by Rodrigo Okido on 01/06/23.
//

import XCTest
@testable import Kanguro

final class DateExtensionTests: XCTestCase {
    
    override func setUp() {
        continueAfterFailure = false
    }
    
    func test_getFormatted_WhenDateIsOnAnyFormatAndCurrentUserLocationTimezoneIsSet_ShouldReturnInUTCFormatAndConvertDateBasedOnUserLocation() {
        
        let date_utc_example_1 = "2023-01-30T23:51:12.0930184+00:00"
        let date1 = date_utc_example_1.date // Convert this String to Date
        
        let date_utc_example_2 = "2023-01-30T00:00:00"
        let date2 = date_utc_example_2.date
        
        let date_utc_example_3 = "2023-01-30"
        let date3 = date_utc_example_3.date
        
        let userLocalTime1 = date1?.getFormatted(format: "yyyy-MM-dd'T'HH:mm:ss", timezone: .init(identifier: "America/Sao_Paulo")!)
        let userLocalTime2 = date2?.getFormatted(format: "yyyy-MM-dd'T'HH:mm:ss", timezone: .init(identifier: "America/Sao_Paulo")!)
        let userLocalTime3 = date3?.getFormatted(format: "yyyy-MM-dd'T'HH:mm:ss", timezone: .init(identifier: "America/Sao_Paulo")!)

        XCTAssertEqual(userLocalTime1, "2023-01-30T20:51:12")
        XCTAssertEqual(userLocalTime2, "2023-01-29T21:00:00")
        XCTAssertEqual(userLocalTime3, "2023-01-29T21:00:00")
    }
    
    func test_getFormatted_WhenDateIsOnAnyFormatAndUTCTimezoneIsSet_ShouldReturnDateBasedOnUTC() {
        
        let date_utc_example_1 = "2023-01-30T23:51:12.0930184+00:00"
        let date1 = date_utc_example_1.date // Convert this String to Date
        
        let date_utc_example_2 = "2023-01-30T00:00:00"
        let date2 = date_utc_example_2.date
        
        let date_utc_example_3 = "2023-01-30"
        let date3 = date_utc_example_3.date
        
        let userLocalTime1 = date1?.getFormatted(format: "yyyy-MM-dd'T'HH:mm:ss", timezone: .init(abbreviation: "UTC")!)
        let userLocalTime2 = date2?.getFormatted(format: "yyyy-MM-dd'T'HH:mm:ss", timezone: .init(abbreviation: "UTC")!)
        let userLocalTime3 = date3?.getFormatted(format: "yyyy-MM-dd'T'HH:mm:ss", timezone: .init(abbreviation: "UTC")!)

        XCTAssertEqual(userLocalTime1, "2023-01-30T23:51:12")
        XCTAssertEqual(userLocalTime2, "2023-01-30T00:00:00")
        XCTAssertEqual(userLocalTime3, "2023-01-30T00:00:00")
    }
}
