import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class HorizontalReminderCardList: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet var leftTitleLabel: CustomLabel!
    @IBOutlet private var rightTitleLabel: CustomLabel!
    @IBOutlet var reminderCardsCollectionView: UICollectionView!
    
    // MARK: - Stored Properties
    var requestError: String = ""
    var reminders: [KanguroSharedDomain.Reminder]?
    var pet: Pet?
    
    // MARK: - Computed Properties
    var filteredReminders: [KanguroSharedDomain.Reminder] {
        return reminders.flatMap { $0.filter { $0.petId == pet?.id } } ?? []
    }
    
    // MARK: - Actions
    private var didTapSeeAllAction: SimpleClosure = {}
    private var didTapMedicalHistoryCardAction: PetClosure = { _ in }
    private var didTapCommunicationCardAction: StringClosure = { _ in }
    
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
extension HorizontalReminderCardList {
    
    private func setupLayout() {
        setupLabels()
        registerCells()
        reloadData()
    }
    
    func setupLabels() {
        leftTitleLabel.set(text: "dashboard.reminderTitle.label".localized,
                           style: TextStyle(color: .secondaryDark, weight: .bold, size: .p12))
        rightTitleLabel.set(text: "dashboard.seeAll.label".localized,
                            style: TextStyle(color: .secondaryMedium, weight: .bold, size: .p14))
    }
    
    private func registerCells() {
        reminderCardsCollectionView.register(identifiers: [HorizontalReminderCardCell.identifier])
    }
}

// MARK: - Public Methods
extension HorizontalReminderCardList {
    
    func update(seeAllAction: @escaping SimpleClosure) {
        self.didTapSeeAllAction = seeAllAction
    }
    
    func update(medicalHistoryAction: @escaping PetClosure) {
        self.didTapMedicalHistoryCardAction = medicalHistoryAction
    }
    
    func update(communicationAction: @escaping StringClosure) {
        self.didTapCommunicationCardAction = communicationAction
    }
    
    func setup(reminders: [KanguroSharedDomain.Reminder]) {
        self.reminders = reminders
        setupLayout()
    }
    
    func reloadData() {
        guard reminders != nil else { return }
        reminderCardsCollectionView.reloadData()
    }
}

// MARK: - UICollectionViewDelegate & UICollectionViewDataSource & UICollectionViewDelegateFlowLayout
extension HorizontalReminderCardList: UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: 146, height: 100)
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return reminders?.count ?? 0
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: HorizontalReminderCardCell.identifier, for: indexPath) as? HorizontalReminderCardCell,
              let reminders = reminders else { return UICollectionViewCell() }
        
        let currentReminder = reminders[indexPath.row]
        
        cell.viewModel = ReminderCardCellViewModel(reminder: currentReminder)
        cell.setup(medicalHistoryAction: didTapMedicalHistoryCardAction,
                   communicationAction: didTapCommunicationCardAction)
        cell.animate()
        return cell
    }
}

// MARK: IB Actions
extension HorizontalReminderCardList {
    
    @IBAction func seeAllTouchUpInside(_ sender: UIButton) {
        didTapSeeAllAction()
    }
}
