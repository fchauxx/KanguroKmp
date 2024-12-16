import UIKit

class AccordionCardView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet var backgroundView: UIView!
    @IBOutlet var actionCard: ActionCard!
    @IBOutlet var itemsStackView: UIStackView!
    
    // MARK: - Stored Properties
    var title: String?
    var isExpanded: Bool = false
    var cardBGColor: CardBackgroundColor? = .neutralBackground
    
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
extension AccordionCardView {
    
    private func setupLayout() {
        setupItemsStackView()
        setupView()
        setupActionCard()
    }
    
    func setupActionCard() {
        guard let title = title else { return }
        actionCard.setup(actionCardData: ActionCardData(leadingTitle: title,
                                                        didTapAction: changeItemsStackStatus,
                                                        viewType: .accordion(fontSize: .p16)),
                         backgroundColor: .neutralBackground)
    }
    
    func setupItemsStackView() {
        itemsStackView.isHidden = !isExpanded
    }
    
    func setupView() {
        guard let cardBGColor = cardBGColor?.color else { return }
        self.backgroundView.backgroundColor = cardBGColor
    }
}

// MARK: - Private Methods
extension AccordionCardView {
    
    func setColoredBorderIsHidden(_ isHidden: Bool) {
        self.cornerRadius = 8
        self.borderColor = .primaryMedium
        isHidden ? (borderWidth = 0) : (borderWidth = 2)
    }
    
    func changeItemsStackStatus() {
        isExpanded.toggle()
        actionCard.update(isExpanded: isExpanded)
        UIView.animate(withDuration: 0.3) { [weak self] in
            guard let self = self else { return }
            self.setColoredBorderIsHidden(!self.isExpanded)
            self.setupItemsStackView()
            self.itemsStackView.superview?.layoutIfNeeded()
        }
    }
}

// MARK: - Public Methods
extension AccordionCardView {
    
    func update(title: String, backgroundColor: CardBackgroundColor? = .neutralBackground) {
        self.title = title
        self.cardBGColor = backgroundColor
    }
    
    func addItems(accordionItemsData: [AccordionCardItemData]) {
        guard let cardBGColor = cardBGColor else { return }
        for item in accordionItemsData {
            let accordionCardItem = AccordionCardItemView()
            accordionCardItem.update(data: item, backgroundColor: cardBGColor)
            itemsStackView.addArrangedSubview(accordionCardItem)
        }
        setupLayout()
        layoutIfNeeded()
    }
}
