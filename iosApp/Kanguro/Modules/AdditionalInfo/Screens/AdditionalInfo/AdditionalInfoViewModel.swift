import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroStorageDomain
import KanguroUserDomain
import KanguroPetDomain

enum AdditionalInfoViewState {
    
    case started
    case dataChanged
    case finishedAllPets
}

class AdditionalInfoViewModel {
    
    // MARK: - Published Properties
    @Published var state: AdditionalInfoViewState = .started
    
    // MARK: - Dependencies
    @LazyInjected var getLocalUserService: GetLocalUser
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var keychain: SecureStorage

    // MARK: - Stored Properties
    var pets: [Pet]
    
    // MARK: - Computed Properties
    var currentPet: Pet? {
        return pets.first ?? nil
    }
    var user: User? {
        guard let user: User = try? getLocalUserService.execute().get() else { return nil }
        return user
    }
    var isUserDonating: Bool {
        return user?.donation != nil
    }
    
    // MARK: - Initializers
    init(pets: [Pet]) {
        self.pets = pets
    }
}

// MARK: - Analytics
extension AdditionalInfoViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .AdditionalInfo)
    }
}

// MARK: - Public Methods
extension AdditionalInfoViewModel {
    
    func removeCompletedPet() {
        pets.remove(at: 0)
        state = pets.isEmpty ? .finishedAllPets : .dataChanged
    }
}
