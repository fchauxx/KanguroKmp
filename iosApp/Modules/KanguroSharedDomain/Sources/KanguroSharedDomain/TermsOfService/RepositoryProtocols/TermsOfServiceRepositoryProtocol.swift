import Foundation

public protocol TermsOfServiceRepositoryProtocol {
    
    func getTermsOfService(parameters: TermsOfServiceParameters,
                           completion: @escaping((Result<Data, RequestError>) -> Void))
}
