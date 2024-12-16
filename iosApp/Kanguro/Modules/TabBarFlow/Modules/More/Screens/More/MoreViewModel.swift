import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroStorageDomain
import KanguroUserDomain
import KanguroFeatureFlagDomain
import KanguroSharedDomain

class MoreViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var keychain: SecureStorage
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var getLocalUserService: GetLocalUser
    @LazyInjected var getFeatureFlag: GetFeatureFlagBoolUseCaseProtocol
    @LazyInjected var getContactInformationService: GetContactInformationUseCaseProtocol

    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    var supportPhoneNumber = "+1-888-546-5264"
    var shouldLogout = false
    var requestError = ""
    var contactInformation: [ContactInformation] = []
    let productType: ProductType

    // MARK: - Computed Properties
    var user: User? {
        guard let user: User = try? getLocalUserService.execute().get() else { return nil }
        return user
    }
    var blogURL: URL? {
        guard let user: User = try? getLocalUserService.execute().get() else { return nil }
        return URL(string: AppURLs.blogUrl(language: user.language ?? .Spanish)) ?? nil
    }
    var privacyPolicyURL: URL? {
        guard let user: User = try? getLocalUserService.execute().get() else { return nil }
        return URL(string: AppURLs.getPrivacyPolicy(language: user.language ?? .Spanish)) ?? nil
    }

    public var shouldShowEmergencySection: Bool {
        guard let shouldShowLiveVet: Bool = try? getFeatureFlag.execute(
            key: KanguroBoolFeatureFlagKeys.shouldShowLiveVet
        ) else { return false }

        let userHasPetPolicy = self.productType != .rentersProduct

        return shouldShowLiveVet && userHasPetPolicy
    }

    public init(productType: ProductType) {
        self.productType = productType
    }
}

// MARK: - Analytics
extension MoreViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .More)
    }
}

// MARK: - Public Methods
extension MoreViewModel {
    
    func logout() {
        keychain.cleanAll()
        shouldLogout = true
        state = .dataChanged
    }
    
    func callSupport() {
        guard let url = URL(string: "tel://\(supportPhoneNumber)"),
              UIApplication.shared.canOpenURL(url) else { return }
        UIApplication.shared.open(url)
    }
}

// MARK: - Network
extension MoreViewModel {

    func getContactInformation() {
        state = .loading
        getContactInformationService.execute { [weak self] response in
            guard let self else { return }

            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let contactInformation):
                self.contactInformation = contactInformation
                self.state = .dataChanged
            }
        }
    }
}
