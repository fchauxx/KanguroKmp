import Foundation
import KanguroAnalyticsDomain
import KanguroAnalyticsData

final class AnalyticsSpy: KanguroAnalyticsModuleProtocol {
    var loggedScreens: [KanguroAnalyticsEnums.Screen] = []

    func analyticsLogScreen(screen: KanguroAnalyticsEnums.Screen) {
        loggedScreens.append(screen)
    }
}
