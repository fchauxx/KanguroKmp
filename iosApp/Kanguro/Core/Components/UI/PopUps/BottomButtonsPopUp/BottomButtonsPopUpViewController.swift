import UIKit

class BottomButtonsPopUpViewController: UIViewController {
    
    // MARK: - IBOutlets
    @IBOutlet private weak var actionCardsList: ActionCardsList!
    @IBOutlet private weak var topDistance: NSLayoutConstraint!
    
    // MARK: - Stored Properties
    var popupViewController: PopUpViewController?
    var allowsTapToDismissPopupCard: Bool = true
    var allowsSwipeToDismissPopupCard: Bool = true
    var actionCardListHeight: CGFloat = 156
    var bottomSafeArea: CGFloat = 40
    
    // MARK: - Computed Properties
    private var screenHeight: CGFloat {
        UIScreen.main.bounds.size.height
    }
}

// MARK: - Setup
extension BottomButtonsPopUpViewController {
    
    func setup(data: [ActionCardData]) {
        actionCardsList.addActionCards(actionCardDataList: data)
        topDistance.constant = screenHeight-actionCardListHeight-bottomSafeArea
    }
}

// MARK: - PopUpContentProtocol
extension BottomButtonsPopUpViewController: PopUpContentProtocol {
    
    static func create() -> UIViewController {
        guard let viewController = BottomButtonsPopUpViewController.instantiate() else { return UIViewController() }
        return viewController
    }
}

// MARK: - PopUpContentProtocol
extension BottomButtonsPopUpViewController {
    
    @IBAction private func closeTouchUpInside(_ sender: UIButton) {
        self.dismiss(animated: true)
    }
}
