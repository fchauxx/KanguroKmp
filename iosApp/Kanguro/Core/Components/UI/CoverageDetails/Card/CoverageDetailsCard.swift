import UIKit
import SwiftUI
import KanguroSharedDomain
import KanguroPetDomain

enum CoverageDetailsCardType {
    
    case perroBueno
    case gatoSano
    case preventiveAndWellness
    case coveragePayment
    case paymentSettings
    
    // MARK: - Computed Properties
    var isPet: Bool {
        return self == .perroBueno || self == .gatoSano
    }
    var isPayment: Bool {
        return self == .coveragePayment || self == .paymentSettings
    }
}

class CoverageDetailsCard: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var petImageHeader: PetImageHeaderView!
    @IBOutlet private var cardHeaderView: CardHeaderView!
    @IBOutlet private var warningView: WarningView!
    @IBOutlet private var rememberingLabelsView: RememberingLabelsView!
    @IBOutlet private var progressBar: CoverageDetailsProgressBar!
    @IBOutlet private var monthlyPayment: MonthlyPaymentView!
    @IBOutlet private var nextBilling: CustomLabel!
    @IBOutlet private var accordionCard: AccordionCardView!
    @IBOutlet private var actionCardsList: ActionCardsList!
    @IBOutlet private var accordionDocsListView: AccordionDocsListView!
    @IBOutlet private var actionCardStackView: UIStackView!
    
    // MARK: - Stored Properties
    var policy: PetPolicy?
    var type: CoverageDetailsCardType?
    var documents: [KanguroSharedDomain.PolicyDocumentData]?
    
    // MARK: - Actions
    var didTapPlanCoveredAction: SimpleClosure = {}
    var didTapPreventiveCoveredAction: SimpleClosure = {}
    
    var didTapPolicyDocsAction: SimpleClosure = {}
    var didTapBillingPreferencesAction: SimpleClosure = {}
    
    var didTapDocumentAction: AnyClosure = { _ in }
    
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
extension CoverageDetailsCard {
    
    private func setupLayout() {
        setupActionCardsList()
        setupViews()
        setupHeaderView()
        setupWarningView()
        setupLabels()
        setupProgressBar()
        setupMonthlyPayment()
    }
    
    private func setupViews() {
        self.cornerRadius = 8
        self.setShadow()
    }
    
    private func setupHeaderView() {
        guard let type = type,
              let status = policy?.status,
              let policy = policy else { return }
        
        if type.isPet {
            switch status {
            case .ACTIVE:
                cardHeaderView.update(expirationDate: "\("coverageDetails.renewOn.label".localized) \(policy.endDateFormatted)")
                cardHeaderView.update(policyPeriod: "\("coverageDetails.policyPeriod.label".localized) \(policy.startDateFormatted) - \(policy.endDateFormatted)")
            case .CANCELED:
                cardHeaderView.update(expirationDate: "")
                cardHeaderView.update(policyPeriod: "\("coverageDetails.purchaseDate.label".localized) \(policy.startDateFormatted)")
            case .TERMINATED:
                cardHeaderView.update(expirationDate: "\("coverageDetails.finish.label".localized) \(policy.endDateFormatted)")
                cardHeaderView.update(policyPeriod: "\("coverageDetails.policyPeriod.label".localized) \(policy.startDateFormatted) - \(policy.endDateFormatted)")
            default:
                break
            }
        }
        
        switch type {
        case .perroBueno:
            guard let image = UIImage(named: "ic-blotchy-dog") else { return }
            cardHeaderView.update(leadingIcon: image)
            cardHeaderView.update(leadingTitle: "coverageDetails.perroBueno.label".localized,
                                  leadingSubtitle: "coverageDetails.accident&illness.label".localized.uppercased())
        case .gatoSano:
            guard let image = UIImage(named: "ic-blotchy-cat") else { return }
            cardHeaderView.update(leadingIcon: image)
            cardHeaderView.update(leadingTitle: "coverageDetails.gatoSano.label".localized,
                                  leadingSubtitle: "coverageDetails.accident&illness.label".localized.uppercased())
        case .preventiveAndWellness:
            guard let image = UIImage(named: "ic-blotchy-vet") else { return }
            cardHeaderView.update(leadingIcon: image)
            cardHeaderView.update(leadingTitle: "coverageDetails.preventiveAndWellness.label".localized,
                                  leadingSubtitle: "coverageDetails.extraService.label".localized)
        case .coveragePayment:
            guard let image = UIImage(named: "ic-blotchy-money") else { return }
            cardHeaderView.update(leadingIcon: image)
            cardHeaderView.update(leadingTitle: "coverageDetails.payment.label".localized)
            cardHeaderView.setTraillingTitleIsHidden(true)
        case .paymentSettings:
            guard let image = UIImage(named: "ic-blotchy-money") else { return }
            cardHeaderView.update(leadingIcon: image)
            cardHeaderView.update(leadingTitle: "coverageDetails.payment.label".localized)
            cardHeaderView.setTraillingTitleIsHidden(true)
            hideHeaderView()
            setupImageHeaderView()
        }
    }
    
    private func setupImageHeaderView() {
        guard let pet = policy?.pet else { return }
        petImageHeader.setup(pet: pet)
        petImageHeader.isHidden = false
    }
    
    private func setupWarningView() {
        guard let policy = policy,
              let type = type,
              let remainingDays = policy.waitingPeriodRemainingDays else { return }
        warningView.setup(type: .policy(policy: policy))
        warningView.isHidden = (!type.isPet || remainingDays < 0)
    }
    
