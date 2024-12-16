import UIKit
import Combine
import KanguroPetDomain
import Resolver

class SearchVetViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var getVeterinariansService: GetVeterinariansUseCaseProtocol
    
    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    var textFieldInput: String
    var invalidInput: Bool
    var vetDataList: [Veterinarian]?
    var requestError: String

    // MARK: - Computed Properties
    var isValidEmail: Bool {
        return textFieldInput.isValidEmail
    }
    
    init(textFieldInput: String = "",
         invalidInput: Bool = false,
         vetDataList: [Veterinarian]? = nil,
         requestError: String = "") {
        self.textFieldInput = textFieldInput
        self.invalidInput = invalidInput
        self.vetDataList = vetDataList
        self.requestError = requestError
    }
}

// MARK: Network
extension SearchVetViewModel {
    
    func getVeterinarians() {
        state = .loading
        getVeterinariansService.execute { [weak self] response in
            guard let self = self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let vetDataList):
                self.vetDataList = vetDataList
                self.state = .requestSucceeded
            }
        }
    }
}
