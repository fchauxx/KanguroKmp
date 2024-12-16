import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public class TermsOfServiceRepository: TermsOfServiceRepositoryProtocol {

    // MARK: - Stored Properties
    private let network: NetworkProtocol
    private var fileName: KanguroFile = KanguroFile(name: .termOfService, ext: .pdf)
    
    public init(network: NetworkProtocol) {
        self.network = network
    }
    
    public func getTermsOfService(parameters: TermsOfServiceParameters,
                                  completion: @escaping ((Result<Data, RequestError>) -> Void)) {

        network.download(endpoint: TermsOfServiceModuleEndpoint.termsOfService(language: parameters.preferencialLanguage),
                         fileName: fileName.name.title,
                         fileExt: fileName.ext.fileType,
                         errorType: NetworkRequestError.self) { response in
            switch response {
            case .success(let data):
                completion(.success(data))
            case .failure(let error), .customError(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            }
        }
    }
}
