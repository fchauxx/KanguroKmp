import UIKit
import SwiftUI
import KanguroSharedDomain
import KanguroPetDomain

class StatusView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var view: UIView!
    
    // MARK: - Stored Properties
    var title: String?
    var hasPendingCommunication: Bool?
    var isPendingMedicalHistory: Bool = false

    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
        setupLabels()
    }
}

// MARK: - Life Cycle
extension StatusView {
    
    override func layoutSubviews() {
        super.layoutSubviews()
        setupLabels()
    }
}

// MARK: - Setup
extension StatusView {
    
    private func setupLabels() {
        let color: UIColor = if hasPendingCommunication == true || isPendingMedicalHistory {
            .secondaryDarkest
        } else {
            .white
        }

        guard let title else { return }
        titleLabel.set(
            text: title,
            style: TextStyle(
                color: color,
                weight: .black,
                size: .p12,
                alignment: .center
            )
        )
    }
}

// MARK: - Public Methods
extension StatusView {
    
    func update(claim: PetClaim, customTitle: String? = nil) {
        guard let claimStatus = claim.status else { return }
        self.hasPendingCommunication = claim.isPendingCommunication
        self.isPendingMedicalHistory = claim.status == .PendingMedicalHistory

        title = customTitle ?? claimStatus.title
        view.backgroundColor = claim.isPendingCommunication ? claimStatus.secondaryColor : claimStatus.primaryColor
    }
}
