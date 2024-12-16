import UIKit
import KanguroDesignSystem
import KanguroSharedDomain
import StoreKit
import Resolver
import SwiftUI

class MixedDashboardViewController: BaseViewController, CardPositionProtocol, ClaimsNavigationProtocol {
    
    // MARK: - Dependencies
    var viewModel: MixedDashboardViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet var scrollView: UIScrollView!
    @IBOutlet var helloLabel: CustomLabel!
    @IBOutlet var usernameLabel: CustomLabel!
    @IBOutlet var petNamesLabel: CustomLabel!
    @IBOutlet var coverageList: CoverageCardList!
    @IBOutlet var reminderList: HorizontalReminderCardList!
    @IBOutlet var bannerTitleLabel: CustomLabel!
    @IBOutlet var bannerSubtitleLabel: CustomLabel!
    @IBOutlet var actionCardsList: ActionCardsList!
    @IBOutlet var notificationButton: UIButton!
    @IBOutlet var notificationIcon: UIImageView!
    
    // MARK: - SwiftUI
    @IBOutlet var rentersListView: UIView!
    @IBOutlet var donationBannerView: UIView!
    @IBOutlet var insuranceBannerView: UIView!
    
    // MARK: Stored Properties
    var cardPosition: CGPoint?
    let refreshControl = UIRefreshControl()
    
    // MARK: - Actions
    var goBackAction: SimpleClosure = {}
    var blockedAction: SimpleClosure = {}
    var didTapCardAction: PetPolicyClosure = { _ in }
    var didTapBannerButtonAction: SimpleClosure = {}
    var didTapMedicalHistoryReminderAction: RemindersClosure = { _ in }
    var goToPartnerWebsite: StringClosure = { _ in}
    var didTapNonActivePolicyAction: UserPolicyTypeClosure = { _ in }
    var didTapNotificationAction: SimpleClosure = {}
    var didTapSeeAllRemindersAction: SimpleClosure = {}
    var didTapMedicalHistoryCardAction: PetClosure = { _ in }
    var didTapCommunicationCardAction: StringClosure = { _ in }
    var goToRentersOnboarding: OnboardingChatClosure = { _,_ in }

    var didTapGetAQuotePetAction: SimpleClosure = {}
    var didTapGetAQuoteRentersAction: SimpleClosure = {}

    var didTapTalkToVetAction: SimpleClosure = {}
    var didTapClaimsAction: SimpleClosure = {}
    var didTapRenterPolicyCard: RenterPolicyClosure = { _ in }
    
    // Banners
    var didTapReferFriendButtonAction: SimpleClosure = {}
    var didTapDonationBannerAction: BoolClosure = { _ in }
    
    // Action cards
    var didTapFileClaimAction: SimpleClosure = {}
    var didTapCloudAction: SimpleClosure = {}
    var didTapBlogAction: SimpleClosure = {}
    var didTapFAQAction: SimpleClosure = {}
    
    var restartAppAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension MixedDashboardViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
        
        if !viewModel.renterPolicies.isEmpty {
            let isDonating = viewModel.user?.donation != nil
            
            if let policyNeedsOnboarding = viewModel.renterPolicies.first(where: { $0.onboardingCompleted == false }) {
                goToRentersOnboarding(policyNeedsOnboarding, isDonating)
            }
        }
        
        observe(viewModel.$state) { [weak self] state in
            guard let self else { return }
            self.changed(state: state)
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        updateUsernameLabel()
        viewModel.checkIfUserIsBlocked()
        viewModel.checkIfUserLanguageChanged()
        viewModel.getReminders()
        viewModel.askForNotificationPermissionIfNeeded()
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        viewModel.remindersFlag = false
    }
}

// MARK: - View State
extension MixedDashboardViewController {
    
    private func changed(state: MixedDashboardViewState) {
        
        refreshControl.endRefreshing()
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            askForUserReview()
        case .requestFailed:
            showSimpleAlert(message: viewModel.requestError)
        case .getRemindersSucceeded:
            setupReminderList()
        case .notificationPermissionSucceeded:
            viewModel.putNotificationsToken()
        case .blockedUser:
            blockedAction()
        case .partnerRedirectionSucceeded(let url):
            goToPartnerWebsite(url)
        case .reloadApp:
            showActionAlert(title: "alert.attention".localized,
                            message: "dashboard.languagePreference.label".localized,
                            action: restartAppAction)
        default:
            break
        }
    }
}

extension MixedDashboardViewController {
    
    func showReferFriendsPopUpMenu() {
        guard let referFriendsVC = ReferFriendsPopUpViewController.create() as? ReferFriendsPopUpViewController,
        let keyWindow = UIApplication.shared.connectedScenes
            .compactMap({ $0 as? UIWindowScene })
            .flatMap({ $0.windows })
            .first(where: { $0.isKeyWindow }), let currentViewController = keyWindow.rootViewController  else { return }
        let popUp = PopUpViewController(contentViewController: referFriendsVC)
        popUp.show(onViewController: currentViewController)
        referFriendsVC.didTapReferFriendAction = didTapReferFriendButtonAction
    }
    
