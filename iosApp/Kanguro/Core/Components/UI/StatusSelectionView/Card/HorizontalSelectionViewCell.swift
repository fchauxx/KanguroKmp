import UIKit

class HorizontalSelectionButtonCell: UICollectionViewCell, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var imageView: UIImageView!
    @IBOutlet private var titleLabel: CustomLabel!
    
    // MARK: - Stored Properties
    private var pet: Pet?
    
    // MARK: - Computed Properties
    override var isSelected: Bool {
        didSet {
            if isSelected {
                contentView.backgroundColor = .primaryLight
                contentView.borderColor = .primaryLight
            } else {
                contentView.backgroundColor = .white
                contentView.borderColor = .neutralBackground
            }
        }
    }
}

// MARK: - Life Cycle
extension ReminderButtonViewCell {
    
    override func prepareForReuse() {
        super.prepareForReuse()
        titleLabel.text = ""
        imageView.image = nil
    }
}

// MARK: - Setup
extension ReminderButtonViewCell {
    
    private func setupLayout() {
        setupLabels()
        setupImage()
    }
    
    private func setupLabels() {
        let name = pet?.name ?? "reminders.all.label".localized
        titleLabel.set(text: name, style: TextStyle(color: .secondary, size: .p16))
        titleLabel.sizeToFit()
    }
    
    private func setupImage() {
        imageView.kf.setImage(with: pet?.petPictureResource, placeholder: pet?.placeholderImage)
        pet != nil ? imageView.setAsCircle() : (imageView.isHidden = true)
    }
}

// MARK: - Public Methods
extension ReminderButtonViewCell {
    
    func setup(pet: Pet? = nil) {
        self.pet = pet
        setupLayout()
    }
    
    func update(isSelected: Bool) {
        self.isSelected = isSelected
    }
}
