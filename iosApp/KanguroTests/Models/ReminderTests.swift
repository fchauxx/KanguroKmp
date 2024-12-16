@testable import Kanguro
import XCTest
import UIKit
import KanguroSharedDomain

class ReminderTests: XCTestCase {
    
    // MARK: - Stored Properties
    var reminder: Reminder!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        reminder = Reminder(type: .Claim)
    }
}

// MARK: - Initializers
extension ReminderTests {
    
    func testProperties() {
        let testReminder = Reminder(type: .Claim)
        XCTAssertEqual(testReminder.type, reminder.type)
    }
}
