import UIKit
import KanguroSharedDomain

class DonationCollectionView: BaseView, NibOwnerLoadable {

    // MARK: - IB Outlets
    @IBOutlet private var collectionView: UICollectionView!

    // MARK: - Stored Properties
    var donationTypes: [DonationType]?
    var donationCauses: [DonationCause]?
    
    // MARK: - Computed Properties
    var cellWidth: CGFloat {
        return (collectionView.frame.size.width/2) - 6
    }
    var cellHeight: CGFloat {
        return (collectionView.frame.size.height/2) - 6
    }

    // MARK: - Actions
    var didTapDonationCard: DonationTypeClosure = { (_,_) in }

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
extension DonationCollectionView {

    private func setupLayout() {
        setupCollectionView()
        collectionView.reloadData()
    }
    
    private func setupCollectionView() {
        collectionView.register(identifiers: [DonationCollectionViewCell.identifier])
    }
}

// MARK: - Public Methods
extension DonationCollectionView {

    func setup(donationTypes: [DonationType], donationCauses: [DonationCause]) {
        self.donationTypes = donationTypes
        self.donationCauses = donationCauses
        setupLayout()
    }
    
    func update(tapAction: @escaping DonationTypeClosure) {
        self.didTapDonationCard = tapAction
    }
}

// MARK: - UICollectionViewDelegate & UICollectionViewDataSource
extension DonationCollectionView: UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        guard let donationTypes else { return 0 }
        return donationTypes.count
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: cellWidth, height: cellHeight)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
        return 12
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: DonationCollectionViewCell.identifier, for: indexPath) as? DonationCollectionViewCell,
              let currentType = donationTypes?[indexPath.row] else { return UICollectionViewCell() }
        
        cell.setup(type: currentType)
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        guard let currentType = donationTypes?[indexPath.row],
              let donationCauses else { return }
        
        let filteredDonationList = donationCauses.filter { donationCause in
            donationCause.attributes.cause == currentType
        }
        didTapDonationCard(currentType, filteredDonationList)
    }
}
