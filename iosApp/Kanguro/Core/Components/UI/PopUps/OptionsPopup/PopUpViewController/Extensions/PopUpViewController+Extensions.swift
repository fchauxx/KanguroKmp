import UIKit

extension PopUpViewController {
    
    @objc
    func tapAway() {
        
        if let protocolResponder = popupProtocolResponder {
            if protocolResponder.allowsTapToDismissPopupCard {
                animateOut()
            }
        } else {
            animateOut()
        }
    }
    
    @objc
    func didPan(recognizer: UIPanGestureRecognizer) {
        
        if state == .animatingIn {
            state = .idle
            self.view.layer.removeAllAnimations()
            self.containerView.layer.removeAllAnimations()
        }
        
        guard
            state == .idle || state == .panning
        else { return }
        
        let applyOffset = {
            self.swipeOffset = recognizer.translation(in: self.view).y
            self.view.setNeedsUpdateConstraints()
        }
        
        switch recognizer.state {
        case .possible:
            break
        case .began:
            state = .panning
            applyOffset()
        case .changed:
            state = .panning
            applyOffset()
        case .cancelled:
            break
        case .failed:
            break
        case .ended:
            animate(fromPan: recognizer)
        default:
            break
        }
    }
    
    @objc
    func tick() {
        
        // We need a previous time stamp to work with, bail if we don't have one
        guard let last = lastTimeStamp else {
            lastTimeStamp = displayLink.timestamp
            return
        }
        
        // Work out dt
        let timeStamp = displayLink.timestamp - last
        
        // Save the current time
        lastTimeStamp = displayLink.timestamp
        
        // If we're using physics to animate out, update the simulation
        guard case var PopUpState.physicsOut(physicsState) = state else {
            return
        }
        
        physicsState.velocity += CGFloat(timeStamp) * physicsState.acceleration
        
        swipeOffset += physicsState.velocity * CGFloat(timeStamp)
        
        view.setNeedsUpdateConstraints()
        state = .physicsOut(physicsState)
        
        // Remove if the content view is off screen
        if swipeOffset > view.bounds.size.height / 2 {
            dismiss(animated: false, completion: nil)
        }
    }
}

// MARK: - Gesture Recognizers
extension PopUpViewController: UIGestureRecognizerDelegate {
    
    public func gestureRecognizerShouldBegin(_ gestureRecognizer: UIGestureRecognizer) -> Bool {
        
        // Pan, check if swipe enabled
        if let responder = popupProtocolResponder, gestureRecognizer === panRecognizer {
            
            if self.disableSwipeToDismiss {
                return false
            }
            
            return responder.allowsSwipeToDismissPopupCard
        }
        
        // Tap, check if is outside view
        if gestureRecognizer === tapRecognizer {
            
            if self.disableTapToDismiss {
                return false
            }
            
            let location = tapRecognizer.location(in: view)
            return !containerView.frame.contains(location)
        }
        
        return true
    }
}
