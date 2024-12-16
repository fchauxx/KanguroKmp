import XCTest
import Resolver
@testable import Kanguro
import KanguroSharedDomain
import KanguroPetDomain
import KanguroPetData

final class CloudPolicyFilesViewModelTests: XCTestCase {
    
    var sut_claim: CloudPolicyFilesViewModel!
    var sut_medicalHistory: CloudPolicyFilesViewModel!
    var sut_policyDocuments: CloudPolicyFilesViewModel!
    
    var cloud_serviceMock: CloudDocumentServiceMock!
    var claims_serviceMock: ClaimsServiceMock!
    var getPolicyAttachmentService: GetPolicyAttachmentUseCaseMock!

    override func setUp() {
        sut_claim = CloudPolicyFilesViewModel(selectedCloud: SelectedCloud(id: "pet_id", name: "Pet Name", cloudDocumentPolicies: []), policyId: "id_test", option: .claimDocumentsAndInvoices)
        sut_medicalHistory = CloudPolicyFilesViewModel(selectedCloud: SelectedCloud(id: "pet_id", name: "Pet Name", cloudDocumentPolicies: []), policyId: "id_test", option: .medicalHistory)
        sut_policyDocuments = CloudPolicyFilesViewModel(selectedCloud: SelectedCloud(id: "pet_id", name: "Pet Name", cloudDocumentPolicies: []), policyId: "id_test", option: .policyDocuments)

        cloud_serviceMock = CloudDocumentServiceMock()
        claims_serviceMock = ClaimsServiceMock()
        getPolicyAttachmentService = GetPolicyAttachmentUseCaseMock()
        Resolver.register { self.cloud_serviceMock as (any GetCloudDocumentUseCaseProtocol) }
        Resolver.register { self.cloud_serviceMock as GetCloudDocumentsByPolicyUseCaseProtocol }
        Resolver.register { self.cloud_serviceMock as GetClaimDocumentByPolicyAndClaimUseCaseProtocol }
        Resolver.register { self.claims_serviceMock as CreatePetClaimUseCaseProtocol }
        Resolver.register { self.claims_serviceMock as CreatePetCommunicationsUseCaseProtocol }
        Resolver.register { self.claims_serviceMock as CreatePetClaimUseCaseProtocol }
        Resolver.register { self.claims_serviceMock as CreatePetCommunicationsUseCaseProtocol }
        Resolver.register { self.claims_serviceMock as CreatePetDocumentsUseCaseProtocol }
        Resolver.register { self.claims_serviceMock as GetPetClaimAttachmentsUseCaseProtocol }
        Resolver.register { self.claims_serviceMock as GetPetClaimAttachmentUseCaseProtocol }
        Resolver.register { self.claims_serviceMock as GetPetClaimsUseCaseProtocol }
        Resolver.register { self.claims_serviceMock as GetPetClaimUseCaseProtocol }
        Resolver.register { self.claims_serviceMock as GetPetCommunicationsUseCaseProtocol }
        Resolver.register { self.claims_serviceMock as PetUpdateFeedbackUseCaseProtocol }
        Resolver.register { self.getPolicyAttachmentService as GetPolicyAttachmentUseCaseProtocol }
    }
}

// MARK: - Computed Properties Tests
extension CloudPolicyFilesViewModelTests {
    
    func test_petName_WhenPetNameIsNil_ShouldReturnDefaultLocalizableStringUppercased() {
        sut_claim.selectedCloud = nil

        let expectation = sut_claim.cloudName
        XCTAssertEqual(expectation, "cloud.breadcrumb.label".localized.uppercased())
    }
    
    func test_petName_WhenPetNameIsNotNil_ShouldReturnPetNameUppercased() {
        let cloudPet = SelectedCloud(id: "pet_id", name: "Pet Name", cloudDocumentPolicies: [])
        sut_claim.selectedCloud = cloudPet

        let expectation = sut_claim.cloudName
        XCTAssertEqual(expectation, "Pet Name".uppercased())
    }
}

