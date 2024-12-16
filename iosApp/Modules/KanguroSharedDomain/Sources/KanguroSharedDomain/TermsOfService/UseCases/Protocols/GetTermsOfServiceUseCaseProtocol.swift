import Foundation

public protocol GetTermsOfServiceUseCaseProtocol {
    
    func execute(parameters: TermsOfServiceParameters,
                 completion: @escaping ((Result<Data, RequestError>) -> Void))
}
