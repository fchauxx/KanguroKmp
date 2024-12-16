import UIKit

enum BlotchyLabelsStackViewSide {
    
    case left
    case right
}

class BlotchyLabelsCardView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet var topImageView: UIImageView!
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet var leftStackView: UIStackView!
    @IBOutlet private var rightStackView: UIStackView!
    
    // MARK: - Stored Properties
    var topImage: UIImage?
    var title: String?
    
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
extension BlotchyLabelsCardView {
    
    private func setupLayout() {
        setupLabels()
        setupImages()
    }
    
    private func setupLabels() {
        guard let title = title else { return }
        titleLabel.set(text: title,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h24, font: .raleway))
    }
    
    private func setupImages() {
        guard let topImage = topImage else { return }
        topImageView.image = topImage
    }
}

// MARK: - Public Methods
extension BlotchyLabelsCardView {
    
    func setup(title: String, topImage: UIImage) {
        self.title = title
        self.topImage = topImage
        setupLayout()
    }
    
    func setupStackViews(stackViewSide: BlotchyLabelsStackViewSide, blotchyStringViewData: [BlotchyLabelData], spacing: CGFloat) {
        let stackView = (stackViewSide == .left) ? leftStackView : rightStackView
        for data in blotchyStringViewData {
            let blotchyStringView = BlotchyLabelView()
            blotchyStringView.setup(data: data)
            stackView?.addArrangedSubview(blotchyStringView)
        }
        stackView?.spacing = spacing
        stackView?.layoutIfNeeded()
    }
}
