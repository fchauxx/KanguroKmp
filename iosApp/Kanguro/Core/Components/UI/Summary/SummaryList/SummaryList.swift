import UIKit

class SummaryList: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet var stackView: UIStackView!
    
    // MARK: - Stored Properties
    var title: String?
    
    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
    }
}

// MARK: - Setup
extension SummaryList {
    
    func setupLabels() {
        guard let title = title else { return }
        titleLabel.set(text: title, style: TextStyle(weight: .bold, size: .p12))
    }
}

// MARK: - Public Methods
extension SummaryList {
    
    func addSummaryCard(summaryData: SummaryData) {
        let summaryCard = SummaryCard()
        summaryCard.update(data: summaryData)
        stackView.addArrangedSubview(summaryCard)
        stackView.layoutIfNeeded()
    }
    
    func update(title: String) {
        self.title = title
        setupLabels()
    }
    
    func cleanAll() {
        stackView.removellArrangedSubviews()
    }
}
