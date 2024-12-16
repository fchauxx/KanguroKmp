import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class CoverageCardCell: UICollectionViewCell {
    
    // MARK: - IB Outlets
    @IBOutlet private var petPhotoView: UIView!
    @IBOutlet private var petPhotoImageView: UIImageView!
    @IBOutlet private var petNameLabel: CustomLabel!
    @IBOutlet private var petInfoLabel: CustomLabel!
    @IBOutlet private var statusLabel: CustomLabel!
    @IBOutlet private var seeDetailsLabel: CustomLabel!
    @IBOutlet private var statusView: UIView!
    
    // MARK: - Stored Properties
    var policy: PetPolicy?
}

// MARK: - Setup
extension CoverageCardCell {
    
    private func setupLayout() {
        setupLabels()
        setupViews()
        setupImages()
    }
    
    private func setupLabels() {
        guard let pet = policy?.pet else { return }
        let name = pet.name ?? ""
        let age = pet.birthDate?.ageText ?? ""
        var petInfoText = age
        
        if let breed = pet.breed { petInfoText = breed + ", " + age }
        petInfoLabel.set(
            text: petInfoText,
            style: TextStyle(
                color: .secondaryDarkest,
                weight: .medium,
                size: .p14
            )
        )
        petNameLabel.set(
            text: name,
            style: TextStyle(
                weight: .bold,
                size: .p21,
                font: .raleway
            )
        )
        seeDetailsLabel.set(
            text: "dashboard.seeDetails.label".localized,
            style: TextStyle(
                color: .tertiaryExtraDark,
                weight: .bold,
                size: .p11,
                underlined: true
            )
        )
        statusLabel.set(
            text: policy?.status?.title ?? "",
            style: TextStyle(
                color: .neutralMedium,
                weight: .regular,
                size: .p12
            )
        )
        petNameLabel.setupToFitWidth()
        petInfoLabel.setupToFitWidth()
    }
    
    private func setupViews() {
        statusView.setAsCircle()
        statusView.backgroundColor = policy?.status?.color
        self.setShadow(shadowOffset: CGSize(width: 2, height: 2))
    }
    
    private func setupImages() {
        guard let placeholder = policy?.pet.placeholderImage else { return }
        petPhotoImageView.kf.setImage(with: policy?.pet.petPictureResource, placeholder: placeholder)
    }
}

// MARK: - Public Methods
extension CoverageCardCell {
    
    func setup(policy: PetPolicy) {
        self.policy = policy
        setupLayout()
    }
}
