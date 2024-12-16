import UIKit
import Resolver
import Combine
import KanguroSharedDomain
import KanguroSharedData
import KanguroPetDomain

class ReminderCardCellViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var getPetService: GetPetUseCaseProtocol
    
    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    var requestError: String = ""
    var reminder: KanguroSharedDomain.Reminder
    var petId: Int?
    var pet: Pet?
    
    // MARK: - Computed Properties
    var petName: String? {
        return pet?.name ?? ""
    }
    
    // MARK: - Initializers
    init(reminder: KanguroSharedDomain.Reminder) {
        self.reminder = reminder
    }
}

// MARK: Network
extension ReminderCardCellViewModel {
    
    func getPet() {
        state = .loading
        guard let petId = reminder.petId else { return }
        let parameters = GetPetParameters(id: petId)
        getPetService.execute(parameters: parameters) { [weak self] response in
            guard let self = self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let pet):
                self.pet = pet
                self.state = .requestSucceeded
            }
        }
    }
}
