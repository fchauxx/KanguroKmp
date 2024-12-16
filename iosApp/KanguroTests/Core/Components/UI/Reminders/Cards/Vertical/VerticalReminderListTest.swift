@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedDomain
import KanguroPetDomain

class VerticalReminderListTests: XCTestCase {
    
    // MARK: - Stored Properties
    var reminderList: VerticalReminderCardList!
    var reminders = [KanguroSharedDomain.Reminder(petId: 0, type: .MedicalHistory),
                    KanguroSharedDomain.Reminder(petId: 1, type: .Claim)]
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        reminderList = VerticalReminderCardList()
        reminderList.setup(reminders: reminders)
    }
}

// MARK: - Computed Properties
extension VerticalReminderListTests {
    
    func testFilteredRemindersReturnsWhenNil() {
        reminderList.reminders = nil
        XCTAssertTrue(reminderList.filteredReminders.isEmpty)
    }
    
    func testFilteredReminders() {
        reminderList.pet = Pet(id: 1)
        XCTAssertEqual(reminderList.filteredReminders.first?.petId,
                       reminders.last?.petId)
    }
}

// MARK: - Setup
extension VerticalReminderListTests {
    
    func testSetupLabel() {
        reminderList.setupLabels()
        XCTAssertEqual("reminders.noReminder.label".localized,
                       reminderList.warningLabel.text)
    }
    
    func testSetup() {
        XCTAssertEqual(reminders.first?.type, reminderList.reminders?.first?.type)
    }
    
    func testCollectionViewCount() {
        reminderList.tableView.reloadData()
        XCTAssertEqual(reminders.count,
                       reminderList.tableView.numberOfRows(inSection: 0))
    }
    
    func testReloadDataReturnsWhenNil() {
        reminderList.reminders = nil
        reminderList.tableView.reloadData()
        XCTAssertNotEqual(reminders.count,
                          reminderList.tableView.numberOfRows(inSection: 0))
    }
    
    func testReloadRemindersNil() {
        reminderList.reloadReminders()
        XCTAssertNil(reminderList.pet)
    }
    
    func testReloadReminders() {
        let pet = Pet(id: 0)
        reminderList.reloadReminders(pet: pet)
        XCTAssertNotNil(reminderList.pet)
    }
}
