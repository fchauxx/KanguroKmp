@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedDomain
import KanguroPetDomain

class RemindersScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var remindersVC: RemindersViewController!
    var viewModel: RemindersViewModel!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        remindersVC = RemindersViewController()
        viewModel = RemindersViewModel()
        remindersVC.viewModel = viewModel
        let _ = self.remindersVC.view
    }
}

// MARK: - Setup
extension RemindersScreenTests {
    
    func testSetupLabels() {
        remindersVC.setupLabels()
        XCTAssertEqual(remindersVC.titleLabel.text,
                       "reminders.reminder.label".localized)
    }
    
    func testSetupReminderButtonsCollectionView() {
        viewModel.pets = [Pet(id: 0), Pet(id: 1)]
        viewModel.reminders = [Reminder(petId: 0, type: .MedicalHistory),
                               Reminder(petId: 2, type: .MedicalHistory)]
        remindersVC.setupReminderButtonsCollectionView()
        XCTAssertEqual(viewModel.reminders.count,
                       remindersVC.reminderButtonListView.pets.count)
    }
    
    func testSetupVerticalReminderList() {
        viewModel.reminders = [Reminder(petId: 0, type: .MedicalHistory),
                               Reminder(petId: 2, type: .MedicalHistory)]
        remindersVC.setupVerticalRemindersList()
        XCTAssertEqual(viewModel.reminders.count,
                       remindersVC.verticalReminderCardList.reminders?.count)
    }
}
