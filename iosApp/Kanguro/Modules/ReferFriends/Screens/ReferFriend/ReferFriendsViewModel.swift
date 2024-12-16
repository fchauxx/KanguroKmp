import UIKit
import Resolver
import KanguroAnalyticsDomain
import KanguroStorageDomain
import KanguroUserDomain
import KanguroSharedDomain

class ReferFriendsViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var keychain: SecureStorage
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var getLocalUserService: GetLocalUser
    @LazyInjected var getTermOfServiceService: GetTermsOfServiceUseCaseProtocol
    
    // MARK: - Computed Properties
    var referralCode: String? {
        guard let user: User = try? getLocalUserService.execute().get() else { return nil }
        return user.referralCode
    }
    var username: String? {
        guard let user: User = try? getLocalUserService.execute().get() else { return nil }
        return user.givenName
    }
    var getTermOfService: URL? {
        guard let user: User = try? getLocalUserService.execute().get(), 
                let userLanguage = user.language else { return nil }
        return AppURLs.getReferFriendsTermOfService(language: userLanguage)
    }
}

// MARK: - Analytics
extension ReferFriendsViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .ReferFriends)
    }
}