// MARK: - Public Methods Tests
extension CloudPolicyFilesViewModelTests {
    
    func test_getBreadcrumbPathUppercased_WhenViewTypeIsBase_ShoudReturnEmptyString() {
        let expectation = sut_claim.getBreadcrumbPathUppercased(viewType: .base)
        XCTAssertEqual(expectation, "")
    }
    
    func test_getBreadcrumbPathUppercased_WhenViewTypeIsPetPolicies_ShoudReturnEmptyString() {
        let expectation = sut_claim.getBreadcrumbPathUppercased(viewType: .petPolicies)
        XCTAssertEqual(expectation, "")
    }
    
    func test_getBreadcrumbPathUppercased_WhenViewTypeIsPetPolicyDocumentsOptions_ShoudReturnEmptyString() {
        let expectation = sut_claim.getBreadcrumbPathUppercased(viewType: .petPolicyDocumentsOptions)
        XCTAssertEqual(expectation, "")
    }
    
    func test_getBreadcrumbPathUppercased_WhenViewTypeIsPetClaimAndInvoicesList_ShoudReturnEmptyString() {
        let expectation = sut_claim.getBreadcrumbPathUppercased(viewType: .petClaimAndInvoicesList)
        XCTAssertEqual(expectation, "")
    }
    
    func test_getBreadcrumbPathUppercased_WhenViewTypeIsPetFilesAndOptionSelectedIsClaim_ShoudReturnPathWithPrefixClaimId() {
        let claimDocument = KanguroSharedDomain.ClaimDocument(claimPrefixId: "UP1000", claimId: "claim_test", claimDocuments: [])
        sut_claim.claimDocument = claimDocument
        
        if let prefixId = sut_claim.claimDocument?.claimPrefixId {
            let expectation = sut_claim.getBreadcrumbPathUppercased(viewType: .petFiles)
            let finalPath = "\(sut_claim.cloudName) / \("cloud.breadcrumb.policy.label".localized.uppercased()) / ... / \("cloud.claimCard.label".localized.uppercased()) \(prefixId.uppercased())"
            XCTAssertEqual(expectation, finalPath)
        } else {
            XCTFail("No prefixId found")
        }
    }
    
    func test_getBreadcrumbPathUppercased_WhenViewTypeIsPetFilesAndOptionSelectedIsMedicalHistory_ShoudReturnPathWithMedicalHistory() {
        if let medicalHistoryOption = sut_medicalHistory.petPolicyOption?.text {
            let expectation = sut_medicalHistory.getBreadcrumbPathUppercased(viewType: .petFiles)
            let finalPath = "\(sut_medicalHistory.cloudName) / \("cloud.breadcrumb.policy.label".localized.uppercased()) / \(medicalHistoryOption.uppercased())"
            XCTAssertEqual(expectation, finalPath)
        } else {
            XCTFail("No option found")
        }
    }
    
    func test_getBreadcrumbPathUppercased_WhenViewTypeIsPetFilesAndOptionSelectedIsPolicyDocuments_ShoudReturnPathWithPolicyDocuments() {
        if let policyDocumentsOption = sut_policyDocuments.petPolicyOption?.text {
            let expectation = sut_policyDocuments.getBreadcrumbPathUppercased(viewType: .petFiles)
            
            let finalPath = "\(sut_policyDocuments.cloudName) / \("cloud.breadcrumb.policy.label".localized.uppercased()) / \(policyDocumentsOption.uppercased())"
            XCTAssertEqual(expectation, finalPath)
        } else {
            XCTFail("No option found")
        }
    }
}

// MARK: - Network Test
extension CloudPolicyFilesViewModelTests {

