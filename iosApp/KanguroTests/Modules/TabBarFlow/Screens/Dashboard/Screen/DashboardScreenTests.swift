@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedDomain
import KanguroPetDomain

class DashboardScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var dashboardScreen: DashboardViewController!
    var viewModel: DashboardViewModel!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        dashboardScreen = DashboardViewController()
        viewModel = DashboardViewModel(policies: [PetPolicy(pet: Pet(id: 530))])
        dashboardScreen.viewModel = viewModel
        let _ = self.dashboardScreen.view
    }
    
    override class func tearDown() {
        super.tearDown()
    }
}

// MARK: - Actions
extension DashboardScreenTests {
    
    func testGoBackAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        dashboardScreen.goBackAction = action
        dashboardScreen.goBackAction()
        XCTAssertEqual(1, count)
    }
    
    func testDidTapBannerButtonAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        dashboardScreen.goBackAction = action
        dashboardScreen.goBackAction()
        XCTAssertEqual(1, count)
    }
    
    func testDidTapFileClaimAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        dashboardScreen.goBackAction = action
        dashboardScreen.goBackAction()
        XCTAssertEqual(1, count)
    }
    
    func testDidTapVetAdviceAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        dashboardScreen.goBackAction = action
        dashboardScreen.goBackAction()
        XCTAssertEqual(1, count)
    }
    
    func testDidTapFAQAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        dashboardScreen.goBackAction = action
        dashboardScreen.goBackAction()
        XCTAssertEqual(1, count)
    }
    
    func testDidTapPetParentsAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        dashboardScreen.goBackAction = action
        dashboardScreen.goBackAction()
        XCTAssertEqual(1, count)
    }
}

// MARK: - Setup
extension DashboardScreenTests {
    
    func testSetupLabels() {
        let text = "dashboard.hello.label".localized + ","
        XCTAssertEqual(text, dashboardScreen.helloLabel.text)
    }
}

// MARK: - Computed Properties
extension DashboardScreenTests {
    
    func test_pets_WhenPetListIsReceived_ShouldReturnThePetListOrderedByBiggestID() {
        let pets: [Pet] = [
           Pet(id: 11),
           Pet(id: 2),
           Pet(id: 31)
        ]
        let petPolicies: [PetPolicy] = [
           PetPolicy(pet: pets[0]),
           PetPolicy(pet: pets[1]),
           PetPolicy(pet: pets[2])
        ]
        
        viewModel.policies = petPolicies
        XCTAssertEqual(viewModel.pets, [Pet(id: 31),Pet(id: 11),Pet(id: 2)])
    }
    
    func test_petNames_WhenPetListIsReceived_ShouldReturnThePetListOrderedByIDAndIgnorePetsWithNoName() {
        let pets: [Pet] = [
           Pet(id: 11, name: "Test1"),
           Pet(id: 2),
           Pet(id: 31, name: "Test2")
        ]
        let petPolicies: [PetPolicy] = [
           PetPolicy(pet: pets[0]),
           PetPolicy(pet: pets[1]),
           PetPolicy(pet: pets[2])
        ]
        
        viewModel.policies = petPolicies
        
        // Should order Pets List names by biggest ID ignoring the ones with no name attributed.
        // In this case Pet with id 2.
        XCTAssertEqual(viewModel.petNames, ["Test2", "Test1"])
    }
    
    func test_medicalHistoryReminder_WhenReminderListIsReceivedWithMedicalHistoryPendencies_ShouldReturnTheReminderListFilteredByMedicalHistory() {
        let reminderList = [
           KanguroSharedDomain.Reminder(id: "test_reminder_1", type: .MedicalHistory),
           KanguroSharedDomain.Reminder(id: "test_reminder_2", type: .Claim),
           KanguroSharedDomain.Reminder(id: "test_reminder_3", type: .MedicalHistory),
           KanguroSharedDomain.Reminder(id: "test_reminder_4", type: .Claim)
        ]
        viewModel.reminders = reminderList
        
        let filteredReminders = viewModel.medicalHistoryReminder
        XCTAssertEqual(filteredReminders, [Reminder(id: "test_reminder_1", type: .MedicalHistory),
                                           Reminder(id: "test_reminder_3", type: .MedicalHistory)])
        XCTAssertEqual(filteredReminders.first?.id, "test_reminder_1")
        XCTAssertEqual(filteredReminders[1].id, "test_reminder_3")
    }
    
    func test_medicalHistoryReminder_WhenReminderListIsReceivedWithoutMedicalHistoryPendencies_ShouldReturnTheMedicalHistoryReminderListEmpty() {
        let reminderList = [
           KanguroSharedDomain.Reminder(id: "test_reminder_1", type: .Claim),
           KanguroSharedDomain.Reminder(id: "test_reminder_2", type: .Claim),
           KanguroSharedDomain.Reminder(id: "test_reminder_3", type: .Claim),
           KanguroSharedDomain.Reminder(id: "test_reminder_4", type: .Claim)
        ]
        viewModel.reminders = reminderList
        
        let filteredReminders = viewModel.medicalHistoryReminder
        XCTAssertEqual(filteredReminders, [])
    }
    
    func test_isMedicalRemindersPopUpNeeded_WhenUserHavePendingMedicalHistoryAndAreStillOnDashboardScreen_ShouldReturnTrue() {
        let reminderList = [
           KanguroSharedDomain.Reminder(id: "test_reminder_1", type: .MedicalHistory),
           KanguroSharedDomain.Reminder(id: "test_reminder_2", type: .Claim),
           KanguroSharedDomain.Reminder(id: "test_reminder_3", type: .MedicalHistory),
           KanguroSharedDomain.Reminder(id: "test_reminder_4", type: .Claim)
        ]
        viewModel.reminders = reminderList
        viewModel.remindersFlag = true
        
        let medicalReminderPopUp = viewModel.isMedicalRemindersPopUpNeeded
        XCTAssertTrue(medicalReminderPopUp)
    }
    
    func test_isMedicalRemindersPopUpNeeded_WhenUserHavePendingMedicalHistoryAndAreLeftDashboardScreen_ShouldReturnFalse() {
        let reminderList = [
           KanguroSharedDomain.Reminder(id: "test_reminder_1", type: .MedicalHistory),
           KanguroSharedDomain.Reminder(id: "test_reminder_2", type: .Claim),
           KanguroSharedDomain.Reminder(id: "test_reminder_3", type: .MedicalHistory),
           KanguroSharedDomain.Reminder(id: "test_reminder_4", type: .Claim)
        ]
        viewModel.reminders = reminderList
        viewModel.remindersFlag = true
        dashboardScreen.viewDidDisappear(false)
        
        let medicalReminderPopUp = viewModel.isMedicalRemindersPopUpNeeded
        XCTAssertFalse(medicalReminderPopUp)
    }
}
