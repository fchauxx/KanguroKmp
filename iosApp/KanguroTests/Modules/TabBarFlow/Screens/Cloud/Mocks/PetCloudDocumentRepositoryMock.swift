import Foundation
import KanguroPetDomain
import KanguroSharedDomain
@testable import Kanguro

class PetCloudDocumentRepositoryMock: CloudDocumentRepositoryProtocol {

    typealias Item = KanguroPetDomain.Pet

    enum Methods {
        case getCloudDocuments
        case getCloudDocumentsByPolicyId
        case getClaimDocumentsByPolicyIdAndClaimId
    }
    
    var requestShouldFail: Bool = false
    var calledMethods: [Methods] = []
    
    func getCloudDocument(completion: @escaping ((Result<CloudDocument, RequestError>) -> Void)) {
        calledMethods.append(.getCloudDocuments)
        if requestShouldFail {
            completion(.failure(RequestError(errorType: .serverError, errorMessage: "serverError.default".localized)))
        } else {
            let pets: [KanguroSharedDomain.PetCloud] = [PetCloud(id: 999, name: "Pet Name", cloudDocumentPolicies: [])]
            let cloudDocument: CloudDocument = CloudDocument(userId: "id_test", pets: pets, renters: [])
            completion(.success(cloudDocument))
        }
    }

    func getCloudDocument(
        by policyId: PolicyParameters,
        completion: @escaping ((Result<CloudDocumentPolicy, RequestError>) -> Void)
    ) {
        calledMethods.append(.getCloudDocumentsByPolicyId)
        if requestShouldFail {
            completion(.failure(RequestError(errorType: .serverError, errorMessage: "serverError.default".localized)))
        } else {
            let mockedCloudDocumentPolicy = CloudDocumentPolicy(id: "777", ciId: 777, policyStartDate: nil,
                                                                policyAttachments: [
                                                                    PolicyAttachment(id: 777, name: "fileTest", fileSize: 1424)
                                                                ],
                                                                policyDocuments: [
                                                                    PolicyDocumentData(id: 777, name: "fileTest", filename: "filename_test")
                                                                ], claimDocuments: [
                                                                    ClaimDocument(claimPrefixId: "UP10000", claimId: "id_claim", claimDocuments: [Attachment(id: 777, fileName: "attachment", fileSize: 8888)])
                                                                ])
            completion(.success(mockedCloudDocumentPolicy))
        }
    }

    func getClaimDocument(
        by policyAndClaim: ClaimDocumentsParameters,
        completion: @escaping ((Result<ClaimDocument, RequestError>) -> Void)
    ) {
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
