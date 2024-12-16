import UIKit

// MARK: - Computed Properties
extension UICollectionViewCell {
    
    static var identifier: String {
        String(describing: Self.self)
    }
    
    func animate(delay: Double = 0) {
        alpha = 0
        UIView.animate(withDuration: 0.3, delay: delay, options: .curveEaseIn) { [weak self] in
            guard let self = self else { return }
            self.alpha = 1.0
        }
    }
}
