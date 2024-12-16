import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class ReminderButtonView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var backgroundView: UIView!
    @IBOutlet private var imageView: UIImageView!
    @IBOutlet private var titleLabel: CustomLabel!
    
    // MARK: - Stored Properties
    var pet: Pet?
    private var isSelected = false
    
    // MARK: - Actions
    var didTapButtonAction: OptionalPetClosure = { _ in }
    
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
extension ReminderButtonView {
    
    private func setupLayout() {
        setupImages()
        setupViews()
        setupLabels()
    }
    
    private func setupLabels() {
        let name = pet?.name ?? "reminders.all.label".localized
        titleLabel.set(text: name, style: TextStyle(color: .secondaryDarkest, size: .p16))
    }
    
    private func setupImages() {
        imageView.kf.setImage(with: pet?.petPictureResource, placeholder: pet?.placeholderImage)
        imageView.setAsCircle()
        imageView.isHidden = (pet == nil)
    }
    
    private func setupViews() {
        backgroundView.backgroundColor = isSelected ? .primaryLight : .white
        backgroundView.borderColor = isSelected ? .primaryLight : .secondaryLightest
    }
}

// MARK: - Public Methods
extension ReminderButtonView {
    
    func setup(pet: Pet? = nil) {
        self.pet = pet
        setupLayout()
    }
    
    func update(isSelected: Bool) {
        self.isSelected = isSelected
        setupViews()
    }
}

// MARK: - IB Actions
extension ReminderButtonView {
    
    @IBAction private func buttonTouchUpInside(_ sender: UIButton) {
        didTapButtonAction(pet)
    }
}
