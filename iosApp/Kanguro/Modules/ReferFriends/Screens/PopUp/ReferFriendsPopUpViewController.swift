import UIKit

class ReferFriendsPopUpViewController: UIViewController {
    
    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var subtitleLabel: CustomLabel!
    @IBOutlet private var laterButton: CustomButton!
    @IBOutlet private var doItButton: CustomButton!
    
    // MARK: - Stored Properties
    var popupViewController: PopUpViewController?
    var allowsTapToDismissPopupCard: Bool = true
    var allowsSwipeToDismissPopupCard: Bool = true
    
    // MARK: - Actions
    var goBackAction: SimpleClosure = {}
    var didTapReferFriendAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension ReferFriendsPopUpViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
    }
}

// MARK: - Setup
extension ReferFriendsPopUpViewController {
    
    private func setupLayout() {
        setupActions()
        setupLabels()
        setupButtons()
    }
    
    private func setupLabels() {
        titleLabel.set(text: "referFriends.giftCardTitle.label".localized,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p21, font: .raleway))
        subtitleLabel.setHighlightedText(text: "referFriends.giftCardSubtitle.label".localized,
                                         style: TextStyle(color: .neutralMedium, size: .p16),
                                         highlightedText: "referFriends.boldedGiftCardSubtitle.label".localized,
                                         highlightedStyle: TextStyle(color: .primaryDarkest, weight: .bold, size: .p16))
    }
    
    private func setupButtons() {
        laterButton.set(title: "referFriends.later.button".localized,
                        style: .secondary)
        laterButton.onTap { [weak self] in
            guard let self = self else { return }
            self.goBackAction()
        }
        doItButton.set(title: "referFriends.doIt.button".localized, style: .primary)
        doItButton.setImage(nil, for: .normal)
        doItButton.onTap { [weak self] in
            guard let self = self else { return }
            self.goBackAction()
            self.didTapReferFriendAction()
        }
    }
    
    private func setupActions() {
        goBackAction = { [weak self] in
            guard let self = self else { return }
            self.dismiss(animated: true)
        }
    }
}

// MARK: - PopUpContentProtocol
extension ReferFriendsPopUpViewController: PopUpContentProtocol {
    
    static func create() -> UIViewController {
        guard let viewController = ReferFriendsPopUpViewController.instantiate() else { return UIViewController() }
        return viewController
    }
}

// MARK: - PopUpContentProtocol
extension ReferFriendsPopUpViewController {
    
    @IBAction private func closeTouchUpInside(_ sender: UIButton) {
        goBackAction()
    }
}