    func test_getClaimDocumentsByPolicyIdAndClaimId_WhenAPICallIsSuccessful_ShouldUpdateClaimDocumentAndState() {
        cloud_serviceMock.requestShouldFail = false
        sut_claim.claimDocument = ClaimDocument(claimPrefixId: "", claimId: "claim_test", claimDocuments: [])
        
        sut_claim.getClaimDocumentsByPolicyIdAndClaimId()
        
        XCTAssertEqual(sut_claim.claimDocument?.claimDocuments?.count, 1)
        XCTAssertEqual(sut_claim.claimDocument?.claimPrefixId, "UP10000")
        XCTAssertEqual(sut_claim.claimDocument?.claimDocuments?.first?.id, 777)

        XCTAssertEqual(sut_claim.state, .requestSucceeded)
        XCTAssertEqual(cloud_serviceMock.calledMethods, [.getClaimDocumentsByPolicyIdAndClaimId])
    }
    
    func test_getClaimDocumentsByPolicyIdAndClaimId_WhenAPICallFails_ShouldUpdateRequestErrorAndState() {
        cloud_serviceMock.requestShouldFail = true
        sut_claim.claimDocument = ClaimDocument(claimPrefixId: "", claimId: "claim_test", claimDocuments: [])

        sut_claim.getClaimDocumentsByPolicyIdAndClaimId()
        
        XCTAssertEqual(sut_claim.requestError, "serverError.default".localized)
        XCTAssertEqual(sut_claim.state, .requestFailed)
        
        XCTAssertEqual(cloud_serviceMock.calledMethods, [.getClaimDocumentsByPolicyIdAndClaimId])
    }
    
    func test_getClaimAttachment_WhenAPICallIsSuccessful_ShouldDownloadAttachmentAndUpdateState() {
        claims_serviceMock.requestShouldFail = false
        sut_claim.claimDocument = ClaimDocument(claimPrefixId: "", claimId: "claim_test", claimDocuments: [])
        
        sut_claim.getClaimAttachment(attachment: Attachment(id: 777, fileName: "file_test", fileSize: 12345))
        
        XCTAssertEqual(sut_claim.state, .downloadSucceeded(Data()))
        XCTAssertEqual(claims_serviceMock.calledMethods, [.getClaimAttachment])
    }
    
    func test_getClaimAttachment_WhenAPICallFails_ShouldUpdateRequestErrorAndState() {
        claims_serviceMock.requestShouldFail = true
        sut_claim.claimDocument = ClaimDocument(claimPrefixId: "", claimId: "claim_test", claimDocuments: [])
        
        sut_claim.getClaimAttachment(attachment: Attachment(id: 777, fileName: "file_test", fileSize: 12345))
        
        XCTAssertEqual(sut_claim.requestError, "serverError.default".localized)
        XCTAssertEqual(sut_claim.state, .requestFailed)
        XCTAssertEqual(claims_serviceMock.calledMethods, [.getClaimAttachment])
    }
    
    func test_getPolicyAttachment_WhenAPICallIsSuccessful_ShouldDownloadPolicyAttachmentAndUpdateState() {
        
        sut_medicalHistory.getPolicyAttachment(policyAttachment: PolicyAttachment(id: 777, name: "file_test", fileSize: 12345))
        getPolicyAttachmentService.completeSuccessfully()
        XCTAssertEqual(sut_medicalHistory.state, .downloadSucceeded(Data()))
        XCTAssertEqual(getPolicyAttachmentService.callCount, 1)
    }
    
    func test_getPolicyAttachment_WhenAPICallFails_ShouldUpdateRequestErrorAndState() {

        sut_medicalHistory.getPolicyAttachment(policyAttachment: PolicyAttachment(id: 777, name: "file_test", fileSize: 12345))
        getPolicyAttachmentService.completeWithError()
        XCTAssertEqual(sut_medicalHistory.requestError, "serverError.default".localized)
        XCTAssertEqual(sut_medicalHistory.state, .requestFailed)
        XCTAssertEqual(getPolicyAttachmentService.callCount, 1)
    }
}
