import Foundation

public protocol UpdateAppLanguageUseCaseProtocol {
    
    func execute(parameters: LanguageParameters,
                 completion: @escaping ((Result<Void, RequestError>) -> Void))
}
