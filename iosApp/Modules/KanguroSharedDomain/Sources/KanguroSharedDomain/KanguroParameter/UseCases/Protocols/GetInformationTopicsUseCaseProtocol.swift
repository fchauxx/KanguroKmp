import Foundation

public protocol GetInformationTopicsUseCaseProtocol {
    
    func execute(parameters: KanguroParameterModuleParameters,
                 completion: @escaping ((Result<[InformerData], RequestError>) -> Void))
}
