import UIKit

class DonationPopUpViewController: UIViewController {
    
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
    var didTapDonationAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension DonationPopUpViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
    }
}

// MARK: - Setup
extension DonationPopUpViewController {
    
    private func setupLayout() {
        setupLabels()
        setupButtons()
    }
    
    private func setupLabels() {
        titleLabel.set(text: "donation.cardTitle.label".localized,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p21, font: .raleway, alignment: .center))
        subtitleLabel.set(text: "donation.cardSubtitle.label".localized,
                          style: TextStyle(color: .neutralMedium, size: .p16, alignment: .center))
    }
    
    private func setupButtons() {
        laterButton.set(title: "donation.later.button".localized,
                        style: .secondary)
        laterButton.onTap { [weak self] in
            guard let self = self else { return }
            self.goBackAction()
        }
        doItButton.set(title: "donation.doIt.button".localized,
                       style: .primary)
        doItButton.setImage(nil, for: .normal)
        doItButton.onTap { [weak self] in
            guard let self else { return }
            self.didTapDonationAction()
        }
    }
}

// MARK: - PopUpContentProtocol
extension DonationPopUpViewController: PopUpContentProtocol {
    
    static func create() -> UIViewController {
        guard let viewController = DonationPopUpViewController.instantiate() else { return UIViewController() }
        return viewController
    }
}

// MARK: - PopUpContentProtocol
extension DonationPopUpViewController {
    
    @IBAction private func closeTouchUpInside(_ sender: UIButton) {
        goBackAction()
    }
}
