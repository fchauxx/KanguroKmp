import Foundation
import KanguroPetDomain
import KanguroSharedDomain
import KanguroNetworkDomain

// MARK: - Direct Pay to Vets Endpoints
extension PetClaimRepository {

    public func createPetVetDirectPaymentClaim(_ parameters: PetVetDirectPaymentParameters,
                                               completion: @escaping ((Result<PetClaim, RequestError>) -> Void)) {
        guard let remotePetId = parameters.petId,
              let claimType = parameters.type,
              let remoteClaimType = RemoteClaimType(rawValue: claimType.rawValue),
              let remoteInvoiceDate = parameters.invoiceDate?.UTCFormat,
              let remoteVeterinarianEmail = parameters.veterinarianEmail,
              let remoteVeterinarianName = parameters.veterinarianName,
              let remoteVeterinarianClinic = parameters.veterinarianClinic,
              let remoteDescription = parameters.description,
              let remoteAmount = parameters.amount,
              let remotePledgeOfHonor = parameters.pledgeOfHonor,
              let remotePledgeOfHonorExtension = parameters.pledgeOfHonorExtension
        else {
            completion(.failure(RequestError(errorType: .invalidRequest, errorMessage: "Invalid Request")))
            return
        }
        if remoteDescription.isEmpty {
            completion(.failure(RequestError(errorType: .invalidRequest, errorMessage: "Invalid Request")))
            return
        }
        network.request(endpoint: ClaimModuleEndpoint.createPetVetDirectPaymentClaim,
                        method: .POST,
                        parameters: RemotePetVetDirectPaymentParameters(petId: remotePetId,
                                                                        type: remoteClaimType,
                                                                        invoiceDate: remoteInvoiceDate,
                                                                        veterinarianEmail: remoteVeterinarianEmail,
                                                                        veterinarianName: remoteVeterinarianName,
                                                                        veterinarianClinic: remoteVeterinarianClinic,
                                                                        description: remoteDescription,
                                                                        amount: remoteAmount,
                                                                        veterinarianId: parameters.veterinarianId,
                                                                        pledgeOfHonor: remotePledgeOfHonor,
                                                                        pledgeOfHonorExtension: remotePledgeOfHonorExtension),
                        responseType: RemotePetClaim.self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: PetClaimMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }

    public func createDirectPaymentVeterinarianSignature(claimId: String,
                                                         parameters: UploadedDocumentParameters,
                                                         completion: @escaping ((Result<Void, RequestError>) -> Void)) {
        network.request(endpoint: ClaimModuleEndpoint.createDirectPaymentVetSignature(claimId: claimId),
                        method: .POST,
                        parameters: RemoteUploadedDocumentParameters(fileIds: parameters.fileIds),
                        errorType: NetworkRequestError.self) { response in
                switch response {
                case .success:
                    completion(.success(()))
                case .failure(let error), .customError(let error):
                    completion(.failure(RequestErrorMapper.map(error)))
                }
        }
    }

    public func getDirectPaymentPreSignedFileURL(claimId: String,
                                                 completion: @escaping ((Result<String, RequestError>) -> Void)) {
        network.request(endpoint: ClaimModuleEndpoint.getDirectPaymentPreSignedFileUrl(claimId: claimId),
                        method: .GET,
                        responseStringUse: true,
                        responseType: String.self,
                        errorType: NetworkRequestError.self) { response in
            switch response {
            case .success(let fileUrl):
                completion(.success(fileUrl))
            case .failure(let error), .customError(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            }
        }
    }
}
