import UIKit
import StoreKit
import Resolver

class DashboardViewController: BaseViewController, CardPositionProtocol, ClaimsNavigationProtocol {
    
    // MARK: - Dependencies
    var viewModel: DashboardViewModel!
    
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
    
    // MARK: Stored Properties
    var cardPosition: CGPoint?
    let refreshControl = UIRefreshControl()
    let partner = "ROAM"

    // MARK: - Actions
    var goBackAction: SimpleClosure = {}
    var blockedAction: SimpleClosure = {}
    var didTapCardAction: PetPolicyClosure = { _ in }
    var didTapProfileAction: SimpleClosure = {}
    var didTapBannerButtonAction: SimpleClosure = {}
    var didTapReferFriendButtonAction: SimpleClosure = {}
    var didTapMedicalHistoryReminderAction: RemindersClosure = { _ in }
    var goToPartnerWebsite: StringClosure = { _ in}

    var didTapSeeAllRemindersAction: SimpleClosure = {}
    var didTapMedicalHistoryCardAction: PetClosure = { _ in }
    var didTapCommunicationCardAction: StringClosure = { _ in }
    
    var didTapFileClaimAction: SimpleClosure = {}
    var didTapClaimsAction: SimpleClosure = {}
    var didTapGetAQuoteAction: SimpleClosure = {}
    var didTapVetAdviceAction: SimpleClosure = {}
    var didTapFAQAction: SimpleClosure = {}
    var didTapPetParentsAction: SimpleClosure = {}
    var didTapMapCellAction: SimpleClosure = {}
    var didTapDirectPayAction: PetsClosure = { _ in }
    
    var restartAppAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension DashboardViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        updateUsernameLabel()
        viewModel.checkIfUserLanguageChanged()
        viewModel.getReminders()
        viewModel.askForNotificationPermissionIfNeeded()
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        viewModel.remindersFlag = false
    }
}

// MARK: - View State
extension DashboardViewController {
    
    private func changed(state: DashboardViewState) {
        
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

extension DashboardViewController {
    
    func showReferFriendsPopUpMenu() {
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
    
    func showMedicalHistoryReminderPopUp() {
        guard let medicalHistoryReminderVC = MedicalReminderPopUpViewController.create() as? MedicalReminderPopUpViewController,
              let keyWindow = UIApplication.shared.connectedScenes
            .compactMap({ $0 as? UIWindowScene })
            .flatMap({ $0.windows })
            .first(where: { $0.isKeyWindow }),
        let currentViewController = keyWindow.rootViewController else { return }
        medicalHistoryReminderVC.medicalReminders = viewModel.medicalHistoryReminder
        let popUp = PopUpViewController(contentViewController: medicalHistoryReminderVC)
        popUp.show(onViewController: currentViewController)
        medicalHistoryReminderVC.didTapMedicalHistoryReminderAction = didTapMedicalHistoryReminderAction
    }

    func updateUsernameLabel() {
        if let username = viewModel.username {
            let text = viewModel.pets.isEmpty ? username : "\(username) &"
            usernameLabel.set(text: text,
                              style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
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
extension DashboardViewController {
    
    @IBAction func referKanguroTouchUpInside(_ sender: UIButton) {
        didTapBannerButtonAction()
    }
    
    @IBAction func profileTouchUpInside(_ sender: UIButton) {
        didTapProfileAction()
    }
    
    @IBAction func roamBannerTouchUpInside(_sender: UIButton) {
        self.viewModel.redirectToPartnerWebpage(partnerName: partner)
    }
}
