import UIKit

class PaymentSettingsViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: PaymentSettingsViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet var navigationBackButton: NavigationBackButton!
    @IBOutlet private var preferencesTitleLabel: CustomLabel!
    @IBOutlet var paymentMethodActionCard: ActionCard!
    @IBOutlet private var reimbursementActionCard: ActionCard!
    @IBOutlet var paymentTitleLabel: CustomLabel!
    @IBOutlet var stackView: UIStackView!
    
    // MARK: Actions
    var goBackAction: SimpleClosure = {}
    var goToBillingPreferencesAction: PetPolicyClosure = { _ in }
    var goToPaymentMethodAction: SimpleClosure = {}
    var goToReimbursementAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension PaymentSettingsViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
}

// MARK: - View State
extension PaymentSettingsViewController {
    
    private func changed(state: DefaultViewState) {
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
        default:
            break
        }
    }
}

// MARK: - Setup
extension PaymentSettingsViewController {
    
    private func setupLayout() {
        setupLabels()
        setupCoverageCards()
        setupNavigationBackButton()
        setupActionCards()
    }
    
    private func setupNavigationBackButton() {
        navigationBackButton.update(title: "paymentSettings.title.label".localized)
        navigationBackButton.update(action: goBackAction)
    }
    
    private func setupLabels() {
        let titleStyle = TextStyle(color: .secondaryDarkest, weight: .bold, size: .h24, font: .raleway)
        paymentTitleLabel.set(text: "paymentSettings.payment.label".localized,
                              style: titleStyle)
        preferencesTitleLabel.set(text: "paymentSettings.preferences.label".localized,
                                  style: titleStyle)
    }
    
    private func setupActionCards() {
        
        let paymentMethodData = ActionCardData(traillingImage: UIImage(named: "ic-right-arrow"),
                                               leadingTitle: "paymentSettings.paymentMethod.label".localized,
                                               didTapAction: goToPaymentMethodAction)
        let reimbursementData = ActionCardData(traillingImage: UIImage(named: "ic-right-arrow"),
                                               leadingTitle: "paymentSettings.reimbursement.label".localized,
                                               didTapAction: goToReimbursementAction)
        paymentMethodActionCard.setup(actionCardData: paymentMethodData,
                                      backgroundColor: .white)
        reimbursementActionCard.setup(actionCardData: reimbursementData,
                                      backgroundColor: .white)
    }
    
    private func setupCoverageCards() {
        for policy in viewModel.policies {
            let card = CoverageDetailsCard()
            card.setup(policy: policy, type: .paymentSettings)
            stackView.addArrangedSubview(card)
        }
        stackView.layoutIfNeeded()
    }
}

// MARK: - IB Actions
extension PaymentSettingsViewController {
    
    @IBAction private func backButton(_ sender: UIButton) {
        goBackAction()
    }
}
