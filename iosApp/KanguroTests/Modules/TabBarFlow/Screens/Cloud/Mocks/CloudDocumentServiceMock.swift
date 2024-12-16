import Foundation
@testable import Kanguro
import KanguroSharedData
import KanguroSharedDomain
import KanguroNetworkDomain
import KanguroPetDomain

class CloudDocumentServiceMock {

    enum Methods {
        case getCloudDocuments
        case getCloudDocumentsByPolicyId
        case getClaimDocumentsByPolicyIdAndClaimId
    }

    var requestShouldFail: Bool = false
    var calledMethods: [Methods] = []
}

extension CloudDocumentServiceMock: GetCloudDocumentUseCaseProtocol {
    func execute(completion: @escaping ((Result<CloudDocument, RequestError>) -> Void)) {
        calledMethods.append(.getCloudDocuments)
        if requestShouldFail {
            completion(.failure(RequestError(errorType: .serverError, errorMessage: "serverError.default".localized)))
        } else {
            completion(
                .success(
                    CloudDocument(
                        userId: "id_test",
                        pets: [PetCloud(id: 999, name: "Pet Name", cloudDocumentPolicies: [])],
                        renters: []
                    )
                )
            )
        }

    }
}

extension CloudDocumentServiceMock: GetCloudDocumentsByPolicyUseCaseProtocol {
    func execute(_ parameters: PolicyParameters, completion: @escaping ((Result<CloudDocumentPolicy, RequestError>) -> Void)) {
        calledMethods.append(.getCloudDocumentsByPolicyId)
        if requestShouldFail {
            completion(.failure(RequestError(errorType: .serverError, errorMessage: "serverError.default".localized)))
        } else {
            let mockedCloudDocumentPolicy = CloudDocumentPolicy(
                id: "777", ciId: 777, policyStartDate: nil,
                policyAttachments: [PolicyAttachment(id: 777, name: "fileTest", fileSize: 1424)],
                policyDocuments: [PolicyDocumentData(id: 777, name: "fileTest", filename: "filename_test")],
                claimDocuments: [ClaimDocument(claimPrefixId: "UP10000", claimId: "id_claim", claimDocuments: [Attachment(id: 777, fileName: "attachment", fileSize: 8888)])]
            )
            completion(.success(mockedCloudDocumentPolicy))
        }
    }
}

extension CloudDocumentServiceMock: GetClaimDocumentByPolicyAndClaimUseCaseProtocol {
    func execute(_ parameters: ClaimDocumentsParameters, completion: @escaping ((Result<ClaimDocument, RequestError>) -> Void)) {

        calledMethods.append(.getClaimDocumentsByPolicyIdAndClaimId)
        if requestShouldFail {
            completion(.failure(RequestError(errorType: .serverError, errorMessage: "serverError.default".localized)))
        } else {
            let mockedClaimDocument = ClaimDocument(claimPrefixId: "UP10000",
                                                    claimId: "id_claim",
                                                    claimDocuments: [Attachment(id: 777, fileName: "attachment", fileSize: 8888)])
            completion(.success(mockedClaimDocument))
        }

    }
}
