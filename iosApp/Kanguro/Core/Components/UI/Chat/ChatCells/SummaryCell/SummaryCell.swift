import UIKit

class SummaryCell: UITableViewCell, ChatCellProtocol {
    
    // MARK: - IB Outlets
    @IBOutlet private var summaryList: SummaryList!
    
    // MARK: - Stored Properties
    var didAnimate: Bool = false
}

// MARK: - Life Cycle
extension SummaryCell {
    
    override func prepareForReuse() {
        summaryList.cleanAll()
        didAnimate = false
    }
}

// MARK: - Public Methods
extension SummaryCell {
    
    func addSummaryCard(summaryData: SummaryData) {
        summaryList.update(title: "newClaim.summaryCell.cell".localized.uppercased())
        summaryList.addSummaryCard(summaryData: summaryData)
    }
}
