import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class BillingHistoryView: BaseView, NibOwnerLoadable {

    // MARK: - IB Outlets
    @IBOutlet private var billingDateLabel: CustomLabel!
    @IBOutlet private var mainStackView: UIStackView!
    @IBOutlet private var openableStackView: UIStackView!
    @IBOutlet private var billingTitleLabel: CustomLabel!
    @IBOutlet private var petIconImageView: UIImageView!
    @IBOutlet private var totalCostLabel: CustomLabel!
    @IBOutlet private var arrowImageView: UIImageView!
    @IBOutlet private var creditCardView: CreditCardView!
    @IBOutlet private var perroBuenoLabel: CustomLabel!
    @IBOutlet private var preventiveAndWellnessLabel: CustomLabel!
    @IBOutlet private var perroBuenoCostLabel: CustomLabel!
    @IBOutlet private var preventiveAndWellnessCostLabel: CustomLabel!

    // MARK: - Stored Properties
    var policy: PetPolicy?
    private var isItemsOpen: Bool = false

    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
    }
    
    override init(frame: CGRect) {
         super.init(frame: frame)
         self.loadNibContent()
    }
}

// MARK: - Setup
extension BillingHistoryView {

    private func setupLayout() {
        setupLabels()
        setupImages()
        setupItemsStackView()
        setupCreditCardView()
    }
    
    private func setupLabels() {
        guard let recurringPayment = policy?.payment?.recurringPayment else { return }
        let itemsStyle = TextStyle(color: .secondaryMedium, size: .p12)
        let itemsCostStyle = TextStyle(color: .secondaryDarkest, weight: .bold, size: .p12)
        billingDateLabel.set(text: "\(Date().monthName) \(Date().ddyy)", style: TextStyle(color: .secondaryDarkest, weight: .bold))
        //TODO: change billingTitleLabel name
        billingTitleLabel.set(text: "MOCKEDNAME", style: TextStyle(color: .secondaryDarkest, weight: .black))
        totalCostLabel.set(text: "$\(recurringPayment.formatted)", style: TextStyle(color: .tertiaryExtraDark, weight: .black, size: .p16))
        perroBuenoLabel.set(text: "coverageDetails.perroBueno.label".localized, style: itemsStyle)
        preventiveAndWellnessLabel.set(text: "coverageDetails.preventiveAndWellness.label".localized, style: itemsStyle)
        perroBuenoCostLabel.set(text: "$\(recurringPayment.formatted)", style: itemsCostStyle)
        preventiveAndWellnessCostLabel.set(text: "$\(recurringPayment.formatted)", style: itemsCostStyle)
    }
    
    private func setupImages() {
        petIconImageView.image = UIImage(named: "ic-blotchy-dog")
        arrowImageView.image = (isItemsOpen ? UIImage(named: "ic-up-arrow") : UIImage(named: "ic-down-arrow"))
    }
    
    private func setupItemsStackView() {
        openableStackView.isHidden = !isItemsOpen
    }
    
    private func setupCreditCardView() {
        // TODO: Update when we have the info
        creditCardView.setup(creditCard: 1234)
    }
}

// MARK: - Private Methods
extension BillingHistoryView {
    
    private func changeItemsStackStatus() {
        isItemsOpen.toggle()
        UIView.animate(withDuration: 0.3) { [weak self] in
            guard let self = self else { return }
            self.setupImages()
            self.setupItemsStackView()
            self.openableStackView.superview?.layoutIfNeeded()
        }
    }
}

// MARK: - Public Methods
extension BillingHistoryView {

    func update(policy: PetPolicy) {
        self.policy = policy
        setupLayout()
    }
}

extension BillingHistoryView {
    
    @IBAction private func openItemsStack(_ sender: UIButton) {
        changeItemsStackStatus()
    }
}
