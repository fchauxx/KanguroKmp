import UIKit
import Resolver
import IQKeyboardManagerSwift
import FirebaseCore
import FirebaseMessaging
import FirebaseDynamicLinks
import KanguroStorageDomain
import KanguroFeatureFlagData
import KanguroFeatureFlagDomain
import Sentry
// @main
class AppDelegate: UIResponder {
    
    // MARK: - Dependencies
    @LazyInjected var userDefaults: Storage
    @LazyInjected var keychain: SecureStorage
    @LazyInjected var environment: EnvironmentProtocol
    
    // MARK: - Stored Properties
    var window: UIWindow?
    var coordinator: Coordinator?
    
    // MARK: - Computed Properties
    var isTesting: Bool {
#if DEBUG
        return CommandLine.arguments.contains("-unitTests")
#else
        return false
#endif
    }
    
    var isUITesting: Bool {
#if DEBUG
        return CommandLine.arguments.contains("-uiTests")
#else
        return false
#endif
    }
}

// MARK: - UIApplicationDelegate
extension AppDelegate: UIApplicationDelegate {
    
    func application(_ application: UIApplication,
                     didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        setupAppDependencies()
        setupFirebase()
        setupFeatureFlags()
        setupLaunch()
        setupSentry()
        return true
    }
    
    func application(_ application: UIApplication, continue userActivity: NSUserActivity, restorationHandler: @escaping ([UIUserActivityRestoring]?) -> Void) -> Bool {
        guard userActivity.activityType == NSUserActivityTypeBrowsingWeb,
              let url = userActivity.webpageURL else { return false }
        SentrySDK.capture(message: "handleUniversalLink \(url)")
        let _ = DynamicLinks.dynamicLinks()
            .handleUniversalLink(url) { dynamiclink, error in
                guard let dynamiclink,
                      let finalLink = dynamiclink.url else { return }
                self.handleDeepLinkURL(url: finalLink)
            }
        return true
    }
}

// MARK: - Setup
extension AppDelegate {
    
    func setupLaunch() {
        setupCoordinator()
        setupWindow()
        setupKeyboard()
    }
    
    func setupFirebase() {
        guard let filePath = Bundle.main.path(forResource: "GoogleService-Info-\(environment.target)", ofType: "plist"),
              let fileopts = FirebaseOptions(contentsOfFile: filePath) else { return }
        
        FirebaseApp.configure(options: fileopts)
        FirebaseConfiguration.shared.setLoggerLevel(.min)
        
        UNUserNotificationCenter.current().delegate = self
        Messaging.messaging().delegate = self
    }
    
    func setupSentry() {
#if DEBUG
        return
#else
        let sentryDsn = environment.sentryDsn
        let sentryEnv = environment.sentryEnv
        
        SentrySDK.start { options in
            options.dsn = sentryDsn
            options.environment = sentryEnv
            options.debug = true
            options.tracesSampleRate = 1.0
        }
#endif
    }
    
    func setupAppDependencies() {
        AppDependencies.setup()
    }
    
    func setupCoordinator() {
        let navigation = UINavigationController()
        navigation.isNavigationBarHidden = true
        if shouldClean() {
            keychain.cleanAll()
            userDefaults.save(value: true, key: "didResetKeychain")
        }
        
        coordinator = OnboardingCoordinator(navigation: navigation)
        coordinator?.start()
    }
    
    private func shouldClean() -> Bool {
        let savedPref: Bool? = userDefaults.get(key: "didResetKeychain")
        return savedPref != true
    }
    
    func setupWindow() {
        window = UIWindow(frame: UIScreen.main.bounds)
        window?.rootViewController = coordinator?.navigation
        window?.makeKeyAndVisible()
    }
    
    func setupKeyboard() {
        IQKeyboardManager.shared.enable = true
    }

