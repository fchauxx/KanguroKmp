import UIKit

// MARK: - Computed Properties
extension UITableViewCell {
    
    static var identifier: String {
        String(describing: Self.self)
    }
}

// MARK: - Methods
extension UITableViewCell {
    
    func setCellSpacement(top: CGFloat = 0, left: CGFloat = 0, bottom: CGFloat = 0, right: CGFloat = 0) {
        contentView.frame = contentView.frame.inset(by: UIEdgeInsets(top: top, left: left, bottom: bottom, right: right))
    }
    
    func animate(delay: Double = 0) {
        alpha = 0
        UIView.animate(withDuration: 0.3, delay: delay, options: .curveEaseIn) { [weak self] in
            guard let self = self else { return }
            self.alpha = 1.0
        }
    }
}
