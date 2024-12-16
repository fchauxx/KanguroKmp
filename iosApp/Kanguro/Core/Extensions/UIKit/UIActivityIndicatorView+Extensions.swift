import UIKit

extension UIActivityIndicatorView {
    
    func setScaleSize(_ scale: CGFloat) {
        transform = CGAffineTransform(scaleX: scale,
                                      y: scale)
    }
}
