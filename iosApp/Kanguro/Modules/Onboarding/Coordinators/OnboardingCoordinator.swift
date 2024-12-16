import UIKit
import KanguroUserData
import SwiftUI
import Resolver
import KanguroStorageDomain
import KanguroUserDomain
import KanguroSharedPresentation
import KanguroSharedDomain

class OnboardingCoordinator: Coordinator {
    
    // MARK: - Dependencies
    @LazyInjected var keychain: SecureStorage
    @LazyInjected var getLocalUserService: GetLocalUser
    @LazyInjected var updateLocalUserService: UpdateLocalUser
    @LazyInjected var refreshToken: RefreshTokenUseCaseProtocol

    // MARK: - Stored Properties
    var authCoordinator: AuthCoordinator
    var homeTabBarCoordinator: HomeTabBarCoordinator
    
    // MARK: - Computed Properties
    var user: User? {
        guard let user: User = try? getLocalUserService.execute().get() else { return nil }
        return user
    }
    
    // MARK: - Initializers
    override init(navigation: UINavigationController) {
        homeTabBarCoordinator = HomeTabBarCoordinator(navigation: navigation)
        authCoordinator = AuthCoordinator(navigation: navigation, 
                                          homeTabBarCoordinator: homeTabBarCoordinator)
        super.init(navigation: navigation)
    }
    
    // MARK: - Coordinator
    override func start() {
        homeTabBarCoordinator.onboardingCoordinator = self
        navigateToSplash()
    }
}

// MARK: - Navigation Methods
extension OnboardingCoordinator {

    private func navigateToSplash() {
        let splashViewModel = SplashViewModel(
            user: user,
            updateLocalUserService: updateLocalUserService,
            refreshToken: refreshToken
        )

        let controller = SplashViewController()
        controller.setupWith(viewModel: splashViewModel)

        controller.didFinishAnimationAction = { [weak self] shouldNavigateToLogin in
            guard let self else { return }

            if controller.deepLinkType != nil {
                self.homeTabBarCoordinator.deepLinkType = controller.deepLinkType
                self.authCoordinator.deepLinkType = controller.deepLinkType
                controller.deepLinkType = nil
            }

            shouldNavigateToLogin ? self.navigateToWelcome() : self.navigateToHomeTabBar()
        }
        navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToWelcome() {
        let viewModel = OnboardingViewModel()
        let controller = OnboardingView(viewModel: viewModel, signInAction: { [weak self] in
            guard let self else { return }
            self.authCoordinator.start()
        }, getAQuoteAction: { [weak self] language in
            guard let self else { return }
            self.navigateToGetAQuote(language: language)
        })
        
        let hostingController = UIHostingController(rootView: controller, ignoreSafeArea: true)
        navigation.pushViewController(hostingController, animated: true)
    }
    
    private func navigateToGetAQuote(language: Language) {
        if let url = URL(string: AppURLs.getProductQuoteUrl(language: language)) {
            let controller = WebviewViewController(url: url)
            navigation.present(controller, animated: true)
        }
    }
    
    private func navigateToHomeTabBar() {
        homeTabBarCoordinator.start()
    }
}
