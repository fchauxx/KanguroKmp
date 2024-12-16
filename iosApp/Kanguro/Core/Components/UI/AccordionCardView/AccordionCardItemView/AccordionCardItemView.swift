import UIKit

class AccordionCardItemView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var backgroundView: UIView!
    @IBOutlet private var leadingTitleLabel: CustomLabel!
    @IBOutlet private var traillingTitleLabel: CustomLabel!
    
    // MARK: - Stored Properties
    var data: AccordionCardItemData?
    
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

// MARK: - Life Cycle
extension AccordionCardItemView {
    
    override func layoutSubviews() {
        super.layoutSubviews()
        setupLayout()
        layoutIfNeeded()
    }
}

// MARK: - Setup
extension AccordionCardItemView {
    
    private func setupLayout() {
        setupLabels()
    }
    
    private func setupLabels() {
        guard let data = data else { return }
        leadingTitleLabel.set(text: data.leadingTitle.uppercased(),
                              style: TextStyle(color: .secondaryDark, size: .p12))
        traillingTitleLabel.set(text: data.traillingTitle,
                                style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p16))
    }
}

// MARK: - Public Methods
extension AccordionCardItemView {
    
    func update(data: AccordionCardItemData, backgroundColor: CardBackgroundColor) {
        self.data = data
        self.backgroundView.backgroundColor = backgroundColor.color
    }
}
