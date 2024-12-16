import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroStorageDomain
import KanguroUserDomain
import KanguroSharedDomain

class DonationCauseViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var getLocalUserService: GetLocalUser
    @LazyInjected var updateLocalUserService: UpdateLocalUser
    @LazyInjected var keychain: SecureStorage
    @LazyInjected var patchCharityService: PatchCharityUseCaseProtocol
    
    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    var type: DonationType
    var donationCauses: [DonationCause]
    var requestError = ""
    
    // MARK: - Computed Properties
    var user: User? {
        guard let user: User = try? getLocalUserService.execute().get() else { return nil }
        return user
    }
    
    // MARK: - Initializers
    init(type: DonationType, donationCauses: [DonationCause]) {
        self.type = type
        self.donationCauses = donationCauses
    }
}

// MARK: - Analytics
extension DonationCauseViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .DonationCause)
    }
}

// MARK: - Network
extension DonationCauseViewModel {
    
    func patchUserDonationCause(userId: String, charityId: Int, title: String, cause: DonationType) {
        
        let body = UserDonationCause(userId: userId,
                                     charityId: charityId,
                                     title: title,
                                     cause: cause)
        patchCharityService.execute(body) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success:
                guard var user: User = try? getLocalUserService.execute().get() else {
                    assertionFailure("Could not get local user")
                    self.state = .requestFailed
                    return
                }
                user.donation = body
                updateLocalUserService.execute(user) { result in
                    guard let _ = try? result.get() else {
                        assertionFailure("Could not save local user")
                        self.state = .requestFailed
                        return
                    }
                    self.state = .requestSucceeded
                }
            }
        }
    }
}

