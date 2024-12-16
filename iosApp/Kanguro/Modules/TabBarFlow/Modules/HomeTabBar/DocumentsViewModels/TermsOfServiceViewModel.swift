import UIKit
import Resolver
import Combine
import KanguroSharedDomain
import KanguroStorageDomain

class TermsOfServiceViewModel: PDFViewModelProtocol {
    
    // MARK: - Published Properties
    var statePublisher: Published<DefaultViewState>.Publisher { $state }
    @Published var state: DefaultViewState = .started
    
    // MARK: - Dependencies
    @LazyInjected var getTermsService: GetTermsOfServiceUseCaseProtocol
    @LazyInjected var userDefaults: Storage
    
    // MARK: - Stored Properties
    var data: Data?
    var requestError = ""
    
    // MARK: - Computed Properties
    var language: Language {
        guard let savedLanguage: String = userDefaults.get(key: "preferredLanguage") else {
            return .English
        }
        return Language(rawValue: savedLanguage) ?? .English
    }
    
    // MARK: - Initializers
    init() {
        self.getTermsOfService()
    }
}

// MARK: - Network
extension TermsOfServiceViewModel {
    
    private func getTermsOfService() {
        state = .loading
        let parameters = TermsOfServiceParameters(preferencialLanguage: language.title)
        getTermsService.execute(parameters: parameters) { [weak self] response in
            guard let self = self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let termsOfService):
                self.data = termsOfService
                self.state = .requestSucceeded
            }
        }
    }
}
