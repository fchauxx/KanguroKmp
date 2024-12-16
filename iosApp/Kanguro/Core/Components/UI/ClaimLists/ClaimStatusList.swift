import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class ClaimStatusList: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet var mainTableView: UITableView!
    @IBOutlet var centerLabel: CustomLabel!
    
    // MARK: - Actions
    var didTapAddButtonAction: SimpleClosure = {}
    var goToDetailsAction: ClaimClosure = { _ in }
    var goToNewClaimAction: NextStepClosure = { _ in }
    var didTapPayVetAction: ClaimClosure = { _ in }

    // MARK: - Stored Properties
    var petClaims: [PetClaim]?
    let refreshControl = UIRefreshControl()
    
    // MARK: - Computed Properties
    lazy var claimsSortedByNewest: [PetClaim]? = {
        guard let claims = petClaims else { return nil }
        let sortedList: [PetClaim]? = claims.sorted(by: {
            guard let date1 = $0.updatedAt,
                  let date2 = $1.updatedAt else { return false }
            return (date1) > (date2)
        })
        return sortedList ?? claims
    }()
    
    // MARK: - Actions
    @objc var didDragAction: SimpleClosure = {}
    
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
extension ClaimStatusList {
    
    private func setupLayout() {
        setupCells()
        setupTableView()
        setupLabels()
        setupRefreshControl()
    }
    
    func setupLabels() {
        centerLabel.set(text: "claimStatus.noClaim.label".localized,
                        style: TextStyle(color: .secondaryMedium, size: .p16))
    }
    
    private func setupTableView() {
        mainTableView.rowHeight = UITableView.automaticDimension
        mainTableView.contentInset = UIEdgeInsets(top: 0, left: 0, bottom: 72, right: 0)
    }
    
    private func setupRefreshControl() {
        refreshControl.addTarget(self, action: #selector(reloadData(_:)), for: UIControl.Event.valueChanged)
        mainTableView.addSubview(refreshControl)
    }
    
    private func setupCells() {
        mainTableView.register(identifiers: [OpenClaimCard.identifier,
                                             ClosedClaimCard.identifier,
                                             DraftClaimCard.identifier])
    }
    
    func isClaimsEmpty(_ isEmpty: Bool) {
        mainTableView.isHidden = isEmpty
        centerLabel.isHidden = !isEmpty
    }
    
    @objc func reloadData(_ : AnyObject) {
        didDragAction()
    }
}

// MARK: - Public Methods
extension ClaimStatusList {
    
    func setup(claims: [PetClaim]) {
        self.petClaims = claims
        mainTableView.reloadData()
        refreshControl.endRefreshing()
        isClaimsEmpty(claims.isEmpty)
        setupLayout()
    }
    
    func update(goToDetailsAction: @escaping ClaimClosure) {
        self.goToDetailsAction = goToDetailsAction
    }
    
    func update(didTapPayVetAction: @escaping ClaimClosure) {
        self.didTapPayVetAction = didTapPayVetAction
    }
}

// MARK: - UITableViewDelegate & UITableViewDataSource
extension ClaimStatusList: UITableViewDelegate, UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        guard let claims = petClaims else { return 0 }
        return claims.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        guard let claims = claimsSortedByNewest else { return UITableViewCell() }
        
        let currentClaim = claims[indexPath.row]
        switch currentClaim.status {
        case .Submitted, .Assigned, .InReview:
            if let openStatusCell: OpenClaimCard = tableView.dequeueReusableCell(for: indexPath) {
                openStatusCell.update(claim: currentClaim)
                openStatusCell.update(goToDetailsAction: goToDetailsAction)
                openStatusCell.update(didTapPayVetAction: didTapPayVetAction)
                return openStatusCell
            }
        case .Closed, .Denied, .Approved, .Paid:
            if let closedStatusCell: ClosedClaimCard = tableView.dequeueReusableCell(for: indexPath) {
                closedStatusCell.update(claim: currentClaim)
                closedStatusCell.update(goToDetailsAction: goToDetailsAction)
                return closedStatusCell
            }
        default:
            break
        }
        return UITableViewCell()
    }
}
