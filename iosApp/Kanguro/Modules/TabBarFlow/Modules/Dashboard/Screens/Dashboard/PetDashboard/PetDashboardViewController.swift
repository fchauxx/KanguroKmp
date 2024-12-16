import UIKit
import StoreKit
import Resolver
import SwiftUI
import KanguroDesignSystem

class PetDashboardViewController: BaseViewController, CardPositionProtocol, ClaimsNavigationProtocol {
    
    // MARK: - Dependencies
    var viewModel: PetDashboardViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet var scrollView: UIScrollView!
    @IBOutlet var headerTitleLabel: CustomLabel!
    @IBOutlet var coverageList: CoverageCardList!
    @IBOutlet var actionCardsList: ActionCardsList!
    @IBOutlet var carouselHostingView: UIView!
    
    // MARK: Stored Properties
    var cardPosition: CGPoint?
    let refreshControl = UIRefreshControl()
    
    // MARK: - Actions
    var blockedAction: SimpleClosure = {}
    var didTapCardAction: PetPolicyClosure = { _ in }
    var goToPartnerWebsite: StringClosure = { _ in }
    var didTapGetAQuoteAction: SimpleClosure = {}
    
    var didTapTalkToVetAction: SimpleClosure = {}
    var didTapFileClaimAction: SimpleClosure = {}
    var didTapClaimsAction: SimpleClosure = {}
    var didTapDirectPayAction: PetsClosure = { _ in }
    
    var didTapVaccinationAction: SimpleClosure = {}
    var didTapPaymentSettingsAction: SimpleClosure = {}
    
    var didTapMapCellAction: SimpleClosure = {}
    var didTapFAQAction: SimpleClosure = {}
    
    var restartAppAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension PetDashboardViewController {
    
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
        viewModel.checkIfUserIsBlocked()
        viewModel.checkIfUserLanguageChanged()
        viewModel.askForNotificationPermissionIfNeeded()
    }
}

// MARK: - View State
extension PetDashboardViewController {
    
    private func changed(state: PetDashboardViewState) {
        
        refreshControl.endRefreshing()
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            askForUserReview()
        case .requestFailed:
            showSimpleAlert(message: viewModel.requestError)
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

extension PetDashboardViewController {
    
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
