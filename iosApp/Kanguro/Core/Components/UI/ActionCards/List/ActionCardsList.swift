import UIKit

enum CardBackgroundColor {
    
    case white
    case neutralBackground
    
    var color: UIColor {
        switch self {
        case .white:
            return .white
        case .neutralBackground:
            return .neutralBackground
        }
    }
}

class ActionCardsList: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet private var mainStackView: UIStackView!
    @IBOutlet private var actionCardStackView: UIStackView!
    
    // MARK: - Stored Properties
    var title: String?
    var actions: [ActionCardData]?
    var cardBGColor: CardBackgroundColor? = .white
    
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
extension ActionCardsList {
    
    override func layoutSubviews() {
        super.layoutSubviews()
        setupLayout()
    }
}

// MARK: - Private Methods
extension ActionCardsList {
    
    private func setupLayout() {
        setupLabels()
        setupStackViewSpacement()
    }
    
    func setupLabels() {
        guard let title = title else { return }
        titleLabel.set(text: title, style: TextStyle(color: .secondaryDark, weight: .bold, size: .p12))
    }
    
    private func setupStackViewSpacement() {
        actionCardStackView.spacing = (cardBGColor == .white ? 2 : 6)
        mainStackView.spacing = (cardBGColor == .white ? 16 : 8)
    }
}

// MARK: - Public Methods
extension ActionCardsList {
    
    func update(title: String) {
        self.title = title.uppercased()
        self.titleLabel.isHidden = false
    }
    
    func update(cardBackgroundColor: CardBackgroundColor) {
        self.cardBGColor = cardBackgroundColor
    }
    
    func addActionCards(actionCardDataList: [ActionCardData]) {
        guard let cardBGColor else { return }
        for item in actionCardDataList {
            let actionCard = ActionCard()
            actionCard.setup(actionCardData: item, backgroundColor: cardBGColor)
            actionCardStackView.addArrangedSubview(actionCard)
        }
        actionCardStackView.layoutIfNeeded()
    }
    
    func clearActionCards() {
        actionCardStackView.removellArrangedSubviews()
    }
}
