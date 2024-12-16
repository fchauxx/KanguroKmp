import UIKit
import KanguroSharedDomain

final class AccordionDocsListView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var buttonActionCard: ActionCard!
    @IBOutlet private var stackView: UIStackView!
    
    // MARK: - Stored Properties
    var buttonCardData: ActionCardData?
    var isExpanded: Bool = false
    
    // MARK: - Actions
    var didTapDocumentAction: AnyClosure = { _ in }
    
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
extension AccordionDocsListView {
    
    private func updateStackView() {
        stackView.isHidden = !isExpanded
    }
    
    private func setupButtonActionCard() {
        guard let buttonCardData = buttonCardData else { return }
        buttonActionCard.setup(actionCardData: buttonCardData,
                               backgroundColor: .neutralBackground)
    }
    
    private func updateActionCard() {
        buttonActionCard.update(isExpanded: isExpanded)
    }
}

// MARK: - Private Methods
extension AccordionDocsListView {
    
    private func changeItemsStackStatus() {
        isExpanded.toggle()
        buttonActionCard.update(isExpanded: isExpanded)
        UIView.animate(withDuration: 0.3) { [weak self] in
            guard let self = self else { return }
            self.updateStackView()
            self.updateActionCard()
            self.setColoredBorderHidden(!self.isExpanded)
            self.stackView.superview?.layoutIfNeeded()
        }
    }
    
    func setColoredBorderHidden(_ isHidden: Bool) {
        self.cornerRadius = 8
        self.borderColor = .primaryMedium
        isHidden ? (borderWidth = 0) : (borderWidth = 2)
    }
}

// MARK: - Public Methods
extension AccordionDocsListView {
    
    func addItems(documents: [KanguroSharedDomain.PolicyDocumentData]) {
        for document in documents {
            guard let name = document.name else { return }
            let actionCard = ActionCard()
            let data = ActionCardData(leadingTitle: name,
                                      didTapDataAction: didTapDocumentAction,
                                      viewType: .document,
                                      dataType: .data(document))
            actionCard.setup(actionCardData: data, backgroundColor: .neutralBackground)
            stackView.addArrangedSubview(actionCard)
        }
        updateStackView()
        layoutIfNeeded()
    }
    
    func update(documentAction: @escaping AnyClosure) {
        self.didTapDocumentAction = documentAction
    }
    
    func setupButtonActionCard(data: ActionCardData) {
        self.buttonCardData = data
        self.buttonCardData?.didTapAction = changeItemsStackStatus
        setupButtonActionCard()
    }
}
