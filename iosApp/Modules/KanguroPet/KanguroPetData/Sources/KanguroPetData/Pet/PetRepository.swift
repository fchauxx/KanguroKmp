import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain
import KanguroPetDomain

public class PetRepository: PetRepositoryProtocol {

    private let network: NetworkProtocol

    public init(network: NetworkProtocol) {
        self.network = network
    }

    public func getPets(completion: @escaping ((Result<[Pet], RequestError>) -> Void)) {
        network.request(
            endpoint: PetModuleEndpoint.getPets,
            method: .GET,
            responseType: [RemotePet].self,
            errorType: NetworkRequestError.self
        ) { response in
            ResponseHandler.handle(mapper: PetsMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }

    public func getPet(_ parameters: GetPetParameters, 
                       completion: @escaping ((Result<Pet, RequestError>) -> Void)) {
        network.request(
            endpoint: PetModuleEndpoint.getPet(id: parameters.id),
            method: .GET,
            responseType: RemotePet.self,
            errorType: NetworkRequestError.self
        ) { response in
            ResponseHandler.handle(mapper: PetMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }

    public func updatePetPicture(_ parameters: UpdatePetPictureParameters, completion: @escaping ((Result<Void,RequestError>) -> Void)) {

        let codablePicBase64 =  PetPictureBase64(
            fileInBase64: parameters.petPictureBase64.fileInBase64,
            fileExtension: parameters.petPictureBase64.fileExtension
        )
        let codableParams = RemotePutPetPictureParameters(
            petId: parameters.petId,
            petPictureBase64: codablePicBase64
        )

        network.request(
            endpoint: PetModuleEndpoint.putPetPicture,
            method: .PUT,
            parameters: codableParams,
            errorType: NetworkRequestError.self
        ) { response in
            switch response {
            case .success:
                completion(.success(()))
            case .failure(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            case .customError(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            }
        }
    }
}
