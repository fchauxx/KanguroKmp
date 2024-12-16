import Foundation

public final class GetInformationTopics: GetInformationTopicsUseCaseProtocol  {

    private let kanguroParameterRepo: KanguroParameterRepositoryProtocol
    
    public init(kanguroParameterRepo: KanguroParameterRepositoryProtocol) {
        self.kanguroParameterRepo = kanguroParameterRepo
    }
    
    public func execute(parameters: KanguroParameterModuleParameters,
                        completion: @escaping ((Result<[InformerData], RequestError>) -> Void)) {
        kanguroParameterRepo.getInformationTopics(parameters: parameters) { result in
            completion(result)
        }
    }
}
