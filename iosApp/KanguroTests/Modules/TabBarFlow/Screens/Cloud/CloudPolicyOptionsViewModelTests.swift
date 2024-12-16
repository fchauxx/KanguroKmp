import XCTest
import Resolver
@testable import Kanguro
import KanguroSharedDomain
import KanguroPetDomain

final class CloudPolicyOptionsViewModelTests: XCTestCase {
    
    var sut: CloudPolicyDocumentsOptionsViewModel!
    var serviceMock: CloudDocumentServiceMock!
    
    override func setUp() {
        sut = CloudPolicyDocumentsOptionsViewModel(selectedCloud: SelectedCloud(id: "999", name: "Pet Name", cloudDocumentPolicies: []), policyId: "id_test")
        serviceMock = CloudDocumentServiceMock()
        Resolver.register { self.serviceMock as (any GetCloudDocumentUseCaseProtocol) }
        Resolver.register { self.serviceMock as GetCloudDocumentsByPolicyUseCaseProtocol }
        Resolver.register { self.serviceMock as GetClaimDocumentByPolicyAndClaimUseCaseProtocol }
    }
}

// MARK: - Computed Properties Tests
extension CloudPolicyOptionsViewModelTests {
    
    func test_petName_WhenPetNameIsNil_ShouldReturnDefaultLocalizableStringUppercased() {
        sut.selectedCloud = nil

        
        let expectation = sut.cloudName
        XCTAssertEqual(expectation, "cloud.breadcrumb.label".localized.uppercased())
    }
    
    func test_petName_WhenPetNameIsNotNil_ShouldReturnPetNameUppercased() {
        let cloudPet = SelectedCloud(id: "pet_id", name: "Pet Name", cloudDocumentPolicies: [])
        sut.selectedCloud = cloudPet

        
        let expectation = sut.cloudName
        XCTAssertEqual(expectation, "Pet Name".uppercased())
    }
}

// MARK: - Public Methods Tests
extension CloudPolicyOptionsViewModelTests {
    
    func test_getBreadcrumbPathUppercased_WhenViewTypeIsBase_ShoudReturnEmptyString() {
        let expectation = sut.getBreadcrumbPathUppercased(viewType: .base)
        XCTAssertEqual(expectation, "")
    }
    
    func test_getBreadcrumbPathUppercased_WhenViewTypeIsPetPolicies_ShoudReturnEmptyString() {
        let expectation = sut.getBreadcrumbPathUppercased(viewType: .petPolicies)
        XCTAssertEqual(expectation, "")
    }
    
    func test_getBreadcrumbPathUppercased_WhenViewTypeIsPetPolicyDocumentsOptions_ShoudReturnPetNameAndViewTypeBreadcrumb() {
        let expectation = sut.getBreadcrumbPathUppercased(viewType: .petPolicyDocumentsOptions)
        let finalPath = "\(sut.cloudName) / \("cloud.breadcrumb.policy.label".localized.uppercased())"
        XCTAssertEqual(expectation, finalPath)
    }
    
    func test_getBreadcrumbPathUppercased_WhenViewTypeIsPetClaimAndInvoicesList_ShoudReturnPetNameAndViewTypeBreadcrumb() {
        let expectation = sut.getBreadcrumbPathUppercased(viewType: .petClaimAndInvoicesList)
        let finalPath = "\(sut.cloudName) / \("cloud.breadcrumb.policy.label".localized.uppercased()) / \("cloud.breadcrumb.claimDocuments.label".localized.uppercased())"
        XCTAssertEqual(expectation, finalPath)
    }
    
    func test_getBreadcrumbPathUppercased_WhenViewTypeIsPetFiles_ShoudReturnEmptyString() {
        let expectation = sut.getBreadcrumbPathUppercased(viewType: .petFiles)
        XCTAssertEqual(expectation, "")
    }
}

// MARK: - Network Test
extension CloudPolicyOptionsViewModelTests {

    func test_getCloudDocumentsByPolicyId_WhenAPICallIsSuccessful_ShouldUpdateFilesDocumentsAndAttachmentsAndState() {
        serviceMock.requestShouldFail = false
        sut.getCloudDocumentsByPolicyId()
        
        XCTAssertEqual(sut.policyAttachments?.count, 1)
        XCTAssertEqual(sut.policyDocuments?.count, 1)
        XCTAssertEqual(sut.claimDocuments?.count, 1)

        XCTAssertEqual(sut.policyAttachments?.first?.id, 777)
        XCTAssertEqual(sut.policyDocuments?.first?.id, 777)
        XCTAssertEqual(sut.claimDocuments?.first?.claimPrefixId, "UP10000")
        
        XCTAssertEqual(sut.state, .requestSucceeded)
        XCTAssertEqual(serviceMock.calledMethods, [.getCloudDocumentsByPolicyId])
    }

    func test_getCloudDocumentsByPolicyId_WhenAPICallFails_ShouldUpdateRequestErrorAndState() {
        serviceMock.requestShouldFail = true
        sut.getCloudDocumentsByPolicyId()
        
        XCTAssertEqual(sut.requestError, "serverError.default".localized)
        XCTAssertEqual(sut.state, .requestFailed)
        
        XCTAssertEqual(serviceMock.calledMethods, [.getCloudDocumentsByPolicyId])
    }
}
