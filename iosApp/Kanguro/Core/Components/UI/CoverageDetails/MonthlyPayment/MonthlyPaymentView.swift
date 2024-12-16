import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class MonthlyPaymentView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var invoiceIntervalLabel: CustomLabel!
    @IBOutlet private var invoiceIntervalCostLabel: CustomLabel!
    @IBOutlet private var petTypeLabel: CustomLabel!
    @IBOutlet private var petTypeCostLabel: CustomLabel!
    @IBOutlet private var preventiveAndWellnessLabel: CustomLabel!
    @IBOutlet private var preventiveAndWellnessCostLabel: CustomLabel!
    @IBOutlet private var minorCostLabelsStackView: UIStackView!
    
    // MARK: - Stored Properties
    var policy: PetPolicy?
    
    // MARK: - Computed Properties
    var invoiceTitle: String? {
        guard let invoiceInterval = policy?.payment?.invoiceInterval else { return nil }
        return "coverageDetails.\(invoiceInterval).label".localized
    }
    var invoiceCostText: String? {
        guard let recurringPayment = policy?.payment?.recurringPayment,
              let firstPayment = policy?.payment?.firstPayment else { return nil }
        let paymentValue = (policy?.payment?.invoiceInterval == .YEARLY) ? firstPayment : recurringPayment
        return "\(paymentValue.getCurrencyFormatted(fractionDigits: 2))"
    }
    var petTypeText: String? {
        switch policy?.pet.type {
        case .Cat:
            return "coverageDetails.monthlyPerroBueno.label".localized
        case .Dog:
            return "coverageDetails.monthlyGatoSano.label".localized
        default:
            return nil
        }
    }
    
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
extension MonthlyPaymentView {
    
    private func setupLayout() {
        setupLabels()
    }
    
    private func setupLabels() {
        guard let invoiceTitle = invoiceTitle,
              let invoiceCostText = invoiceCostText,
              let petTypeText = petTypeText else { return }
        let itemsStyle = TextStyle(color: .secondaryMedium, size: .p12)
        let subItemsStyle = TextStyle(color: .secondaryMedium, size: .p10)
        
        invoiceIntervalLabel.set(text: invoiceTitle,
                                 style: TextStyle(color: .secondaryDarkest, size: .h24, font: .raleway))
        invoiceIntervalCostLabel.set(text: invoiceCostText,
                                     style: TextStyle(color: .tertiaryDark, weight: .black, size: .p21, alignment: .right))
        petTypeLabel.setHighlightedText(text: petTypeText,
                                        style: itemsStyle,
                                        highlightedText: "coverageDetails.monthlyAccident.label".localized,
                                        highlightedStyle: subItemsStyle)
        preventiveAndWellnessLabel.setHighlightedText(text: "coverageDetails.monthlyPreventiveAndWellness.label".localized,
                                                      style: itemsStyle,
                                                      highlightedText: "coverageDetails.monthlyExtraService.label".localized,
                                                      highlightedStyle: subItemsStyle)
        //TODO: - Update when we have the official costs value
        setupMinorCostLabels(costsText: nil)
    }
    
    private func setupMinorCostLabels(costsText: String?) {
        let itemsCostStyle = TextStyle(color: .secondaryDarkest, weight: .bold, size: .p12, alignment: .right)
        if let costsText = costsText {
            petTypeCostLabel.set(text: costsText, style: itemsCostStyle)
            preventiveAndWellnessCostLabel.set(text: costsText, style: itemsCostStyle)
        } else {
            minorCostLabelsStackView.isHidden = true
        }
    }
}

// MARK: - Public Methods
extension MonthlyPaymentView {
    
    func setup(policy: PetPolicy) {
        self.policy = policy
        setupLayout()
    }
}
