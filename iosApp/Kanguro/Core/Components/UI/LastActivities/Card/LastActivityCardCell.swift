import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class LastActivityCardCell: UITableViewCell {
    
    // MARK: - IB Outlets
    @IBOutlet private var placeNameLabel: CustomLabel!
    @IBOutlet var petNameLabel: CustomLabel!
    @IBOutlet private var dateLabel: CustomLabel!
    @IBOutlet private var rateLabel: CustomLabel!
    @IBOutlet var petPhotoImageView: UIImageView!
    @IBOutlet private var ratingStars: RatingStars!
    @IBOutlet private var detailsButton: CustomButton!
    
    // MARK: - Stored Properties
    var lastActivity: PetLastActivity?
}

// MARK: - Life Cycle
extension LastActivityCardCell {
    
    override func layoutSubviews() {
        super.layoutSubviews()
        setCellSpacement(top: 4)
    }
}

// MARK: - Setup
extension LastActivityCardCell {
    
    func setup(lastActivity: PetLastActivity) {
        self.lastActivity = lastActivity
        
        setupLabels()
        setupImages()
        setupRatingStars()
        setupDetailsButton()
    }
    
    private func setupLabels() {
        guard let lastActivity = lastActivity,
              let pet = lastActivity.pet,
              let lastVisit = lastActivity.lastVisit else { return }
        placeNameLabel.set(text: lastActivity.placeName ?? "", style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p16))
        petNameLabel.set(text: "\(pet.name ?? "") -", style: TextStyle(color: .secondaryDark, weight: .regular, size: .p11))
        dateLabel.set(text: lastVisit.USADate, style: TextStyle(color: .secondaryDark, weight: .regular, size: .p11))
        rateLabel.set(text: "lastActivityCard.rate.label".localized, style: TextStyle(color: .secondaryMedium, weight: .regular, size: .p11))
    }
    
    private func setupImages() {
        petPhotoImageView.setAsCircle()
    }
    
    private func setupRatingStars() {
        guard let lastActivity = lastActivity,
              let rate = lastActivity.rate else { return }
        ratingStars.update(rate: rate)
    }
    
    private func setupDetailsButton() {
        detailsButton.set(title: "lastActivityCard.details.label".localized, style: .outlined)
    }
}
