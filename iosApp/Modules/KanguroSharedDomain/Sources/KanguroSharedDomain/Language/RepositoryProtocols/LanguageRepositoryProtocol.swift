import Foundation

public protocol LanguageRepositoryProtocol {
    
    func updateAppLanguage(parameters: LanguageParameters,
                           completion: @escaping ((Result<Void, RequestError>) -> Void))
}
