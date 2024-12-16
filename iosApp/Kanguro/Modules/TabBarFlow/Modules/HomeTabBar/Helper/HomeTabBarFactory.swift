import Foundation
import UIKit
import KanguroSharedData

public struct HomeTabBarFactory {

    public static func makeViewController(
        nav: UINavigationController
    ) -> HomeTabBarViewController {
        let dashboardCoordinator = DashboardCoordinator(
            navigation: nav
        )
        let chatbotCoordinator = CentralChatbotCoordinator(
            navigation: nav,
            chatbotData: ChatbotData(type: .NewClaim),
            serviceType: .local
        )
        let controller = HomeTabBarViewController(
            dashboardCoordinator: dashboardCoordinator,
            chatbotCoordinator: chatbotCoordinator
        )
        return controller
    }

    public static func makeViewModel() -> HomeTabBarViewModel {
        return HomeTabBarViewModel()
    }
}
