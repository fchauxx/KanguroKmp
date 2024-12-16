import Foundation

public protocol KanguroParameterRepositoryProtocol {
    
    func getInformationTopics(parameters: KanguroParameterModuleParameters,
                              completion: @escaping ((Result<[InformerData], RequestError>) -> Void))
}
