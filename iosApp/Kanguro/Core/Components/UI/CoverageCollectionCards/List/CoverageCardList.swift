import UIKit
import KanguroDesignSystem
import SwiftUI
import KanguroSharedDomain
import KanguroPetDomain

class CoverageCardList: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var titleLabelView: UIView!
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet var statusSelectionView: StatusSelectionView!
    @IBOutlet private var statusSelectionBgView: UIView!
    @IBOutlet var cardsListCollectionView: UICollectionView!
    
    // MARK: - Actions
    var didTapAddButtonAction: SimpleClosure = {}
    var didTapItemAction: IntClosure = { _ in }
    var didTapAirVetCardAction: SimpleClosure = {}

    // MARK: - Stored Properties
    var policies: [PetPolicy] = []
    var policiesByStatus: [PetPolicy] = []
    var title: String?
    var cardPosition: CGPoint?
    var shouldShowAirVetCard: Bool = true
    var dataSource: UICollectionViewDiffableDataSource<CoverageCardSection, CoverageCardItem>!

    // MARK: - Computed Properties
    var pets: [Pet] {
        return policiesByStatus.map { $0.pet }
    }
    var hasDifferentStatus: Bool {
        let containsActive = policies.map { $0.status }.contains(where: { $0 == .ACTIVE })
        let containsInactive = policies.map { $0.status }.contains(where: { $0 != .ACTIVE })
        
        return containsActive && containsInactive
    }
    var activePolicies: [PetPolicy] {
        return policies.filter { $0.status == .ACTIVE }
    }
    var inactivePolicies: [PetPolicy] {
        return policies.filter { $0.status == .CANCELED }
    }
    
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
extension CoverageCardList {
    
    private func setupLayout() {
        setupLabels()
        setupCells()
        setupCollection()
        setupSelectionView()
        configureDataSource()
    }
    
    func setupLabels() {
        if let title {
            titleLabelView.isHidden = false
            titleLabel.set(
                text: title.uppercased(),
                style: TextStyle(color: .secondaryDark, weight: .bold, size: .p12)
            )
        }
    }
    
    private func setupCollection() {
        let layout = VerticalCenterFlowLayout() // custom class that allows items to be vertically centered in section
        layout.scrollDirection = .horizontal
        cardsListCollectionView.collectionViewLayout = layout
        cardsListCollectionView.contentInset.left = 24
        setupCardsByStatus(status: .none)
    }
    
    private func setupCells() {
        let coverageCard = UINib(nibName: CoverageCardCell.identifier, bundle: nil)
        cardsListCollectionView.register(coverageCard, forCellWithReuseIdentifier: CoverageCardCell.identifier)
        
        let addButton = UINib(nibName: AddButtonCell.identifier, bundle: nil)
        cardsListCollectionView.register(addButton, forCellWithReuseIdentifier: AddButtonCell.identifier)

        let airvetCard = UINib(nibName: AirVetCardCell.identifier, bundle: nil)
        cardsListCollectionView.register(airvetCard, forCellWithReuseIdentifier: AirVetCardCell.identifier)
    }
    
    func setupSelectionView() {
        statusSelectionBgView.isHidden = !hasDifferentStatus
        statusSelectionView.didTapButtonAction = { [weak self] status in
            guard let self else { return }
            self.setupCardsByStatus(status: status)
            self.cardsListCollectionView.scrollToLeft()
        }
    }
    
    private func updateOrder() {
        policiesByStatus = policiesByStatus.sorted(by: { $0.pet.id > $1.pet.id })
    }
}

// MARK: - Private Methods
extension CoverageCardList {
    
    func setupCardsByStatus(status: Status) {
        var list: [PetPolicy] = []
        policies.forEach { policy in
            guard let policyStatus = policy.status else { return }
            if status.policyStatus.contains(policyStatus) {
                list.append(policy)
            }
        }
        policiesByStatus = list
        updateOrder()
        cardsListCollectionView.reloadData()
    }
}

// MARK: - Public Methods
extension CoverageCardList {
    
    func setup(policies: [PetPolicy], title: String? = nil, shouldShowAirVetCard: Bool = true) {
        self.policies = policies
        self.title = title
        self.shouldShowAirVetCard = shouldShowAirVetCard
        setupLayout()
        updateDataSource()
    }
}

