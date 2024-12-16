import Foundation

public final class UpdateAppLanguage: UpdateAppLanguageUseCaseProtocol {

    private let languageRepo: LanguageRepositoryProtocol
    
    public init(languageRepo: LanguageRepositoryProtocol) {
        self.languageRepo = languageRepo
    }
    
    public func execute(parameters: LanguageParameters, completion: @escaping ((Result<Void, RequestError>) -> Void)) {
        languageRepo.updateAppLanguage(parameters: parameters) { result in
            completion(result)
        }
    }
}
