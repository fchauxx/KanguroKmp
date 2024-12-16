import Foundation
@testable import Kanguro
import KanguroSharedData
import KanguroSharedDomain
import KanguroNetworkDomain
import KanguroPetDomain

class ClaimsServiceMock {

    enum Methods {
        case getClaims
        case getClaim
        case getClaimAttachments
        case getClaimAttachment
        case putFeedback
        case getCommunications
        case postCommunications
        case postClaims
        case postUploadDocuments
    }

    var requestShouldFail: Bool = false
    var calledMethods: [Methods] = []
}

extension ClaimsServiceMock: GetPetClaimsUseCaseProtocol {

    func execute(completion: @escaping ((Result<[PetClaim], RequestError>) -> Void)) {
        calledMethods.append(.getClaims)
        if requestShouldFail {
            completion(.failure(RequestError(errorType: .serverError, errorMessage: "serverError.default".localized)))
        } else {
            completion(.success([PetClaim(id: "id_test"), PetClaim(id: "id_test_2")]))
        }
    }
}

extension ClaimsServiceMock: GetPetClaimUseCaseProtocol {
    func execute(_ parameters: PetClaimParameters, completion: @escaping ((Result<PetClaim, RequestError>) -> Void)) {
        calledMethods.append(.getClaim)
        if requestShouldFail {
            completion(.failure(RequestError(errorType: .serverError, errorMessage: "serverError.default".localized)))
        } else {
            completion(.success(PetClaim(id: "id_test")))
        }
    }
}

extension ClaimsServiceMock: GetPetClaimAttachmentsUseCaseProtocol {
    func execute(_ parameters: PetClaimParameters, completion: @escaping ((Result<[Attachment], RequestError>) -> Void)) {
        calledMethods.append(.getClaimAttachments)
        if requestShouldFail {
            completion(.failure(RequestError(errorType: .serverError, errorMessage: "serverError.default".localized)))
        } else {
            completion(.success([Attachment(id: 777, fileName: "file_test", fileSize: 12345)]))
        }
    }
}
extension ClaimsServiceMock: GetPetClaimAttachmentUseCaseProtocol {
    func execute(_ parameters: PetClaimAttachmentsParameters, completion: @escaping ((Result<Data, RequestError>) -> Void)) {
        calledMethods.append(.getClaimAttachment)
        if requestShouldFail {
            completion(.failure(RequestError(errorType: .serverError, errorMessage: "serverError.default".localized)))
        } else {
            completion(.success(Data()))
        }

    }
}

extension ClaimsServiceMock: PetUpdateFeedbackUseCaseProtocol {
    func execute(_ parameters: PetClaimParameters, feedback: PetFeedbackDataParameters, completion: @escaping ((Result<Void, RequestError>) -> Void)) {

    }
}

extension ClaimsServiceMock: GetPetCommunicationsUseCaseProtocol {
    func execute(_ parameters: PetClaimParameters, completion: @escaping ((Result<[Communication], RequestError>) -> Void)) {

    }
}

extension ClaimsServiceMock: CreatePetCommunicationsUseCaseProtocol {
    func execute(_ parameters: PetCommunicationParameters, completion: @escaping ((Result<[Communication], RequestError>) -> Void)) {

    }
}

extension ClaimsServiceMock: CreatePetClaimUseCaseProtocol {
    func execute(parameters: NewPetClaimParameters, completion: @escaping ((Result<PetClaim, RequestError>) -> Void)) {
        calledMethods.append(.postClaims)
        if requestShouldFail {
            completion(.failure(RequestError(errorType: .serverError, errorMessage: "serverError.default".localized)))
        } else {
            completion(.success(PetClaim(id: "")))
        }
    }
}

extension ClaimsServiceMock: CreatePetDocumentsUseCaseProtocol {
    func execute(_ parameters: UploadPetAttachmentParameters, completion: @escaping ((Result<[Int], RequestError>) -> Void)) {
        calledMethods.append(.postUploadDocuments)
        if requestShouldFail {
            completion(.failure(RequestError(errorType: .serverError, errorMessage: "serverError.default".localized)))
        } else {
            completion(.success([2,3,1]))
        }
    }
}
