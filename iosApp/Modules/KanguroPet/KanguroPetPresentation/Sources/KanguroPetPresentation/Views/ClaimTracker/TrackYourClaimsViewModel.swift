import Foundation
import Resolver
import Combine
import KanguroAnalyticsDomain
import KanguroPetDomain
import KanguroSharedDomain


public class TrackYourClaimsViewModel: ObservableObject {
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var getClaimsService: GetPetClaimsUseCaseProtocol
    
    @Published public var isLoading = true
    @Published public var requestFailed: String? = nil
    @Published public var petClaims: [PetClaim] = []

    public init() {

    }

    public func fetchClaims() {
        self.isLoading = true
        self.getClaimsService.execute { response in
            switch response {
            case .failure(let error):
                self.isLoading = false
                self.requestFailed = error.errorMessage ?? "serverError.default".localized()
            case .success(let claims):
                self.isLoading = false
                self.petClaims = claims
            }
        }
    }
}


// MARK: - Analytics
public extension TrackYourClaimsViewModel {
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .Claims)
    }
}

extension TrackYourClaimsViewModel {
    func tap(block: (TrackYourClaimsViewModel) -> Void) -> Self {
        block(self)
        return self
    }
}