    func showMedicalHistoryReminderPopUp() {
        guard let keyWindow = UIApplication.shared.connectedScenes
            .compactMap({ $0 as? UIWindowScene })
            .flatMap({ $0.windows })
            .filter({ $0.isKeyWindow })
            .last,
              let navigationController = keyWindow.rootViewController as? UINavigationController,
              let medicalHistoryReminderVC = MedicalReminderPopUpViewController.create() as? MedicalReminderPopUpViewController else { return }

        medicalHistoryReminderVC.medicalReminders = viewModel.medicalHistoryReminder
        let popUp = PopUpViewController(contentViewController: medicalHistoryReminderVC)

        popUp.show(onViewController: navigationController)
        medicalHistoryReminderVC.didTapMedicalHistoryReminderAction = didTapMedicalHistoryReminderAction
    }
    
    func showRentersFileClaimPopUp() {
        guard let informationPopUp = InformationPopUpViewController.create() as? InformationPopUpViewController,
              let keyWindow = UIApplication.shared.connectedScenes
            .compactMap({ $0 as? UIWindowScene })
            .flatMap({ $0.windows })
            .first(where: { $0.isKeyWindow }), let currentViewController = keyWindow.rootViewController else { return }
        
        let popUp = PopUpViewController(contentViewController: informationPopUp)
        let data = InformationPopUpData(
            title: "centralChatbot.fileClaim.label".localized,
            description: "dashboard.fileClaim.popup.label".localized,
            style: .detail,
            highlighted: (
                text: "rentersclaims@kanguroinsurance.com",
                style: TextStyle(
                    color: .tertiaryDark,
                    size: .p16,
                    underlined: true
                )
            )
        )
        informationPopUp.setup(data: data)
        popUp.show(onViewController: currentViewController)
    }
    
    func showFileClaimPopUp() {
        guard let bottomButtonsPopUp = BottomButtonsPopUpViewController.create() as? BottomButtonsPopUpViewController,
        let keyWindow = UIApplication.shared.connectedScenes
            .compactMap({ $0 as? UIWindowScene })
            .flatMap({ $0.windows })
            .first(where: { $0.isKeyWindow }), let currentViewController = keyWindow.rootViewController  else { return }
        
        let popUp = PopUpViewController(contentViewController: bottomButtonsPopUp)
        bottomButtonsPopUp.setup(data: [
            ActionCardData(leadingImage: UIImage(named: "ic-dog"),
                           leadingTitle: "bottomButtonsPopUp.claim.pet".localized,
                           didTapAction: { [weak self] in
                               guard let self else { return }
                               bottomButtonsPopUp.dismiss(animated: true) {
                                   self.didTapFileClaimAction()
                               }
                           }),
            ActionCardData(leadingImage: UIImage(named: "ic-home"),
                           leadingTitle: "bottomButtonsPopUp.claim.renters".localized,
                           didTapAction: { [weak self] in
                               guard let self else { return }
                               bottomButtonsPopUp.dismiss(animated: true) {
                                   self.showRentersFileClaimPopUp()
                               }
                           })
        ])
        
        popUp.show(onViewController: currentViewController)
    }
    
    func updateUsernameLabel() {
        if let username = viewModel.username {
            let userHasNoPets = viewModel.pets.isEmpty
            let text = userHasNoPets ? username : "\(username) &"
            let labelColor: UIColor = userHasNoPets ? .primaryDarkest : .secondaryDarkest
            
            usernameLabel.set(
                text: text,
                style: TextStyle(
                    color: labelColor,
                    weight: .bold,
                    size: .h32,
                    font: .raleway
                )
            )
            
            usernameLabel.setupToFitWidth()
        }
    }
    
    func askForUserReview() {
        if #available(iOS 14.0, *) {
            if viewModel.shouldAskForUserReview() {
                Task { @MainActor [weak self] in
                    // Delay for two seconds to avoid interrupting the person using the app.
                    // Use the equation n * 10^9 to convert seconds to nanoseconds.
                    try? await Task.sleep(nanoseconds: UInt64(2e9))
                    if let windowScene = self?.view.window?.windowScene,
                       self?.navigationController?.topViewController is DashboardViewController {
                        SKStoreReviewController.requestReview(in: windowScene)
                    }
                }
            }
        }
    }
}

// MARK: - IB Actions
extension MixedDashboardViewController {
    
    @IBAction func notificationButtonTouchUpInside(_ sender: UIButton) {
        didTapNotificationAction()
    }
    
    @IBAction func referKanguroTouchUpInside(_ sender: UIButton) {
        didTapBannerButtonAction()
    }
}
