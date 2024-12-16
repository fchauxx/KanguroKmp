import XCTest
@testable import Kanguro
import KanguroSharedDomain
import KanguroPetDomain

extension LocalChatbotModuleTests {

    func test_starterStepId_WhenSessionTypeIsNewClaim_ShouldReturnSelectPet() {
        let (sut, _) = makeSUT()
        sut.type = .NewClaim
        XCTAssertEqual(sut.starterStepId, .SelectPet)
    }

    func test_isPolicyOnWaitingPeriod_WhenPolicyIsOnWaitingPeriod_ShouldReturnTrue() {
        let (sut, _) = makeSUT()
        sut.chosenPetPolicy = Policy(waitingPeriodRemainingDays: 3)
        XCTAssertTrue(sut.isPolicyOnWaitingPeriod)
    }

    func test_isPolicyOnWaitingPeriod_WhenPolicyIsNotOnWaitingPeriodOrHasNilValueOnIt_ShouldReturnFalse() {
        let (sut, _) = makeSUT()
        sut.chosenPetPolicy = Policy(waitingPeriodRemainingDays: 0)
        XCTAssertFalse(sut.isPolicyOnWaitingPeriod)
        sut.chosenPetPolicy = Policy()
        XCTAssertFalse(sut.isPolicyOnWaitingPeriod)
    }

    func test_isPolicyNotActive_WhenPolicyIsNotWithActiveStatus_ShouldReturnTrue() {
        let (sut, _) = makeSUT()
        sut.chosenPetPolicy = Policy(status: .CANCELED)
        XCTAssertTrue(sut.isPolicyNotActive)
        sut.chosenPetPolicy = Policy(status: .TERMINATED)
        XCTAssertTrue(sut.isPolicyNotActive)
        sut.chosenPetPolicy = Policy(status: .PENDING)
        XCTAssertTrue(sut.isPolicyNotActive)
    }

    func test_isPolicyNotActive_WhenPolicyIsWithActiveStatus_ShouldReturnFalse() {
        let (sut, _) = makeSUT()
        sut.chosenPetPolicy = Policy(status: .ACTIVE)
        XCTAssertFalse(sut.isPolicyNotActive)
    }

    func test_isPolicyWithMedicalReminder_WhenPolicyHasMedicalReminder_ShouldReturnTrue() {
        let (sut, _) = makeSUT()
        sut.hasMedicalReminders = true
        XCTAssertTrue(sut.isPolicyWithMedicalReminder)
    }

    func test_isPolicyWithMedicalReminder_WhenPolicyHasNoMedicalReminderOrNilInfo_ShouldReturnFalse() {
        let (sut, _) = makeSUT()
        sut.hasMedicalReminders = false
        XCTAssertFalse(sut.isPolicyWithMedicalReminder)
        sut.hasMedicalReminders = nil
        XCTAssertFalse(sut.isPolicyWithMedicalReminder)
    }

    func test_isPolicyStillWithCoveragesAvailable_WhenPolicyIsWithCoverages_shouldReturnTrue() {
        let (sut, _) = makeSUT()
        sut.hasCoveragesAvailable = true
        XCTAssertTrue(sut.isPolicyStillWithCoveragesAvailable)
    }

    func test_isPolicyStillWithCoveragesAvailable_WhenPolicyHasNoCoveragesAvailabelOrNilInfo_ShouldReturnFalse() {
        let (sut, _) = makeSUT()
        sut.hasCoveragesAvailable = false
        XCTAssertFalse(sut.isPolicyWithMedicalReminder)
        sut.hasCoveragesAvailable = nil
        XCTAssertFalse(sut.isPolicyWithMedicalReminder)
    }

    func test_isPolicyWithNoAnnualLimit_WhenPolicyHasNoAnnualLimitOrIsNil_ShouldReturnTrue() {
        let (sut, _) = makeSUT()
        sut.chosenPetPolicy = Policy(sumInsured: SumInsured(id: 0, limit: 200, consumed: 200, remainingValue: 0))
        XCTAssertTrue(sut.isPolicyWithNoAnnualLimit)
        sut.chosenPetPolicy = Policy()
        XCTAssertTrue(sut.isPolicyWithNoAnnualLimit)
    }

    func test_isPolicyWithNoAnnualLimit_WhenPolicyHasAnnualLimit_ShouldReturnFalse() {
        let (sut, _) = makeSUT()
        sut.chosenPetPolicy = Policy(sumInsured: SumInsured(id: 0, limit: 200, consumed: 200, remainingValue: 100))
        XCTAssertFalse(sut.isPolicyWithNoAnnualLimit)
    }
}
