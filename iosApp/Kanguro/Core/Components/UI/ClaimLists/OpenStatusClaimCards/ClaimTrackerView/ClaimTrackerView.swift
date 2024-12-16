import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class ClaimTrackerView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var stackView: UIStackView!
    
    // MARK: - Stored Properties
    private var currentClaim: PetClaim?
    private var statusList: [ClaimStatus]?
    
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
extension ClaimTrackerView {
    
    private func setupStackView() {
        guard let statusList = statusList,
              let currentStatus = currentClaim?.status else { return }
        
        statusList.forEach { status in
            let trackerItem = ClaimTrackerItemView()
            let data = ClaimTrackerViewItemData(status: status,
                                                isConfirmed: status.index <= currentStatus.index,
                                                hasPendingCommunications: currentClaim?.isPendingCommunication ?? false,
                                                isNextItemConfirmed: (status.index + 1) <= currentStatus.index,
                                                isNextLastConfirmedItem: (status.index + 1) == currentStatus.index)
            
            trackerItem.setup(data: data)
            if stackView.arrangedSubviews.count < statusList.count {
                stackView.addArrangedSubview(trackerItem)
            }
        }
        stackView.layoutIfNeeded()
    }
}

// MARK: - Public Methods
extension ClaimTrackerView {
    
    func setup(statusList: [ClaimStatus], currentClaim: PetClaim) {
        self.statusList = statusList
        self.currentClaim = currentClaim
        setupStackView()
    }
    
    func resetData() {
        currentClaim = nil
        stackView.removellArrangedSubviews()
    }
}
