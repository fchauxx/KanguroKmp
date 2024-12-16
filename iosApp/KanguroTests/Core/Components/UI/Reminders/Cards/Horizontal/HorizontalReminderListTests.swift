@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedDomain
import KanguroPetDomain

class HorizontalReminderListTests: XCTestCase {
    
    // MARK: - Stored Properties
    var reminderList: HorizontalReminderCardList!
    var reminders = [KanguroSharedDomain.Reminder(petId: 0, type: .MedicalHistory),
                    KanguroSharedDomain.Reminder(petId: 1, type: .Claim)]
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        reminderList = HorizontalReminderCardList()
        reminderList.setup(reminders: reminders)
    }
}

// MARK: - Computed Properties
extension HorizontalReminderListTests {
    
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
extension HorizontalReminderListTests {
    
    func testSetupLabel() {
        reminderList.setupLabels()
        XCTAssertEqual("dashboard.reminderTitle.label".localized,
                       reminderList.leftTitleLabel.text)
    }
    
    func testSetup() {
        XCTAssertEqual(reminders.first?.type, reminderList.reminders?.first?.type)
    }
    
    func testCollectionViewCount() {
        reminderList.reloadData()
        XCTAssertEqual(reminders.count,
                       reminderList.reminderCardsCollectionView.numberOfItems(inSection: 0))
    }
    
    func testReloadDataReturnsWhenNil() {
        reminderList.reminders = nil
        reminderList.reloadData()
        XCTAssertNotEqual(reminders.count,
                          reminderList.reminderCardsCollectionView.numberOfItems(inSection: 0))
    }
}
