import UIKit

// MARK: - Dequeue
extension UITableView {
    
    func dequeueReusableCell<Cell: UITableViewCell>(for indexPath: IndexPath) -> Cell? {
        return dequeueReusableCell(withIdentifier: Cell.identifier, for: indexPath) as? Cell
    }
}

// MARK: - Register
extension UITableView {
    
    func register(identifiers: [String]) {
        for identifier in identifiers {
            let nib = UINib(nibName: identifier, bundle: nil)
            register(nib, forCellReuseIdentifier: identifier)
        }
    }
    
    func scrollToBottom(animated: Bool = true) {
        let section = numberOfSections - 1
        let row = numberOfRows(inSection: section) - 1
        let indexPath = IndexPath(row: row,
                                  section: section)
        
        DispatchQueue.main.async { [weak self] in
            guard let self else { return }
            self.scrollToRow(at: indexPath, at: .bottom, animated: animated)
        }
    }
}
