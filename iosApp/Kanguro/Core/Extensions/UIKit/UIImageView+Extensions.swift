import UIKit
import Kingfisher

extension UIImageView {
    
    func setAnimatedImage(_ image: UIImage?, animated: Bool = true, duration: Double) {
        UIView.transition(with: self, duration: duration, options: .transitionCrossDissolve, animations: { [weak self] in
            guard let self = self else { return }
            self.image = image
        }, completion: nil)
    }
}
