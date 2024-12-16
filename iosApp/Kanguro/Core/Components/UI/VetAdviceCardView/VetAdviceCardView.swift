import UIKit

enum VetAdviceType: String {
    
    case dog = "Dog"
    case cat = "Cat"
    
    var title: String {
        switch self {
        case .dog:
            return "vetAdvice.dog.name".localized
        case .cat:
            return "vetAdvice.cat.name".localized
        }
    }
    var description: String {
        switch self {
        case .dog:
            return "vetAdvice.dog.description".localized
        case .cat:
            return "vetAdvice.cat.description".localized
        }
    }
    var photo: UIImage? {
        switch self {
        case .dog:
            return UIImage(named: "vetAdvice-dog")
        case .cat:
            return UIImage(named: "vetAdvice-cat")
        }
    }
}

class VetAdviceCardView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var imageView: UIImageView!
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var descriptionLabel: CustomLabel!
    @IBOutlet private var seeAdvicesLabel: CustomLabel!
    
    // MARK: - Stored Properties
    var type: VetAdviceType?
    
    // MARK: - Actions
    var didTapCardAction: VetAdviceTypeClosure = { _ in }
    
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
extension VetAdviceCardView {
    
    private func setupLayout() {
        setupLabels()
        setupImages()
        setupViews()
    }
    
    private func setupViews() {
        self.setShadow()
    }
    
    private func setupLabels() {
        guard let type = type else { return }
        titleLabel.set(text: type.title,
                       style: TextStyle(color: .primaryDarkest, weight: .bold, size: .p21, font: .raleway))
        descriptionLabel.set(text: type.description,
                             style: TextStyle(color: .secondaryDarkest))
        seeAdvicesLabel.set(text: "vetAdvice.seeAdvices.label".localized,
                            style: TextStyle(color: .tertiaryExtraDark, size: .p11, underlined: true))
    }
    
    private func setupImages() {
        guard let type = type else { return }
        imageView.image = type.photo
    }
}

// MARK: - Public Methods
extension VetAdviceCardView {
    
    func setupType(_ type: VetAdviceType) {
        self.type = type
        setupLayout()
    }
    
    func update(_ tapAction: @escaping VetAdviceTypeClosure) {
        self.didTapCardAction = tapAction
    }
}

// MARK: - IB Actions
extension VetAdviceCardView {
    
    @IBAction private func cardButtonTouchUpInside(_ sender: UIButton) {
        guard let type = type else { return }
        didTapCardAction(type)
    }
}
