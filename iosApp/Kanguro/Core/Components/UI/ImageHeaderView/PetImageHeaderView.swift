import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class PetImageHeaderView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var imageView: UIImageView!
    @IBOutlet private var titleLabel: CustomLabel!
    
    // MARK: - Stored Properties
    var pet: Pet?
    
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
extension PetImageHeaderView {
    
    private func setupLayout() {
        setupLabels()
        setupImages()
    }
    
    private func setupLabels() {
        guard let name = pet?.name else { return }
        titleLabel.set(text: name,
                       style: TextStyle(color: .primaryDarkest, weight: .bold, size: .h28, font: .raleway))
    }
    
    private func setupImages() {
        imageView.kf.setImage(with: pet?.petPictureResource, placeholder: pet?.placeholderImage)
        pet != nil ? imageView.setAsCircle() : (imageView.isHidden = true)
    }
}

// MARK: - Public Methods
extension PetImageHeaderView {
    
    func setup(pet: Pet) {
        self.pet = pet
        setupLayout()
    }
}
