import UIKit

struct VerticalStepViewData {
    
    let iconName: String
    let title: String
    let description: String
    let highlightedDescription: String?
    let isLastItem: Bool
    
    init(iconName: String,
         title: String,
         description: String,
         highlightedDescription: String? = nil,
         isLastItem: Bool = false) {
        self.iconName = iconName
        self.title = title
        self.description = description
        self.highlightedDescription = highlightedDescription
        self.isLastItem = isLastItem
    }
}

class VerticalStepView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var descriptionLabel: CustomLabel!
    @IBOutlet private var descriptionLabelBottomConstraint: NSLayoutConstraint!
    
    @IBOutlet private var iconImageView: UIImageView!
    @IBOutlet private var stickView: UIView!
    
    // MARK: - Stored Properties
    var data: VerticalStepViewData?
    
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
extension VerticalStepView {
    
    func setup(data: VerticalStepViewData) {
        self.data = data
        setupLabels()
        setupViews()
    }
    
    private func setupLabels() {
        guard let data else { return }
        titleLabel.set(text: data.title.uppercased(),
                       style: TextStyle(color: .primaryDarkest, weight: .black))
        descriptionLabel.setHighlightedText(text: data.description,
                                            style: TextStyle(color: .secondaryDark,
                                                             weight: .bold,
                                                             size: .p16),
                                            highlightedText: data.highlightedDescription ?? "")
    }
    
    private func setupViews() {
        guard let data else { return }
        descriptionLabelBottomConstraint.constant = data.isLastItem ? 0 : 24
        
        iconImageView.image = UIImage(named: "\(data.iconName)")
        iconImageView.tintColor = .primaryDarkest
        
        stickView.isHidden = data.isLastItem
    }
}
