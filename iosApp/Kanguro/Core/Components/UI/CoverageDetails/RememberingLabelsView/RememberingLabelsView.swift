import UIKit

class RememberingLabelsView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var rememberingTopLabel: CustomLabel!
    @IBOutlet private var rememberingDataColoredLabel: CustomLabel!
    @IBOutlet private var rememberingCenterLabel: CustomLabel!
    @IBOutlet private var rememberingBottomLabel: CustomLabel!
    
    // MARK: - Stored Properties
    private var topText: String?
    private var coloredText: String?
    private var centerText: String?
    private var bottomText: String?
    
    
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
extension RememberingLabelsView {
    
    override func layoutSubviews() {
        super.layoutSubviews()
        setupLayout()
    }
}

// MARK: - Setup
extension RememberingLabelsView {
    
    private func setupLayout() {
        setupLabels()
    }
    
    private func setupLabels() {
        guard let centerText = centerText,
              let coloredText = coloredText else { return }
        let basicStyle = TextStyle(color: .secondaryDarkest, size: .h24, font: .raleway)
        rememberingDataColoredLabel.set(text: coloredText, style: TextStyle(color: .tertiaryDark, weight: .black, size: .h24))
        rememberingCenterLabel.set(text: centerText, style: basicStyle)
        if let topText = topText,
           let bottomText = bottomText {
            rememberingTopLabel.set(text: topText, style: basicStyle)
            rememberingBottomLabel.set(text: bottomText, style: basicStyle)
        } else {
            rememberingTopLabel.isHidden = true
            rememberingBottomLabel.isHidden = true
        }
    }
}

// MARK: - Public Methods
extension RememberingLabelsView {
    
    func update(topText: String? = nil, centerText: String, bottomText: String? = nil, coloredText: String) {
        self.topText = topText
        self.centerText = centerText
        self.coloredText = coloredText
        self.bottomText = bottomText
        setupLayout()
    }
}
