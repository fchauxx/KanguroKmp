import UIKit

class PopUpMenuViewController: BaseViewController {
    
    // MARK: - IB Outlets
    @IBOutlet var stackView: UIStackView!
    
    // MARK: - Stored Properties
    var popupViewController: PopUpViewController?
    var allowsTapToDismissPopupCard: Bool = true
    var allowsSwipeToDismissPopupCard: Bool = true
    
    var stackWidth: CGFloat {
        stackView.frame.width
    }
    
    // MARK: Actions
    var closeAction: SimpleClosure?
}

// MARK: - Public Methods
extension PopUpMenuViewController {
    
    func addMenu(popUpData: PopUpData) {
        let item = PopUpItem()
        item.update(data: popUpData)
        stackView.addArrangedSubview(item)
        stackView.layoutIfNeeded()
    }
}

// MARK: - PopUpContentProtocol
extension PopUpMenuViewController: PopUpContentProtocol {
    
    static func create() -> UIViewController {
        guard let viewController = PopUpMenuViewController.instantiate() else { return UIViewController() }
        return viewController
    }
}
