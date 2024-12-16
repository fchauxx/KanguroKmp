import FirebaseAnalytics
import KanguroAnalyticsDomain

public final class FirebaseAnalyticsModule: KanguroAnalyticsModuleProtocol {

    public init() {}

    // MARK: - Public Methods
    public func analyticsLogScreen(screen: KanguroAnalyticsEnums.Screen) {
        Analytics.logEvent(AnalyticsEventScreenView,
                           parameters: [AnalyticsParameterScreenName: screen.value])
    }
}
