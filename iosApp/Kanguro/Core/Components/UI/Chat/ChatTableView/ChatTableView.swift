import UIKit

class ChatTableView: UITableView {}

// MARK: - Setup
extension ChatTableView {
    
    func setup() {
        contentInset = UIEdgeInsets(top: 0, left: 0, bottom: 12, right: 0)
        rowHeight = UITableView.automaticDimension
        estimatedRowHeight = UITableView.automaticDimension
        register(identifiers: [TextCell.identifier, ImageCell.identifier, MapCell.identifier, SummaryCell.identifier])
        self.reloadData()
    }
}
