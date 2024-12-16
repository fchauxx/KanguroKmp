import UIKit

extension UILabel {
    
    func setAnimatedLabel(_ textColor: UIColor?, animated: Bool = true, duration: Double) {
        UIView.transition(with: self, duration: duration, options: .transitionCrossDissolve, animations: { [weak self] in
            guard let self = self else { return }
            self.textColor = textColor
        }, completion: nil)
    }
}
