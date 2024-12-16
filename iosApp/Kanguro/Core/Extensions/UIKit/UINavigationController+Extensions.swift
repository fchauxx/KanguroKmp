import UIKit

// MARK: - StartPoint
enum StartPoint {
    case bottomOfStack
    case topOfStack
}

// MARK: - PopToViewController
extension UINavigationController {
    
    @discardableResult
    func popToViewController<T: UIViewController>(viewControllerType: T.Type, startingFrom startPoint: StartPoint = .topOfStack) -> UIViewController? {
        var viewControllers = self.viewControllers
        if case .topOfStack = startPoint {
            viewControllers.reverse()
        }
        let goalViewController = viewControllers.first(where: { viewcontroller -> Bool in
                return viewcontroller is T
        })
        if let goalViewController = goalViewController {
            self.popToViewController(goalViewController, animated: true)
        }
        return goalViewController
    }
}

// MARK: - PreviousViewController
extension UINavigationController {
    
    var previousViewController: UIViewController? {
       viewControllers.count > 0 ? viewControllers[viewControllers.count - 1] : nil
    }
}
