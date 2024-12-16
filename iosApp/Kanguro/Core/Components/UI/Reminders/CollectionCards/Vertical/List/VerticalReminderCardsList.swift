import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class VerticalReminderCardList: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet var tableView: UITableView!
    @IBOutlet var warningLabel: CustomLabel!
    
    // MARK: - Stored Properties
    var displayedReminders: [KanguroSharedDomain.Reminder] = []
    var requestError: String = ""
    var reminders: [KanguroSharedDomain.Reminder]?
    var pet: Pet?
    
    // MARK: - Computed Properties
    var filteredReminders: [KanguroSharedDomain.Reminder] {
        return reminders.flatMap { $0.filter { $0.petId == pet?.id } } ?? []
    }
    
    // MARK: - Actions
    var didTapMedicalHistoryCardAction: PetClosure = { _ in }
    var didTapCommunicationCardAction: StringClosure = { _ in }
    
    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
    }
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.loadNibContent()
    }
}

// MARK: - Setup
extension VerticalReminderCardList {
    
    private func setupLayout() {
        registerCells()
        setupTableView()
        setupLabels()
    }
    
    func setupLabels() {
        warningLabel.set(text: "reminders.noReminder.label".localized,
                         style: TextStyle(color: .secondaryMedium, size: .p16))
    }
    
    private func setupTableView() {
        guard let reminders = reminders else { return }
        tableView.isHidden = reminders.isEmpty
        tableView.contentInset = UIEdgeInsets(top: 0, left: 0, bottom: 32, right: 0)
    }
    
    private func registerCells() {
        tableView.register(identifiers: [VerticalReminderCardCell.identifier])
    }
}

// MARK: - Public Methods
extension VerticalReminderCardList {
    
    func reloadReminders(pet: Pet? = nil) {
        self.pet = pet
        displayedReminders = filteredReminders
        tableView.reloadData()
    }
    
    func setup(reminders: [KanguroSharedDomain.Reminder]) {
        self.reminders = reminders
        setupLayout()
        tableView.reloadData()
    }
    
    func update(medicalHistoryAction: @escaping PetClosure) {
        self.didTapMedicalHistoryCardAction = medicalHistoryAction
    }
    
    func update(communicationAction: @escaping StringClosure) {
        self.didTapCommunicationCardAction = communicationAction
    }
}

// MARK: - UITableViewDelegate & UITableViewDataSource
extension VerticalReminderCardList: UITableViewDelegate, UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return !displayedReminders.isEmpty ? displayedReminders.count : (reminders?.count ?? 0)
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: VerticalReminderCardCell.identifier, for: indexPath) as? VerticalReminderCardCell,
              let reminders = reminders else { return UITableViewCell() }
        
        let currentReminder = displayedReminders.isEmpty ? reminders[indexPath.row] : displayedReminders[indexPath.row]
        
        cell.viewModel = ReminderCardCellViewModel(reminder: currentReminder)
        cell.setup(medicalHistoryAction: didTapMedicalHistoryCardAction,
                   communicationAction: didTapCommunicationCardAction)
        cell.animate()
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 72
    }
}
