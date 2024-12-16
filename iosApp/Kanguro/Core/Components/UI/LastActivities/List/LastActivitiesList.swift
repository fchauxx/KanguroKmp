import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class LastActivitiesList: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var cardsListTableView: UITableView!
    @IBOutlet private var tableViewHeight: NSLayoutConstraint!
    
    // MARK: - Stored Properties
    var lastActivities: [PetLastActivity]?
    let rowHeight = 127
    
    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
    }
}

// MARK: - Life Cycle
extension LastActivitiesList {
    
    override func layoutSubviews() {
        super.layoutSubviews()
        setupLayout()
    }
}

// MARK: - Setup
extension LastActivitiesList {
    
    private func setupLayout() {
        setupLabels()
        setupCells()
        setupTableView()
    }
    
    private func setupLabels() {
        titleLabel.set(text: "lastActivities.lastActivities.label".localized, style: TextStyle(color: .secondaryDark, weight: .bold, size: .p12))
    }
    
    private func setupCells() {
        cardsListTableView.register(UINib(nibName: LastActivityCardCell.identifier, bundle: nil), forCellReuseIdentifier: LastActivityCardCell.identifier)
    }
    
    private func setupTableView() {
        guard let lastActivities = lastActivities else { return }
        let lastActivitiesQty = lastActivities.count
        tableViewHeight.constant = CGFloat(((lastActivitiesQty <= 3) ? (rowHeight*lastActivitiesQty) : (rowHeight*3)))
    }
}

// MARK: - Public Methods
extension LastActivitiesList {
    func update(lastActivities: [PetLastActivity]) {
        self.lastActivities = lastActivities
    }
}

// MARK: - UITableViewDelegate & UITableViewDataSource
extension LastActivitiesList: UITableViewDelegate, UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        guard let lastActivities = lastActivities else { return 0 }
        return lastActivities.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: LastActivityCardCell.identifier, for: indexPath) as? LastActivityCardCell,
              let lastActivities = lastActivities else { return UITableViewCell() }
        
        cell.setup(lastActivity: lastActivities[indexPath.row])
        return cell
    }
}

