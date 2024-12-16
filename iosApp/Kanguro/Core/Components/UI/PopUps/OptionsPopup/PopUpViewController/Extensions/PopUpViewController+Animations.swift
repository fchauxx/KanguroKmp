import UIKit

extension PopUpViewController {
    
    func animateIn() {
        
        let duration = 0.2
        
        // Animate background color
        UIView.animate(withDuration: duration,
                       delay: 0.0,
                       options: [.curveEaseInOut, .allowUserInteraction],
                       animations: {
            self.view.backgroundColor = UIColor(white: 0, alpha: self.backgroundOpacity)
        }, completion: nil)
        
        // Animate container on screen
        if !isCustomConstraint {
            containerOffscreenConstraint.isActive = false
        }
        self.view.setNeedsUpdateConstraints()
        
        UIView.animate(withDuration: duration,
                       delay: 0.0,
                       usingSpringWithDamping: 0.84,
                       initialSpringVelocity: 0,
                       options: [.allowUserInteraction],
                       animations: {
            self.view.layoutIfNeeded()
        }, completion: { _ in
            self.containerView.isUserInteractionEnabled = true
            self.state = .idle
        })
    }
    
    func animateOut() {
        
        view.isUserInteractionEnabled = false
        state = .animatingOut
        
        let duration = 0.2
        
        // Animate background color
        UIView.animate(withDuration: duration,
                       delay: 0.0,
                       options: [.curveEaseInOut],
                       animations: {
            self.view.backgroundColor = UIColor.clear
        }, completion: nil)
        
        // Animate container off screen
        if !isCustomConstraint {
            containerOffscreenConstraint.isActive = true
        }
        view.setNeedsUpdateConstraints()
        
        UIView.animate(withDuration: duration,
                       delay: 0.0,
                       usingSpringWithDamping: 0.8,
                       initialSpringVelocity: 0,
                       options: [],
                       animations: {
            self.view.layoutIfNeeded()
        }, completion: { _ in
            self.dismiss(animated: false, completion: nil)
        })
    }
    
    func animate(fromPan panRecognizer: UIPanGestureRecognizer) {
        
        let animateOutThreshold = CGFloat(50)
        
        let velocity = panRecognizer.velocity(in: view).y
        
        // Animate out
        if velocity > animateOutThreshold {
            let physicsState = PhysicsState(velocity: velocity)
            state = .physicsOut(physicsState)
        }
        // Snap back
        else {
            animateSnapBackToCenter()
        }
    }
    
    func animateSnapBackToCenter() {
        
        let duration = 0.4
        
        swipeOffset = 0
        view.setNeedsUpdateConstraints()
        //view.setNeedsLayout()
        
        UIView.animate(withDuration: duration,
                       delay: 0.0,
                       usingSpringWithDamping: 0.7,
                       initialSpringVelocity: 0,
                       options: [],
                       animations: {
            self.view.layoutIfNeeded()
        }, completion: { _ in })
    }
}
