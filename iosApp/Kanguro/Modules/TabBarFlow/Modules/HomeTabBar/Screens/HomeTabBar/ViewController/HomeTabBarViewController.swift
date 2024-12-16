import UIKit
import SwiftUI
import KanguroDesignSystem
import KanguroRentersPresentation
import KanguroNetworkDomain
import Resolver

public final class HomeTabBarViewController: BaseViewController {
    
    // MARK: - Dependencies
    @LazyInjected var network: NetworkProtocol

    // MARK: - IBOutlets
    @IBOutlet private var tabBarView: UIView!
    @IBOutlet var tabBarBackgroundView: UIView!
    @IBOutlet var tabBarStackView: UIStackView!
    
    // MARK: - Stored Properties
    var viewModel: HomeTabBarViewModel!
    var dashboardCoordinator: DashboardCoordinator?
    var chatbotCoordinator: CentralChatbotCoordinator?
    let notificationCenter = NotificationCenter.default
    var changedPetPicture: Bool = false
    
    // MARK: - Computed Properties
    var shouldShowRenters: Bool? {
        try? viewModel.shouldShowRenters.execute(key: .shouldShowRenters)
    }
    var safeAreaBottomInset: CGFloat {
        guard let keyWindow = UIApplication.shared.connectedScenes
            .compactMap({ $0 as? UIWindowScene })
            .flatMap({ $0.windows })
            .first else { return 0 }

        return keyWindow.safeAreaInsets.bottom
    }
    var contentFrame: CGRect {
        let bounds = view.bounds
        return CGRect(x: bounds.origin.x,
                      y: bounds.origin.y,
                      width: bounds.width,
                      height: bounds.height - safeAreaBottomInset)
    }
    var contentFrameWithoutTabBar: CGRect {
        let bounds = view.bounds
        return CGRect(x: bounds.origin.x,
                      y: bounds.origin.y,
                      width: bounds.width,
                      height: bounds.height)
    }

    // MARK: - Actions
    var logoutAction: SimpleClosure = {}
    var goToUpdateRequiredAction: SimpleClosure = {}
    var goToBlockedUserAction: SimpleClosure = {}
    var goToAdditionalInfoAction: PetsClosure = { _ in }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
    }
    
    init(
        dashboardCoordinator: DashboardCoordinator,
        chatbotCoordinator: CentralChatbotCoordinator
    ) {
        super.init(nibName: "HomeTabBarViewController", bundle: .main)
        self.dashboardCoordinator = dashboardCoordinator
        self.chatbotCoordinator = chatbotCoordinator
    }
    
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
}

// MARK: - Life Cycle
extension HomeTabBarViewController {
    
    public override func viewDidLoad() {
        super.viewDidLoad()
        registerObservers()
    }
}

// MARK: - Private Methods
extension HomeTabBarViewController {

    private func registerObservers() {
        notificationCenter.addObserver(self,
                                       selector: #selector(hideTabBar),
                                       name: .hideTabBar,
                                       object: nil)
        notificationCenter.addObserver(self,
                                       selector: #selector(showTabBar),
                                       name: .showTabBar,
                                       object: nil)
        notificationCenter.addObserver(self,
                                       selector: #selector(logoutUser),
                                       name: .logoutUser,
                                       object: nil)

        observe(viewModel.$viewState) { [weak self] state in
            guard let self else { return }
            self.changed(state: state)
        }
        observe(viewModel.$rentersViewState) { [weak self] state in
            guard let self else { return }
            self.changedRenters(state: state)
        }
        observe(viewModel.$petsViewState) { [weak self] state in
            guard let self else { return }
            self.changedPet(state: state)
        }
    }

    func setTabBarHidden(_ hidden: Bool) {
        var color: UIColor
        var alpha: CGFloat
        if hidden {
            color = .neutralBackground
            alpha = 0
        } else {
            color = .white
            alpha = 1
        }
        UIView.animate(withDuration: 0.2,
                       delay: 0,
                       options: .curveEaseInOut) { [weak self] in
            guard let self else { return }
            self.view.backgroundColor = color
            self.tabBarView.alpha = alpha
            tabBarBackgroundView.backgroundColor = color
        }
    }

    func handleDeepLink(_ type: DeepLink) {
        switch type {
        case .file_claim:
            hideLoadingView { [weak self] in
                guard let self else { return }
                self.tapMenu(menuItem: self.viewModel.menus[3])
                self.viewModel.deepLinkType = nil

            }
        }
    }
}

// MARK: - Notifications
extension HomeTabBarViewController {

    @objc func hideTabBar() {
        setTabBarHidden(true)
    }

    @objc func showTabBar() {
        setTabBarHidden(false)
    }

    @objc func logoutUser() {
        viewModel.keychain.cleanAll()
        hideLoadingView(duration: 0)
        NotificationCenter.default.removeObserver(self, name: .logoutUser, object: nil)
        logoutAction()
    }
}

// MARK: - View State
extension HomeTabBarViewController {
    
    func changed(state: HomeViewState) {
        switch state {
        case .started:
            viewModel.checkUserLanguageAndStatus(showLoading: true)
        case .loading:
            showLoadingView(shouldAnimate: true)
        case .blockedUser:
            hideLoadingView()
            goToBlockedUserAction()
        case .allowedUser:
            viewModel.getBackendVersion()
        case .validVersion:
            viewModel.startDataRequests()
        case .invalidVersion:
            hideLoadingView()
            goToUpdateRequiredAction()
        case .requestFailed, .noPoliciesFound:
            hideLoadingView()
            showSimpleAlert(message: viewModel.requestError)
            viewModel.checkIfUserExists()
        case .userNotFound:
            showActionAlert(message: viewModel.requestError,
                            action: logoutUser)
        case .goToHome:
            if viewModel.isAllDataFetched {
                setupTabBar()
            }
        default:
            break
        }
    }
    
    func changedRenters(state: RentersViewState) {
        switch state {
        case .getRenterPoliciesSucceeded:
            viewModel.viewState = .goToHome
        case .renterProductNotFound:
            if viewModel.petsViewState == .petProductNotFound {
                // Do not found any pet or renter policy
                viewModel.showNoPoliciesInAccountError()
            } else {
                viewModel.viewState = .goToHome
            }
        default:
            break
        }
    }
    
    func changedPet(state: PetsViewState) {
        switch state {
        case .goToPetAdditionalInfoFlow:
            hideLoadingView()
            goToAdditionalInfoAction(viewModel.incompletedPets)
        case .loadingPetPicture:
            showLoadingView(shouldAnimate: false)
        case .didSetPetPolicies:
            viewModel.viewState = .goToHome
        case .petProductNotFound:
            if viewModel.rentersViewState == .renterProductNotFound {
                // Do not found any pet or renter policy
                viewModel.showNoPoliciesInAccountError()
            } else {
                viewModel.viewState = .goToHome
            }        
        default:
            break
        }
    }
}

// MARK: - IB Actions
extension HomeTabBarViewController {
    
    func tapMenu(menuItem: TabBarMenuItem) {
        viewModel.tapMenu(menu: menuItem)
    }
}
