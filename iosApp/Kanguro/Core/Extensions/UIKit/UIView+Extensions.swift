import UIKit
import SwiftUI

// MARK: - Inspectable
extension UIView {
    
    var topLeft: CACornerMask { return .layerMinXMinYCorner }
    var topRight: CACornerMask { return .layerMaxXMinYCorner }
    var bottomLeft: CACornerMask { return .layerMinXMaxYCorner }
    var bottomRight: CACornerMask { return .layerMaxXMaxYCorner }
    var allCorners: CACornerMask { return [topLeft, topRight, bottomLeft, bottomRight] }
    
    @IBInspectable var cornerRadius: CGFloat {
        get {
            return layer.cornerRadius
        }
        set {
            layer.cornerRadius = newValue
            layer.masksToBounds = newValue > 0
        }
    }
    
    @IBInspectable var borderWidth: CGFloat {
        get {
            return layer.borderWidth
        }
        set {
            layer.borderWidth = newValue
            layer.masksToBounds = newValue > 0
        }
    }
    
    @IBInspectable var borderColor: UIColor {
        get {
            return UIColor.init(cgColor: layer.borderColor!)
        }
        set {
            layer.borderColor = newValue.cgColor
        }
    }
}

// MARK: - Shadow
extension UIView {
    
    func setShadow(shadowColor: UIColor = .gray,
                   shadowOpacity: Float = 0.2,
                   shadowRadius: CGFloat = 4,
                   shadowOffset: CGSize = CGSize(width: 0, height: 0)) {
        layer.shadowColor = shadowColor.cgColor
        layer.shadowOpacity = shadowOpacity
        layer.shadowRadius = shadowRadius
        layer.shadowOffset = shadowOffset
        layer.masksToBounds = false
    }
}

// MARK: - Round Corners
extension UIView {
    
    func roundCorners(corners: UIRectCorner, radius: CGFloat) {
        let path = UIBezierPath(
            roundedRect: bounds,
            byRoundingCorners: corners,
            cornerRadii: CGSize(width: radius, height: radius)
        )
        let mask = CAShapeLayer()
        mask.path = path.cgPath
        layer.mask = mask
    }
}

// MARK: - Circle
extension UIView {
    
    func setAsCircle() {
        layer.cornerRadius = frame.height/2
    }
}

// MARK: - Constraints
extension UIView {
    
    func layoutAttachAll(to view: UIView) {
        leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
        trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
        topAnchor.constraint(equalTo: view.topAnchor).isActive = true
        bottomAnchor.constraint(equalTo: view.bottomAnchor).isActive = true
    }
}

// MARK: - Computed Properties
extension UIView {
    
    var pointRelatedToScreen: CGPoint? {
        return superview?.convert(self.frame.origin, to: nil)
    }
}

extension UIView {
    
    var parentViewController: UIViewController? {
        var parentResponder: UIResponder? = self.next
        while parentResponder != nil {
            if let viewController = parentResponder as? UIViewController {
                return viewController
            }
            parentResponder = parentResponder?.next
        }
        return nil
    }
}

// MARK: - Gradient
extension UIView {
    
    func applyGradient(isVertical: Bool, colorArray: [UIColor]) {
        let gradientLayer = CAGradientLayer()
        gradientLayer.frame = bounds
        
        if isVertical {
            //top to bottom
            gradientLayer.locations = [0.0, 1.0]
        } else {
            //left to right
            gradientLayer.startPoint = CGPoint(x: 0.0, y: 0.5)
            gradientLayer.endPoint = CGPoint(x: 1.0, y: 0.5)
        }
        
        gradientLayer.colors = colorArray.map({ $0.cgColor })
        backgroundColor = colorArray.last
        
        layer.insertSublayer(gradientLayer, at: 0)
    }
}

// MARK: - SwiftUI
extension UIView {
    
    func setupSwiftUIIntoUIKitView(
        swiftUIView: some View,
        basedViewController: UIViewController,
        attachConstraints: Bool = false
    ) {
        let childView = UIHostingController(rootView: swiftUIView)
        basedViewController.addChild(childView)
        childView.view.backgroundColor = .clear
        childView.view.frame = bounds
        addSubview(childView.view)

        if attachConstraints {
            childView.view.translatesAutoresizingMaskIntoConstraints = false
            childView.view.layoutAttachAll(to: self)
        }

        backgroundColor = .clear
        childView.didMove(toParent: basedViewController)
    }
}
