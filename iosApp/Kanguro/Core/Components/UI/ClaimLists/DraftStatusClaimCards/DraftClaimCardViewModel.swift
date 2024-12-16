import UIKit
import Resolver
import Combine
import KanguroSharedDomain
import KanguroPetDomain

class DraftClaimCardViewModel {
    
    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    var claim: PetClaim
    
    // MARK: - Computed Properties
    var petName: String? {
        return claim.pet?.name ?? nil
    }
    
    // MARK: - Initializers
    init(claim: PetClaim) {
        self.claim = claim
    }
}