    func setupFeatureFlags() {
        let featureFlagsService: SetDefaultValuesUseCaseProtocol = Resolver.resolve()
        let defaultValues: [String: NSObject] = [
            "shouldShowRenters": false as NSObject,
            "shouldUseOTPValidation": false as NSObject,
            "shouldShowLiveVet": false as NSObject
        ]
        do {
            try featureFlagsService.execute(defaultValues)
        } catch {
            assertionFailure("Could not setup Feature Flags default values")
        }
    }
}

// MARK: - Private Methods
extension AppDelegate {
    
    private func handleDeepLinkURL(url: URL) {
        let deepLink = url.description
        guard let deepLinkPath = URLComponents(string: deepLink)?.path,
              let linkType = DeepLink.allCases.first(where: {
                  $0.rawValue == deepLinkPath.substring(fromIndex: 1)
              }) else { return }
        
        handleDeepLinkType(link: linkType)
    }
    
    private func handleDeepLinkType(link: DeepLink) {
        
        guard let actualCoordinator = coordinator as? OnboardingCoordinator,
              let visibleVC = actualCoordinator.homeTabBarCoordinator.navigation.visibleViewController else { return }
        
        switch link {
        case .file_claim:
            if let _ = visibleVC as? WelcomeViewController {
                actualCoordinator.authCoordinator.deepLinkType = .file_claim
            } else if let splashVC = visibleVC as? SplashViewController {
                splashVC.deepLinkType = .file_claim
            } else {
                guard let actualVC = visibleVC as? HomeTabBarViewController else { return }
                if actualVC.viewModel.didSetupTabBar {
                    actualVC.tapMenu(menuItem: actualVC.viewModel.menus[2])
                }
            }
        }
    }
}

// MARK: - UNUserNotificationCenterDelegate
extension AppDelegate: UNUserNotificationCenterDelegate {
    
    func userNotificationCenter(_ center: UNUserNotificationCenter, willPresent notification: UNNotification, withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
        completionHandler([[.banner, .sound]])
    }
    
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        Messaging.messaging().apnsToken = deviceToken
    }
    
    func userNotificationCenter(_ center: UNUserNotificationCenter, didReceive response: UNNotificationResponse, withCompletionHandler completionHandler: @escaping () -> Void) {
        completionHandler()
    }
    
    func application(_ application: UIApplication, didFailToRegisterForRemoteNotificationsWithError error: Error) {
        debugPrint(error)
    }
    
    func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
        guard let components = URLComponents(url: url, resolvingAgainstBaseURL: false),
              let email = components.queryItems?.first(where: { $0.name == DeepLinkConstants.emailFieldKey })?.value,
              components.path == DeepLinkConstants.loginPath else {
               return false
           }
        guard let actualCoordinator = coordinator as? OnboardingCoordinator else {
            return false
        }
        
        guard actualCoordinator.user == nil else {
            return false
        }
        let authCoordinator = AuthCoordinator(navigation: actualCoordinator.navigation, homeTabBarCoordinator:actualCoordinator.homeTabBarCoordinator)
        actualCoordinator.authCoordinator = AuthCoordinator(navigation: actualCoordinator.navigation, homeTabBarCoordinator:actualCoordinator.homeTabBarCoordinator)
        actualCoordinator.authCoordinator.email = email
        actualCoordinator.authCoordinator.start()
        return true
    }
}

// MARK: - MessagingDelegate
extension AppDelegate: MessagingDelegate {
    
    func messaging(_ messaging: Messaging, didReceiveRegistrationToken fcmToken: String?) {
        
        let tokenDict = ["token": fcmToken ?? ""]
        NotificationCenter.default.post(name: Notification.Name("FCMToken"), object: nil, userInfo: tokenDict)
        
        Messaging.messaging().token { token, error in
            if let error {
                print(error)
            } else if let token {
                self.keychain.save(value: token, key: KeychainStorageKey.fcmToken.rawValue)
            }
        }
    }
}
