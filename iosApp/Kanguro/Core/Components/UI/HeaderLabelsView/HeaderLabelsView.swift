import UIKit

class HeaderLabelsView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var topTitleLabel: CustomLabel!
    @IBOutlet private var subtitleLabel: CustomLabel!
    @IBOutlet private var vetView: VeterinaryCardView!
    
    // MARK: - Stored Properties
    var data: TitleLabelsViewData?
    var isVetCard: Bool = true
    
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
extension HeaderLabelsView {
    
    private func setupLayout() {
        setupLabels()
        setupVeterinaryCard()
    }
    
    private func setupLabels() {
        guard let data = data else { return }
        topTitleLabel.setHighlightedText(text: data.topTitle,
                                         style: data.topTitleStyle,
                                         highlightedText: data.topHighlightedTitle ?? "",
                                         highlightedStyle: data.topHighlightedStyle)
        if let subtitle = data.subtitle {
            subtitleLabel.set(text: subtitle,
                              style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p16))
        } else {
            subtitleLabel.isHidden = true
        }
    }
    
    private func setupVeterinaryCard() {
        vetView.isHidden = !isVetCard
    }
}

// MARK: - Public Methods
extension HeaderLabelsView {
    
    func setup(_ data: TitleLabelsViewData, isVetCard: Bool) {
        self.data = data
        self.isVetCard = isVetCard
        setupLayout()
    }
}