    private func setupLabels() {
        guard let type = type,
              let reimbursment = policy?.reimbursment,
              let sumInsured = policy?.sumInsured,
              let endDate = policy?.endDateFormatted else { return }
        
        switch type {
        case .perroBueno, .gatoSano:
            rememberingLabelsView.update(topText: "coverageDetails.perroBuenoRemainingTop.label".localized,
                                         centerText: "coverageDetails.perroBuenoRemainingBottom.label".localized,
                                         bottomText: "coverageDetails.inAnnualLimit.label".localized,
                                         coloredText: "\(sumInsured.remainingValue.getCurrencyFormatted())")
        case .preventiveAndWellness:
            rememberingLabelsView.update(centerText: "coverageDetails.reimbursement.label".localized,
                                         coloredText: "\(reimbursment.amount.formatted)%")
        case .coveragePayment, .paymentSettings:
            rememberingLabelsView.isHidden = true
            nextBilling.isHidden = true
            nextBilling.setHighlightedText(text: "coverageDetails.nextBilling.label".localized + " " + endDate,
                                           style: TextStyle(color: .secondaryDarkest),
                                           highlightedText: endDate)
        }
    }
    
    private func setupProgressBar() {
        guard let policy = policy,
              let type = type else { return }
        progressBar.update(policy: policy)
        progressBar.isHidden = !type.isPet
    }
    
    private func setupMonthlyPayment() {
        guard let policy = policy,
              let type = type else { return }
        monthlyPayment.isHidden = !type.isPayment
        monthlyPayment.setup(policy: policy)
    }
    
    private func setupActionCardsList() {
        guard let type = type,
              let deductable = policy?.deductable,
              let reimbursment = policy?.reimbursment,
              let sumInsured = policy?.sumInsured else { return }
        
        let consumedDeductableFromTotal = "\(deductable.consumed.getCurrencyFormatted()) \("coverageDetails.consumedOfTotal.label".localized) \(deductable.limit.getCurrencyFormatted())"
        let consumedAnnualLimitFromTotal = "\(sumInsured.consumed.getCurrencyFormatted()) \("coverageDetails.consumedOfTotal.label".localized) \(sumInsured.limit.getCurrencyFormatted())"
        
        actionCardsList.update(cardBackgroundColor: .neutralBackground)
        switch type {
        case .perroBueno, .gatoSano:
            accordionCard.isHidden = false
            accordionCard.update(title: "coverageDetails.planSummary.label".localized)
            accordionCard.addItems(accordionItemsData: [
                AccordionCardItemData(leadingTitle: "coverageDetails.reimbursement.label".localized,
                                      traillingTitle: "\(reimbursment.amount.formatted)%"),
                AccordionCardItemData(leadingTitle: "coverageDetails.deductible.label".localized,
                                      traillingTitle: consumedDeductableFromTotal),
                AccordionCardItemData(leadingTitle: "coverageDetails.annualLimit.label".localized,
                                      traillingTitle: consumedAnnualLimitFromTotal)])
            guard let coverageListImage = UIImage(named: "ic-coverage-list") else { return }
            setupActionCard(image: coverageListImage,
                            title: "coverageDetails.whatIsCovered.card".localized,
                            action: didTapPlanCoveredAction)
        case .preventiveAndWellness:
            guard let coverageListImage = UIImage(named: "ic-coverage-list") else { return }
            setupActionCard(image: coverageListImage,
                            title: "coverageDetails.covered.card".localized,
                            action: didTapPreventiveCoveredAction)
        default:
            break
        }
        actionCardStackView.isHidden = false
    }
    
    private func setupActionCard(image: UIImage,
                                 title: String,
                                 action: SimpleClosure? = nil,
                                 didTapDataAction: AnyClosure? = nil) {
        actionCardsList.addActionCards(actionCardDataList: [
            ActionCardData(leadingImage: image,
                           leadingTitle: title,
                           didTapAction: action,
                           didTapDataAction: didTapDataAction)
        ])
    }
    
    private func setupDocumentCard() {
        guard let documents = documents,
              let arrowImage = UIImage(named: "ic-up-arrow"),
              let documentImage = UIImage(named: "ic-favorite-document") else { return }
        let data = ActionCardData(leadingImage: documentImage,
                                  traillingImage: arrowImage,
                                  leadingTitle: "coverageDetails.documentation.card".localized,
                                  viewType: .accordion(fontSize: .p16))
        accordionDocsListView.update(documentAction: didTapDocumentAction)
        accordionDocsListView.addItems(documents: documents)
        accordionDocsListView.setupButtonActionCard(data: data)
        showAccordionDocsListView()
    }
    
    private func showAccordionDocsListView() {
        guard let type = type else { return }
        UIView.animate(withDuration: 0.2) { [weak self] in
            guard let self = self else { return }
            self.accordionDocsListView.isHidden = !type.isPet
        }
    }
}

// MARK: - Public Methods
extension CoverageDetailsCard {
    
    func setup(policy: PetPolicy, type: CoverageDetailsCardType) {
        self.policy = policy
        self.type = type
        setupLayout()
    }
    
    func hideHeaderView() {
        cardHeaderView.isHidden = true
    }
    
    func update(documents: [KanguroSharedDomain.PolicyDocumentData], documentAction: @escaping AnyClosure) {
        self.documents = documents
        self.didTapDocumentAction = documentAction
        setupDocumentCard()
    }
}
