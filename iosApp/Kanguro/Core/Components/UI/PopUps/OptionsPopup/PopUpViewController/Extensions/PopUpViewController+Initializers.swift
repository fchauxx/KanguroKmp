import UIKit

extension PopUpViewController {
    
    override public func viewDidLoad() {
        super.viewDidLoad()
        
        view.backgroundColor = UIColor.clear
        
        // Container view
        containerView.layer.cornerRadius = cornerRadius
        containerView.layer.masksToBounds = true
        view.addSubview(containerView)
        containerView.isUserInteractionEnabled = false
        
        // Content
        if let contentViewController = contentViewController {
            addChild(contentViewController)
        }
        containerView.addSubview(contentView)
        contentView.translatesAutoresizingMaskIntoConstraints = false
        
        // Popup Protocol Responder
        popupProtocolResponder?.popupViewController = self
        
        // Apply Constraints
        if !isCustomConstraint {
            applyContainerViewConstraints()
            containerOffscreenConstraint.isActive = true
        }
        applyContentViewConstraints()
        
        
        // Tap Away Recognizer
        tapRecognizer = UITapGestureRecognizer(target: self, action: #selector(tapAway))
        tapRecognizer.delegate = self
        view.addGestureRecognizer(tapRecognizer)
        
        // Pan Recognizer
        panRecognizer = UIPanGestureRecognizer(target: self, action: #selector(didPan))
        panRecognizer.delegate = self
        view.addGestureRecognizer(panRecognizer)
        
        // Display Link
        displayLink = CADisplayLink(target: self, selector: #selector(tick))
        displayLink.add(to: .current, forMode: .common)
    }
    
    override public func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        if !hasAnimatedIn {
            animateIn()
            hasAnimatedIn = true
        }
    }
    
    override public func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        displayLink.invalidate()
    }
    
    override public func updateViewConstraints() {
        super.updateViewConstraints()

        // Elastic Pull upwards
        if swipeOffset < 0 {

            let offset = -swipeOffset
            let offsetPct = (offset / view.bounds.size.width / 2)
            let elasticity = CGFloat(3)
            let percent = offsetPct / (1.0 + (offsetPct * elasticity))

            containerCenterYConstraint.constant = -(percent * view.bounds.size.width / 2)
        }
        // Regular tracking downwards
        else {
            if !isCustomConstraint {
                containerCenterYConstraint.constant = swipeOffset
            }
        }
    }
    
    override public func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        
        // Update background color and card opacity if panning or physics out
        switch state {
        case .animatingIn: break
        case .idle: break
        case .animatingOut: break
        case .panning: break
        case .physicsOut:
            
            let distance = view.bounds.size.height / 2 + contentView.frame.size.height / 2
            
            var outPct = 1.0 - (swipeOffset / distance)
            outPct = min(outPct, 1.0)
            let opacity = backgroundOpacity * outPct
            view.backgroundColor = UIColor(white: 0, alpha: opacity)
            
            containerView.alpha = outPct
        }
    }
}
