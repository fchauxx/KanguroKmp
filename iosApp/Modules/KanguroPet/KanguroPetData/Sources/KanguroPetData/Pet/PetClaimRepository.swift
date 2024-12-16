import Foundation
import KanguroSharedDomain
import KanguroSharedData
import KanguroNetworkDomain
import KanguroPetDomain

public final class PetClaimRepository: PetClaimRepositoryProtocol {
    
    let network: NetworkProtocol

    public init(network: NetworkProtocol) {
        self.network = network
    }

    public func getClaims(completion: @escaping ((Result<[PetClaim], RequestError>) -> Void)) {
        network.request(endpoint: ClaimModuleEndpoint.claims,
                        method: .GET,
                        responseType: [RemotePetClaim].self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: PetClaimsMapper(), response: response, completion: completion)
        }
    }

    public func getClaim(_ parameters: PetClaimParameters, 
                         completion: @escaping ((Result<PetClaim, RequestError>) -> Void)) {
        network.request(endpoint: ClaimModuleEndpoint.claim(id: parameters.id),
                        method: .GET,
                        responseType: RemotePetClaim.self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: PetClaimMapper(), response: response, completion: completion)
        }
    }

    public func getClaimAttachments(_ parameters: PetClaimParameters, 
                                    completion: @escaping ((Result<[Attachment], RequestError>) -> Void)) {
        network.request(endpoint: ClaimModuleEndpoint.attachments(claimId: parameters.id),
                        method: .GET,
                        responseType: [RemoteAttachment].self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: AttachmentsMapper(), response: response, completion: completion)
        }
    }

    public func getClaimAttachment(_ parameters: PetClaimAttachmentsParameters, 
                                   completion: @escaping ((Result<Data, RequestError>) -> Void)) {
        network.download(
            endpoint: ClaimModuleEndpoint.attachment(
                claimId: parameters.claimId,
                attachmentId: parameters.attachment.id
            ),
            fileName: parameters.attachment.fileName.getPrefixBeforeDot ?? "TemporaryPDF",
            fileExt: parameters.attachment.fileName.getFileExtension ?? "pdf",
            errorType: NetworkRequestError.self
        ) { response in
            switch response {
            case .success(let receivedData):
                completion(.success(receivedData))
            case .customError(let error), .failure(let error):
                guard let status = error.statusCode else { 
                    return completion(.failure(RequestError(errorType: .undefined,
                                                            errorMessage: error.error)))
                }
                switch status {
                case 400...499: return completion(.failure(RequestError(errorType: .invalidRequest,
                                                                        errorMessage: error.error)))
                case 500...599: return completion(.failure(RequestError(errorType: .serverError,
                                                                        errorMessage: error.error)))
                default: return completion(.failure(RequestError(errorType: .undefined,
                                                                 errorMessage: error.error)))
                }
            }
        }
    }

    public func updateFeedback(_ parameters: PetClaimParameters, feedback: PetFeedbackDataParameters, completion: @escaping ((Result<Void, RequestError>) -> Void)) {
        network.request(
            endpoint: ClaimModuleEndpoint.feedback(
                claimId: parameters.id
            ),
            method: .PUT,
            parameters: RemoteFeedbackDataParameters(
                feedbackRate: feedback.feedbackRate,
                feedbackDescription: feedback.feedbackDescription
            ),
            errorType: NetworkRequestError.self
        ) { response in
            switch response {
            case .success:
                completion(.success(()))
            case .customError(let error), .failure(let error):
                guard let status = error.statusCode else {
                    return completion(.failure(RequestError(errorType: .undefined,
                                                            errorMessage: error.error)))
                }
                switch status {
                case 400...499: return completion(.failure(RequestError(errorType: .invalidRequest, 
                                                                        errorMessage: error.error)))
                case 500...599: return completion(.failure(RequestError(errorType: .serverError, 
                                                                        errorMessage: error.error)))
                default: return completion(.failure(RequestError(errorType: .undefined, 
                                                                 errorMessage: error.error)))
                }
            }
        }
    }

    public func getCommunications(_ parameters: PetClaimParameters, completion: @escaping ((Result<[Communication], RequestError>) -> Void)) {
        network.request(
            endpoint: ClaimModuleEndpoint.communications(
                claimId: parameters.id
            ),
            method: .GET,
            responseType: [RemoteCommunication].self,
            errorType: NetworkRequestError.self) { response in
                ResponseHandler.handle(mapper: CommunicationsMapper(), 
                                       response: response,
                                       completion: completion)
            }
    }

    public func createCommunications(_ parameters: PetCommunicationParameters, completion: @escaping ((Result<[Communication], RequestError>) -> Void)) {
        guard let id = parameters.id else {
            completion(.failure(RequestError(errorType: .invalidRequest, 
                                             errorMessage: "Invalid Request")))
            return
        }
        network.request(
            endpoint: ClaimModuleEndpoint.communications(
                claimId: id
            ),
            method: .POST,
            parameters: RemoteCommunicationParameters(
                id: id,
                message: parameters.message,
                files: parameters.files
            ),
            responseType: [RemoteCommunication].self,
            errorType: NetworkRequestError.self) { response in
                ResponseHandler.handle(mapper: CommunicationsMapper(), 
                                       response: response,
                                       completion: completion)
            }
    }

    public func createDocuments(
        _ parameters: UploadPetAttachmentParameters,
        completion: @escaping ((Result<[Int], RequestError>) -> Void)
    ) {
        guard let remoteInputTypes = RemoteClaimFileInputType(rawValue: parameters.fileInputType.rawValue) else  {
            completion(.failure(RequestError(errorType: .invalidRequest, 
                                             errorMessage: "Invalid Request")))
            return
        }
        network.request(
            endpoint: ClaimModuleEndpoint.postUploadDocuments,
            method: .POST,
            parameters: RemoteUploadAttachmentParameters(
                files: parameters.files,
                fileInputType: remoteInputTypes
            ),
            responseType: [Int].self,
            errorType: NetworkRequestError.self
        ) { response in
            switch response {
            case .success(let ids):
                completion(.success(ids))
            case .failure(let error), .customError(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            }
        }
    }

    public func createClaim(_ parameters: NewPetClaimParameters, completion: @escaping ((Result<PetClaim, RequestError>) -> Void)) {
        guard let type = RemoteClaimType(rawValue: parameters.type.rawValue),
              let process = RemoteReimbursementProcessType(rawValue: parameters.reimbursementProcess.rawValue),
              let invoiceDate = parameters.invoiceDate.UTCFormat
        else {
            completion(.failure(RequestError(errorType: .invalidRequest, 
                                             errorMessage: "Invalid Request")))
            return
        }
        
        
        network.request(endpoint: ClaimModuleEndpoint.postClaims,
                        method: .POST,
                        parameters: RemoteNewPetClaimParameters(
                            description: parameters.description,
                            invoiceDate: invoiceDate,
                            amount: parameters.amount,
                            petId: parameters.petId,
                            type: type,
                            pledgeOfHonorId: parameters.pledgeOfHonorId,
                            reimbursementProcess: process,
                            documentIds: parameters.documentIds
                        ),
                        responseType: RemotePetClaim.self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: PetClaimMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }
}
