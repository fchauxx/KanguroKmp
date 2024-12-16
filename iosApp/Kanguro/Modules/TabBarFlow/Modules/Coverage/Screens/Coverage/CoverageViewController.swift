import UIKit

class CoverageViewController: BaseViewController, CardPositionProtocol, ClaimsNavigationProtocol {
    
    // MARK: - Dependencies
    var viewModel: CoverageViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet private var backgroundView: UIView!
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var coverageList: CoverageCardList!
    @IBOutlet private var actionCardsList: ActionCardsList!
    
    // MARK: Stored Properties
    var cardPosition: CGPoint?
    
    // MARK: Actions
    var blockedAction: SimpleClosure = {}
    var didTapCardAction: PetPolicyClosure = { _ in }
    var didTapFileClaimAction: SimpleClosure = {}
    var didTapClaimsAction: SimpleClosure = {}
    var didTapPaymentSettingsAction: SimpleClosure = {}
    var didTapFAQAction: SimpleClosure = {}
    var didTapGetAQuoteAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension CoverageViewController {
    
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
extension CoverageViewController {
    
    private func changed(state: CoverageViewState) {
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
        case .blockedUser:
            blockedAction()
        }
    }
}

// MARK: - Setup
extension CoverageViewController {
    
    private func setupLayout() {
        setupLabels()
        setupCoverageList()
        setupActionCardsList()
    }
    
    private func setupLabels() {
        titleLabel.set(text: "coverage.coverageTitle.label".localized, style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
    }
    
    private func setupCoverageList() {
        coverageList.setup(policies: viewModel.policies)
        coverageList.didTapItemAction = { [weak self] index in
            guard let self else { return }
            self.cardPosition = self.coverageList.cardPosition
            self.didTapCardAction(self.viewModel.policies[index])
        }
        coverageList.didTapAddButtonAction = { [weak self] in
            guard let self else { return }
            self.didTapGetAQuoteAction()
        }
    }
    
    private func setupActionCardsList() {
        guard let fileClaimImage = UIImage(named: "ic-tab-more-default"),
              let getQuoteImage = UIImage(named: "ic-tab-dashboard-default"),
              let claimsImage = UIImage(named: "ic-claims"),
              let paymentImage = UIImage(named: "ic-payment"),
              let faqImage = UIImage(named: "ic-faq") else { return }
        
        actionCardsList.addActionCards(actionCardDataList: [
            ActionCardData(leadingImage: fileClaimImage,
                           leadingTitle: "moreActions.fileClaim.card".localized,
                           didTapAction: { [weak self] in
                               guard let self = self else { return }
                               self.viewModel.checkIfUserIsBlocked()
                               self.didTapFileClaimAction()
                           }),
            ActionCardData(leadingImage: getQuoteImage,
                           leadingTitle: "more.getQuote.actionCard".localized,
                           didTapAction: didTapGetAQuoteAction),
            ActionCardData(leadingImage: claimsImage,
                           leadingTitle: "moreActions.trackClaim.card".localized,
                           didTapAction: { [weak self] in
                               guard let self = self else { return }
                               self.viewModel.checkIfUserIsBlocked()
                               self.didTapClaimsAction()
                           }),
            ActionCardData(leadingImage: paymentImage,
                           leadingTitle: "moreActions.payment.card".localized,
                           didTapAction: { [weak self] in
                               guard let self = self else { return }
                               self.viewModel.checkIfUserIsBlocked()
                               self.didTapPaymentSettingsAction()
                           }),
            ActionCardData(leadingImage: faqImage,
                           leadingTitle: "moreActions.FAQ.card".localized,
                           didTapAction: didTapFAQAction)
        ])
        actionCardsList.update(title: "moreActions.titleLabel.label".localized)
    }
}
