import UIKit

class BillingPreferencesViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: BillingPreferencesViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet var billingPreferencesLabel: CustomLabel!
    @IBOutlet var creditCardActionCard: ActionCard!
    @IBOutlet private var billingHistoryLabel: CustomLabel!
    @IBOutlet var billingHistoryStackView: UIStackView!
    
    // MARK: - Actions
    var goBackAction: SimpleClosure = {}
    var goToCreditCardPreferences: SimpleClosure = {}
}

// MARK: - Life Cycle
extension BillingPreferencesViewController {
    
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
extension BillingPreferencesViewController {
    
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
extension BillingPreferencesViewController {
    
    private func setupLayout() {
        setupLabels()
        setupBillingHistoryStackView()
        setupCreditCardActionCard()
    }
    
    private func setupLabels() {
        let style = TextStyle(color: .black, weight: .bold, size: .h24, font: .raleway)
        billingPreferencesLabel.set(text: "billingPreferences.billingPreferences.label".localized, style: style)
        billingHistoryLabel.set(text: "billingPreferences.billingHistory.label".localized, style: style)
    }
    
    private func setupBillingHistoryStackView() {
        let billingHistoryView = BillingHistoryView()
        billingHistoryView.update(policy: viewModel.policy)
        billingHistoryStackView.addArrangedSubview(billingHistoryView)
    }
    
    private func setupCreditCardActionCard() {
        guard let cardImage = UIImage(named: "ic-mastercard") else { return }
        creditCardActionCard.setup(actionCardData: ActionCardData(leadingImage: cardImage, leadingTitle: "•••• 1234", didTapAction: { [weak self] in
            guard let self = self else { return }
            self.goToCreditCardPreferences()
        } ), backgroundColor: .neutralBackground)
    }
}

extension BillingPreferencesViewController {
    
    @IBAction private func closeButtonTouchUpInside(_ sender: UIButton) {
        goBackAction()
    }
}
