import UIKit

public class PopUpViewController: UIViewController {
    
    // MARK: - Public Properties
    public var disableSwipeToDismiss = false
    public var disableTapToDismiss = false
    public var cornerRadius = CGFloat(7)
    
    // MARK: - Stored Properties
    var isCustomConstraint: Bool = false
    
    // MARK: - Public Functions
    public func close() {
        animateOut()
    }
    
    public func show(onViewController viewController: UIViewController) {
        self.modalPresentationStyle = .overFullScreen
        viewController.present(self, animated: false, completion: nil)
    }
    
    // MARK: - Initializers
    
    public init(contentViewController viewController: UIViewController) {
        contentViewController = viewController
        contentView = viewController.view
        super.init(nibName: nil, bundle: nil)
    }
    
    public init(contentView view: UIView) {
        contentViewController = nil
        contentView = view
        super.init(nibName: nil, bundle: nil)
    }
    
    public required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    // MARK: - Stored Properties
    
    let backgroundOpacity = CGFloat(0.35)
    
    var displayLink: CADisplayLink!
    var lastTimeStamp: CFTimeInterval?
    
    let contentViewController: UIViewController?
    let contentView: UIView
    
    let containerView = UIView(frame: .zero)
    
    var hasAnimatedIn = false
    
    var containerCenterYConstraint: NSLayoutConstraint!
    var containerOffscreenConstraint: NSLayoutConstraint!
    
    var tapRecognizer: UITapGestureRecognizer!
    var panRecognizer: UIPanGestureRecognizer!
    
    var state = PopUpState.animatingIn
    var swipeOffset = CGFloat(0)
    
    // MARK: - Computed Properties
    
    var popupProtocolResponder: PopUpContentProtocol? {
        if let contentViewController = contentViewController {
            if let protocolResponder = contentViewController as? PopUpContentProtocol {
                return protocolResponder
            }
        } else if let protocolResponder = contentView as? PopUpContentProtocol {
            return protocolResponder
        }
        
        return nil
    }
}
