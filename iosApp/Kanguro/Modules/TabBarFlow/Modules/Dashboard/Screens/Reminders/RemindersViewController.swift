import UIKit

class RemindersViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: RemindersViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet private var petOptionsTitle: CustomLabel!
    @IBOutlet var reminderButtonListView: ReminderButtonListView!
    @IBOutlet var verticalReminderCardList: VerticalReminderCardList!
    
    // MARK: Actions
    var didTapCloseButtonAction: SimpleClosure = {}
    var didTapMedicalHistoryCardAction: PetClosure = { _ in }
    var didTapCommunicationCardAction: StringClosure = { _ in }
}

// MARK: - Life Cycle
extension RemindersViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
}

// MARK: - View State
extension RemindersViewController {
    
    private func changed(state: RemindersViewState) {
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            viewModel.getReminders()
        case .loading:
            showLoadingView(shouldAnimate: false)
        case .getPetsSucceeded:
            setupReminderButtonsCollectionView()
            hideLoadingView()
        case .getRemindersSucceeded:
            setupVerticalRemindersList()
            hideLoadingView()
        default:
            break
        }
    }
}

// MARK: - Setup
extension RemindersViewController {
    
    private func setupLayout() {
        setupLabels()
    }
    
    func setupLabels() {
        titleLabel.set(text: "reminders.reminder.label".localized,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
        petOptionsTitle.set(text: "reminders.wantToSee.label".localized,
                            style: TextStyle(color: .neutralDark, size: .p16))
    }
    
    func setupReminderButtonsCollectionView() {
        guard let pets = viewModel.pets else { return }
        let remindersPetIds = viewModel.reminders.compactMap { $0.petId }
        let petList = pets.filter { remindersPetIds.contains($0.id) }
        reminderButtonListView.setup(pets: petList)
        reminderButtonListView.didTapButtonAction = { [weak self] pet in
            guard let self = self else { return }
            self.verticalReminderCardList.reloadReminders(pet: pet)
        }
    }
    
    func setupVerticalRemindersList() {
        verticalReminderCardList.setup(reminders: viewModel.reminders)
        verticalReminderCardList.update(medicalHistoryAction: didTapMedicalHistoryCardAction)
        verticalReminderCardList.update(communicationAction: didTapCommunicationCardAction)
    }
}

// MARK: - IB Actions
extension RemindersViewController {
    
    @IBAction func closeTouchUpInside(_ sender: UIButton) {
        didTapCloseButtonAction()
    }
}
