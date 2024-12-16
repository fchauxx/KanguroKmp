import Foundation
import KanguroFeatureFlagDomain
import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroStorageDomain
import KanguroSharedDomain

public class RentersChatViewModel: ObservableObject {

    // MARK: - Dependencies
    @LazyInjected var keychain: SecureStorage
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var getContactInformationService: GetContactInformationUseCaseProtocol
    @LazyInjected var getFeatureFlag: GetFeatureFlagBoolUseCaseProtocol

    enum RentersChatViewState {
        case started
        case loading
        case requestFailed
        case requestSucceeded
    }

    // MARK: - Published Properties
    @Published var state: RentersChatViewState = .started

    // MARK: - Stored Properties
    var requestError = ""
    var contactInformation: [ContactInformation] = []
    
   public var shouldShowLiveVet: Bool {
        guard let shouldShowLiveVet: Bool = try? getFeatureFlag.execute(key: KanguroBoolFeatureFlagKeys.shouldShowLiveVet) else { return false }
        return shouldShowLiveVet
    }

    public init() { }

}

// MARK: - Analytics
extension RentersChatViewModel {

    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .More)
    }
}

// MARK: - Network
extension RentersChatViewModel {

    func getContactInformation() {
        state = .loading
        getContactInformationService.execute { [weak self] response in
            guard let self else { return }

            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized()
                self.state = .requestFailed
            case .success(let contactInformation):
                self.contactInformation = contactInformation
                self.state = .requestSucceeded
            }
        }
    }
}
