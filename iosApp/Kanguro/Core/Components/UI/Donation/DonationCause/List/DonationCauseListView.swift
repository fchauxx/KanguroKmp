import UIKit
import KanguroSharedDomain

class DonationCauseListView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var stackView: UIStackView!
    
    // MARK: - Computed Properties
    var stackViewSubviews: [DonationCauseCardView] {
        return stackView.subviews as? [DonationCauseCardView] ?? []
    }
    
    // MARK: - Actions
    var didTapChooseButtonAction: DonationCauseSelectedTypeClosure = { _ in }
    var didTapChooseToChangeCause: SimpleClosure = {}

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

// MARK: - Private Methods
extension DonationCauseListView {
    
    private func setupActions() {
        for item in stackViewSubviews {
            item.didTapExpansionAction = { [weak self] in
                guard let self else { return }
                self.closeAccordionViews(openView: item)
            }
            item.didTapChooseButtonAction = { [weak self] selectedCause in
                guard let self else { return }
                self.didTapChooseButtonAction(selectedCause)
            }
            item.didTapChooseToChangeCause = { [weak self] in
                guard let self else { return }
                self.didTapChooseToChangeCause()
            }
        }
    }
    
    private func closeAccordionViews(openView: DonationCauseCardView) {
        var accordionViews = stackViewSubviews
        if let index = accordionViews.firstIndex(where: { $0 === openView }) {
            accordionViews.remove(at: index)
        }
        if openView.isExpanded {
            accordionViews.forEach { view in
                if view.isExpanded { view.close() }
            }
        }
    }
}

// MARK: - Public Methods
extension DonationCauseListView {
    
    func setup(donationCauseList: [DonationCause]) {
        for data in donationCauseList {
            let card = DonationCauseCardView()
            card.setup(data: data)
            stackView.addArrangedSubview(card)
        }
        stackView.layoutIfNeeded()
        setupActions()
    }
}
