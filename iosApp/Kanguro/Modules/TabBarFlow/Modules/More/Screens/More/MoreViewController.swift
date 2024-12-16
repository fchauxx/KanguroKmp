import UIKit
import MessageUI

class MoreViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: MoreViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet var preferencesActionCardsList: ActionCardsList!
    @IBOutlet var legalActionCardsList: ActionCardsList!
    @IBOutlet var supportActionCardsList: ActionCardsList!
    @IBOutlet var emergencyActionCardsList: ActionCardsList!
    @IBOutlet var logoutActionCardsList: ActionCardsList!
    @IBOutlet var bannerView: UIView!
    @IBOutlet var bannerTitleLabel: CustomLabel!
    @IBOutlet var bannerSubtitleLabel: CustomLabel!
    @IBOutlet var versionLabel: CustomLabel!
    @IBOutlet var loader: UIActivityIndicatorView!
    
    @IBOutlet var loaderBackgroundView: UIView!
    
    // MARK: - Stored Properties

    // MARK: - Computed Properties
    var actionCardLists: [ActionCardsList] {
        [preferencesActionCardsList, emergencyActionCardsList, supportActionCardsList, legalActionCardsList, logoutActionCardsList]
    }
    var version: String? {
        if let infoDictionary: String = (Bundle.main.infoDictionary?["CFBundleShortVersionString"]) as? String {
            return ("v \(infoDictionary)")
        } else {
            return nil
        }
    }
    var shouldShowRenters: Bool? {
        try? viewModel.getFeatureFlag.execute(key: .shouldShowRenters)
    }
    
    // MARK: - Actions
    var didTapProfileAction: SimpleClosure = {}
    var didTapRemindersAction: SimpleClosure = {}
    var didTapPaymentSettingsAction: SimpleClosure = {}
    var didTapBillingPreferencesAction: SimpleClosure = {}
    var didTapSupportCauseAction: BoolClosure = { _ in }
    var didTapAppSettingsAction: SimpleClosure = {}
    var didTapContactUsAction: SimpleClosure = {}

    var didTapReferFriendButtonAction: SimpleClosure = {}
    
    var didTapVetAdviceAction: SimpleClosure = {}
    var didTapFAQAction: SimpleClosure = {}
    var didTapBlogAction: SimpleClosure = {}
    var didTapPetParentsAction: SimpleClosure = {}
    var didTapComunicationAction: SimpleClosure = {}

    var goToTermsOfServiceAction: SimpleClosure = {}
    var didTapPrivacyPolicyAction: SimpleClosure = {}
    var refreshActionCards: SimpleClosure = {}

    var didTapTalkToVet: SimpleClosure = {}

    var logoutAction: SimpleClosure = {}

    init() {
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: - Life Cycle
extension MoreViewController {

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
extension MoreViewController {

    private func changed(state: DefaultViewState) {

        showLoader(shouldShow: false)

        switch state {
        case .loading:
            showLoader(shouldShow: true)
        case .started:
            viewModel.analyticsLogScreen()
            viewModel.getContactInformation()
        case .dataChanged:
            if viewModel.shouldLogout { logoutAction() }
        default:
            break
        }
    }
}

// MARK: - Private Methods
extension MoreViewController {

    func showPopUpMenu() {
        guard let referFriendsVC = ReferFriendsPopUpViewController.create() as? ReferFriendsPopUpViewController,
              let keyWindow = UIApplication.shared.connectedScenes
            .compactMap({ $0 as? UIWindowScene })
            .flatMap({ $0.windows })
            .first(where: { $0.isKeyWindow }),
                let currentViewController = keyWindow.rootViewController else { return }
        let popUp = PopUpViewController(contentViewController: referFriendsVC)
        popUp.show(onViewController: currentViewController)
        referFriendsVC.didTapReferFriendAction = didTapReferFriendButtonAction
    }

    func sendSmsMessage() {
        guard let smsInfo = viewModel.contactInformation.first(where: { $0.type == .sms }) else {
            showSimpleAlert(message: "more.sms.contactInfo.unavailable.alert".localized)
            return
        }

        if MFMessageComposeViewController.canSendText() {
            let controller = MFMessageComposeViewController()
            let messageBody = smsInfo.data.text
            var phoneNumber = smsInfo.data.number
            if !phoneNumber.hasPrefix("+") {
                phoneNumber = "+" + phoneNumber
            }
            
            controller.body = messageBody
            controller.recipients = [phoneNumber]
            controller.messageComposeDelegate = self
            self.present(controller, animated: true, completion: nil)
        } else {
            showSimpleAlert(message: "more.sms.unavailable.alert".localized)
        }
    }

    func sendWhatsAppMessage() {
        guard let whatsAppInfo = viewModel.contactInformation.first(where: { $0.type == .whatsapp }) else {
            showSimpleAlert(message: "more.whatsapp.contactInfo.unavailable.alert".localized)
            return
        }
        let messageBody = whatsAppInfo.data.text
        let phoneNumber = whatsAppInfo.data.number
        let urlWhats = "https://wa.me/\(phoneNumber)?text=\(messageBody)"

        if let urlString = urlWhats.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed),
           let whatsappURL = URL(string: urlString),
           let whatsappAppURL = URL(string: "whatsapp://") {
            if UIApplication.shared.canOpenURL(whatsappAppURL) {
                UIApplication.shared.open(whatsappURL, options: [:], completionHandler: nil)
            } else {
                showSimpleAlert(message: "more.whatsapp.notInstalled.alert".localized)
            }
        }
    }

    func showLoader(shouldShow: Bool) {
        if shouldShow {
            loaderBackgroundView.isHidden = false
            loader.isHidden = false
            loader.startAnimating()
        } else {
            loaderBackgroundView.isHidden = true
            loader.stopAnimating()
            loader.isHidden = true
        }
    }
}

// MARK: - IB Actions
extension MoreViewController {
    
    @IBAction private func showReferFriendsTouchUpInside(_ sender: UIButton) {
        showPopUpMenu()
    }
}
