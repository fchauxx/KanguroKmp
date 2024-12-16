import UIKit

class ReferFriendsViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: ReferFriendsViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var firstDescriptionLabel: CustomLabel!
    @IBOutlet private var secondDescriptionLabel: CustomLabel!
    @IBOutlet private var termsOfServiceLabel: CustomLabel!
    @IBOutlet private var shareCodeView: ShareCodeView!
    
    // MARK: Actions
    var goBackAction: SimpleClosure = {}
    var didTapShareCodeAction: SimpleClosure = {}
    var didTapTermsOfServiceAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension ReferFriendsViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
        viewModel.analyticsLogScreen()
    }
}

// MARK: - Setup
extension ReferFriendsViewController {
    
    private func setupLayout() {
        setupLabels()
        setupActions()
        setupCodeView()
    }
    
    private func setupLabels() {
        let descriptionStyle = TextStyle(color: .neutralMedium, size: .p16)
        titleLabel.set(text: "referFriends.title.label".localized,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h28, font: .raleway))
        firstDescriptionLabel.set(text: "referFriends.firstDescription.label".localized,
                                  style: descriptionStyle)
        secondDescriptionLabel.setHighlightedText(text: "referFriends.secondDescription.label".localized,
                                                  style: descriptionStyle,
                                                  highlightedText: "referFriends.giftCard.label".localized,
                                                  highlightedStyle: TextStyle(color: .primaryDarkest, weight: .bold, size: .p16))
        termsOfServiceLabel.set(text: "referFriends.termsOfService.label".localized,
                                style: TextStyle(color: .tertiaryExtraDark, weight: .bold, underlined: true))
    }
    
    private func setupActions() {
        didTapShareCodeAction = { [weak self] in
            guard let self = self,
                  let referralCode = self.viewModel.referralCode,
                  let url = AppURLs.kanguro.url,
                  let user = self.viewModel.username else { return }
            self.share(message: user +
                       "referFriends.shareCode.sharing".localized +
                       "\(url.path)" +
                       "referFriends.referralCode.sharing".localized +
                       referralCode)
        }
    }
    
    private func setupCodeView() {
        guard let referralCode = viewModel.referralCode else { return }
        shareCodeView.setup(code: referralCode)
        shareCodeView.update(action: didTapShareCodeAction)
    }
}

// MARK: - Private Methods
extension ReferFriendsViewController {
    
    private func share(message: String) {
            let objectsToShare = [message] as [Any]
            let activityVC = UIActivityViewController(activityItems: objectsToShare, applicationActivities: nil)
            self.present(activityVC, animated: true, completion: nil)
    }
}

// MARK: - IB Actions
extension ReferFriendsViewController {
    
    @IBAction private func closeTouchUpInside(_ sender: UIButton) {
        goBackAction()
    }
    
    @IBAction private func termsOfServiceTouchUpInside(_ sender: UIButton) {
        didTapTermsOfServiceAction()
    }
}
