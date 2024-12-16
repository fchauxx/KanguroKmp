import UIKit

extension PopUpViewController {
    
    public func setContentViewConstraints(positionX: CGFloat, positionY: CGFloat, leftAligned: Bool = true) {
        
        isCustomConstraint = true
        
        guard let viewController = (contentViewController as? PopUpMenuViewController) else { return }
        let popUpWidth = viewController.stackWidth
        
        containerView.translatesAutoresizingMaskIntoConstraints = false
        
        let leftPosition = (positionX - popUpWidth)
        let rightPosition = (positionX + popUpWidth)
        
        let finalXPosition = NSLayoutConstraint(item: containerView,
                                                attribute: .left,
                                                relatedBy: .equal,
                                                toItem: view,
                                                attribute: .left,
                                                multiplier: 1.0,
                                                constant: leftAligned == false ? leftPosition : rightPosition)
        
        let finalYPosition = NSLayoutConstraint(item: containerView,
                                                attribute: .top,
                                                relatedBy: .equal,
                                                toItem: view,
                                                attribute: .top,
                                                multiplier: 1.0,
                                                constant: positionY)
        
        view.addConstraints([finalXPosition, finalYPosition])
    }
    
    func applyContentViewConstraints() {
        
        contentView.translatesAutoresizingMaskIntoConstraints = false
        
        [NSLayoutConstraint.Attribute.left, .right, .top, .bottom].forEach {
            
            let constraint = NSLayoutConstraint(item: contentView,
                                                attribute: $0,
                                                relatedBy: .equal,
                                                toItem: containerView,
                                                attribute: $0,
                                                multiplier: 1.0,
                                                constant: 0)
            containerView.addConstraint(constraint)
        }
        contentView.setShadow()
    }
    
    func applyContainerViewConstraints() {
        
        containerView.translatesAutoresizingMaskIntoConstraints = false
        
        let sideMargin = CGFloat(0)
        let verticalMargins = CGFloat(0)
        
        let left = NSLayoutConstraint(item: containerView,
                                      attribute: .left,
                                      relatedBy: .equal,
                                      toItem: view,
                                      attribute: .left,
                                      multiplier: 1.0,
                                      constant: sideMargin)
        
        let right = NSLayoutConstraint(item: containerView,
                                       attribute: .right,
                                       relatedBy: .equal,
                                       toItem: view,
                                       attribute: .right,
                                       multiplier: 1.0,
                                       constant: -sideMargin)
        
        containerCenterYConstraint = NSLayoutConstraint(item: containerView,
                                                        attribute: .centerY,
                                                        relatedBy: .equal,
                                                        toItem: view,
                                                        attribute: .centerY,
                                                        multiplier: 1.0,
                                                        constant: 0)
        containerCenterYConstraint.priority = UILayoutPriority.defaultLow
        
        let limitHeight = NSLayoutConstraint(item: containerView,
                                             attribute: .height,
                                             relatedBy: .lessThanOrEqual,
                                             toItem: view,
                                             attribute: .height,
                                             multiplier: 1.0,
                                             constant: -verticalMargins * 2)
        limitHeight.priority = UILayoutPriority.defaultHigh
        
        containerOffscreenConstraint = NSLayoutConstraint(item: containerView,
                                                          attribute: .top,
                                                          relatedBy: .equal,
                                                          toItem: view,
                                                          attribute: .bottom,
                                                          multiplier: 1.0,
                                                          constant: 0)
        containerOffscreenConstraint.priority = UILayoutPriority.required
        
        view.addConstraints([left, right, containerCenterYConstraint, limitHeight, containerOffscreenConstraint])
    }
    
    func pinContainerOffscreen(_ pinOffscreen: Bool) {
        containerOffscreenConstraint.isActive = pinOffscreen
    }
}
