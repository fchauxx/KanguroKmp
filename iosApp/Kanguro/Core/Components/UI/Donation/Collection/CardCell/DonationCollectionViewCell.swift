import UIKit
import KanguroSharedDomain

class DonationCollectionViewCell: BaseCollectionViewCell {

    // MARK: - IBOutlets
    @IBOutlet private var imageView: UIImageView!
    @IBOutlet private var titleLabel: CustomLabel!
    
    // MARK: - Stored Properties
    var type: DonationType?
}

// MARK: - Setup
extension DonationCollectionViewCell {

    private func setupLayout() {
        setupLabels()
        setupImages()
    }
    
    private func setupLabels() {
        guard let type = type else { return }
        titleLabel.set(text: type.title,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p16))
    }
    
    private func setupImages() {
        guard let type = type else { return }
        imageView.image = type.image
    }
}

// MARK: - Public Methods
extension DonationCollectionViewCell {

    func setup(type: DonationType) {
        self.type = type
        setupLayout()
    }
}
