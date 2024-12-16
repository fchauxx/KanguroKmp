import UIKit
import Resolver
import Combine
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroPetDomain

class ClaimsViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var getClaimsService: GetPetClaimsUseCaseProtocol
    
    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    var requestError: String = ""
    var claims: [PetClaim]? = []
    var isFirstRequest: Bool = true
    
    // MARK: - Computed Properties
    var openClaims: [PetClaim]? {
        guard let claims = claims else { return nil }
        return claims.filter { $0.isOpenStatus }
    }
    var closedClaims: [PetClaim]? {
        guard let claims = claims else { return nil }
        return claims.filter { $0.isClosedStatus }
    }
    var draftClaims: [PetClaim]? {
        guard let claims = claims else { return nil }
        return claims.filter { $0.status == .Draft }
    }
}

// MARK: - Analytics
extension ClaimsViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .Claims)
    }
}

// MARK: Network
extension ClaimsViewModel {
    
    func getClaims() {
        state = .loading
        DispatchQueue.global(qos: .userInitiated).async {
            self.getClaimsService.execute { response in
                DispatchQueue.main.async {
                    switch response {
                    case .failure(let error):
                        self.requestError = error.errorMessage ?? "serverError.default".localized
                        self.state = .requestFailed
                    case .success(let claims):
                        self.claims = claims
                        self.isFirstRequest = false
                        self.state = .requestSucceeded
                    }
                }
            }
        }
    }
}