// MARK: - UICollectionViewDelegate & UICollectionViewDelegateFlowLayout
extension CoverageCardList: UICollectionViewDelegate, UICollectionViewDelegateFlowLayout {

    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return shouldShowAirVetCard ? 3 : 2
    }
    
    func collectionView(
        _ collectionView: UICollectionView,
        layout collectionViewLayout: UICollectionViewLayout,
        sizeForItemAt indexPath: IndexPath
    ) -> CGSize {
        if indexPath.section == 0 && shouldShowAirVetCard {
            return CGSize(width: 157, height: 225)
        } else if indexPath.section == (shouldShowAirVetCard ? 2 : 1) {
            return CGSize(width: 40, height: 40)
        }
        return CGSize(width: 157, height: 225)
    }


    func collectionView(
        _ collectionView: UICollectionView,
        layout collectionViewLayout: UICollectionViewLayout,
        minimumLineSpacingForSectionAt section: Int
    ) -> CGFloat {
        return InsetSpacing.nano  // Item spacing
    }

    func collectionView(
        _ collectionView: UICollectionView,
        layout collectionViewLayout: UICollectionViewLayout,
        insetForSectionAt section: Int
    ) -> UIEdgeInsets {
        return UIEdgeInsets(top: 0, left: 0, bottom: 0, right: InsetSpacing.nano) // Section Spacing
    }

    func collectionView(
        _ collectionView: UICollectionView,
        willDisplay cell: UICollectionViewCell,
        forItemAt indexPath: IndexPath
    ) {
        cell.alpha = 0
        UIView.animate(withDuration: 0.2, delay: 0.1 * Double(indexPath.row)) {
            cell.alpha = 1
        }
    }

    func collectionView(
        _ collectionView: UICollectionView,
        didSelectItemAt indexPath: IndexPath
    ) {
        guard let cell = collectionView.cellForItem(at: indexPath) else { return }
        cardPosition = cell.pointRelatedToScreen
        didTapItemAction(indexPath.row)
    }
}

// MARK: - UICollectionViewDiffableDateSource config and update
extension CoverageCardList {

    func configureDataSource() {
        dataSource = UICollectionViewDiffableDataSource<CoverageCardSection, CoverageCardItem>(
            collectionView: cardsListCollectionView
        ) {
            (collectionView: UICollectionView, indexPath: IndexPath, item: CoverageCardItem) -> UICollectionViewCell? in

            let section = self.dataSource.snapshot().sectionIdentifiers[indexPath.section]

            switch section {
            case .airVetCard where item.shouldShowAirVetCard:
                let cell = collectionView.dequeueReusableCell(
                    withReuseIdentifier: AirVetCardCell.identifier,
                    for: indexPath
                ) as? AirVetCardCell

                guard let parentViewController = self.parentViewController else {
                    debugPrint("No parent view controller found...")
                    return nil
                }

                cell?.setup(
                    in: parentViewController,
                    action: self.didTapAirVetCardAction
                )

                return cell
            case .policies:
                let cell = collectionView.dequeueReusableCell(
                    withReuseIdentifier: CoverageCardCell.identifier,
                    for: indexPath
                ) as? CoverageCardCell

                if let policy = item.policy {
                    cell?.setup(policy: policy)
                }
                return cell
            case .addButton:
                let cell = collectionView.dequeueReusableCell(
                    withReuseIdentifier: AddButtonCell.identifier,
                    for: indexPath
                ) as? AddButtonCell

                cell?.setup(didTapAddButtonAction: self.didTapAddButtonAction)
                return cell
            default:
                return nil
            }
        }
    }


    func updateDataSource() {
        var snapshot = NSDiffableDataSourceSnapshot<CoverageCardSection, CoverageCardItem>()

        if shouldShowAirVetCard {
            snapshot.appendSections([.airVetCard])
            snapshot.appendItems([CoverageCardItem(shouldShowAirVetCard: true)], toSection: .airVetCard)
        }

        if !policiesByStatus.isEmpty {
            snapshot.appendSections([.policies])
            let items = policiesByStatus.map { CoverageCardItem(policy: $0) }
            snapshot.appendItems(items, toSection: .policies)
        }

        snapshot.appendSections([.addButton])
        snapshot.appendItems([CoverageCardItem()], toSection: .addButton)

        dataSource.apply(snapshot, animatingDifferences: true)
    }
}

