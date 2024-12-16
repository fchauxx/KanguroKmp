import UIKit
import KanguroSharedDomain

class MedicalReminderPopUpViewController: UIViewController {
    
    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var subtitleLabel: CustomLabel!
    @IBOutlet private var letsDoItButton: CustomButton!
    
    // MARK: - Stored Properties
    var medicalReminders: [KanguroSharedDomain.Reminder]?
    var popupViewController: PopUpViewController?
    var allowsTapToDismissPopupCard: Bool = true
    var allowsSwipeToDismissPopupCard: Bool = true
    private let deviceLogicalHeight: CGFloat = UIScreen.main.bounds.height
    
    // MARK: - Actions
    var goBackAction: SimpleClosure = {}
    var didTapMedicalHistoryReminderAction: RemindersClosure = { _ in }
}

// MARK: - Life Cycle
extension MedicalReminderPopUpViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
    }
}

// MARK: - Setup
extension MedicalReminderPopUpViewController {
    
    private func setupLayout() {
        setupActions()
        setupLabels()
        setupButtons()
    }
    
    private func setupLabels() {
        titleLabel.set(text: "medicalHistory.cardTitle.label".localized,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p21, font: .raleway, alignment: .center))
        subtitleLabel.set(text: "medicalHistory.cardSubtitle.label".localized,
                          style: TextStyle(color: .neutralMedium, size: deviceLogicalHeight <= 667 ? .p14 : .p16, alignment: .center))
    }
    
    private func setupButtons() {
        letsDoItButton.set(title: "medicalHistory.letsDoIt.button".localized,
                        style: .primary)
        letsDoItButton.setImage(nil, for: .normal)
        letsDoItButton.onTap { [weak self] in
            guard let self else { return }
            self.goBackAction()
            self.didTapMedicalHistoryReminderAction(self.medicalReminders ?? [])
        }
    }
    
    private func setupActions() {
        goBackAction = { [weak self] in
            guard let self else { return }
            self.dismiss(animated: true)
        }
    }
}

// MARK: - PopUpContentProtocol
extension MedicalReminderPopUpViewController: PopUpContentProtocol {
    
    static func create() -> UIViewController {
        guard let viewController = MedicalReminderPopUpViewController.instantiate() else { return UIViewController() }
        return viewController
    }
}

// MARK: - PopUpContentProtocol
extension MedicalReminderPopUpViewController {
    
    @IBAction private func closeTouchUpInside(_ sender: UIButton) {
        goBackAction()
    }
}
