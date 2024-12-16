import XCTest
import Resolver
@testable import Kanguro
import KanguroSharedDomain
import KanguroPetDomain
import KanguroPetData

final class CloudViewModelTests: XCTestCase {
    
    var sut: CloudViewModel!
    var serviceMock: GetPetCloudDocument!
    var petCloudDocumentRepositoryMock: PetCloudDocumentRepositoryMock!
    
    override func setUp() {
        sut = CloudViewModel()
        petCloudDocumentRepositoryMock = PetCloudDocumentRepositoryMock()
        Resolver.register { self.petCloudDocumentRepositoryMock }
        
        serviceMock = GetPetCloudDocument(petCloudDocumentRepo: petCloudDocumentRepositoryMock)
        Resolver.register { self.serviceMock }
    }
}

// MARK: - Computed Properties Tests
extension CloudViewModelTests {
    
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
    
    func test_policiesSortedByNewest_WhenCloudDocumentPolicyListIsNil_ShouldReturnNil() {
        let expectation = sut.policiesSortedByNewest
        XCTAssertNil(expectation)
    }
    
    func test_policiesSortedByNewest_WhenCloudDocumentPolicyListIsNotNil_ShouldReturnOrderedByNewestDate() {
        let unorderedList = [
           KanguroSharedDomain.CloudDocumentPolicy(id: "test", ciId: 777, policyStartDate: Date(timeIntervalSince1970: 1654041600), policyAttachments: [], policyDocuments: [], claimDocuments: []),
           KanguroSharedDomain.CloudDocumentPolicy(id: "test", ciId: 777, policyStartDate: Date(timeIntervalSince1970: 1622505600), policyAttachments: [], policyDocuments: [], claimDocuments: []),
           KanguroSharedDomain.CloudDocumentPolicy(id: "test", ciId: 777, policyStartDate: Date(timeIntervalSince1970: 1685577600), policyAttachments: [], policyDocuments: [], claimDocuments: [])
        ]
        let orderedList = [
           KanguroSharedDomain.CloudDocumentPolicy(id: "test", ciId: 777, policyStartDate: Date(timeIntervalSince1970: 1685577600), policyAttachments: [], policyDocuments: [], claimDocuments: []),
           KanguroSharedDomain.CloudDocumentPolicy(id: "test", ciId: 777, policyStartDate: Date(timeIntervalSince1970: 1654041600), policyAttachments: [], policyDocuments: [], claimDocuments: []),
           KanguroSharedDomain.CloudDocumentPolicy(id: "test", ciId: 777, policyStartDate: Date(timeIntervalSince1970: 1622505600), policyAttachments: [], policyDocuments: [], claimDocuments: [])
        ]
        sut.cloudDocumentPolicies = unorderedList
        
        let expectation = sut.policiesSortedByNewest
        XCTAssertEqual(expectation, orderedList)
    }
}
    
// MARK: - Public Methods Tests
extension CloudViewModelTests {
    
    func test_getBreadcrumbPathUppercased_WhenViewTypeIsBase_ShoudReturnDefaultLocalizedString() {
        let expectation = sut.getBreadcrumbPathUppercased(viewType: .base)
        XCTAssertEqual(expectation, "cloud.breadcrumb.label".localized.uppercased())
    }
    
    func test_getBreadcrumbPathUppercased_WhenViewTypeIsPetPolicies_ShoudReturnPetNameString() {
        let expectation = sut.getBreadcrumbPathUppercased(viewType: .petPolicies)
        XCTAssertEqual(expectation, sut.cloudName)
    }
    
    func test_getBreadcrumbPathUppercased_WhenViewTypeIsPetPolicyDocumentsOptions_ShoudReturnEmptyString() {
        let expectation = sut.getBreadcrumbPathUppercased(viewType: .petPolicyDocumentsOptions)
        XCTAssertEqual(expectation, "")
    }
    
    func test_getBreadcrumbPathUppercased_WhenViewTypeIsPetClaimAndInvoicesList_ShoudReturnEmptyString() {
        let expectation = sut.getBreadcrumbPathUppercased(viewType: .petClaimAndInvoicesList)
        XCTAssertEqual(expectation, "")
    }
    
    func test_getBreadcrumbPathUppercased_WhenViewTypeIsPetFiles_ShoudReturnEmptyString() {
        let expectation = sut.getBreadcrumbPathUppercased(viewType: .petFiles)
        XCTAssertEqual(expectation, "")
    }
    
    func test_shouldHiddenOrderByButton_WhenViewTypeIsPetPolicies_ShouldReturnTrue() {
        let expectation = sut.shouldHiddenOrderByButton(viewType: .petPolicies)
        XCTAssertFalse(expectation)
    }
    
    func test_shouldHiddenOrderByButton_WhenViewTypeIsNotPetPolicies_ShouldReturnFalse() {
        let expectation = sut.shouldHiddenOrderByButton(viewType: .petClaimAndInvoicesList)
        XCTAssertTrue(expectation)
    }
}
    
// MARK: - Network Test
extension CloudViewModelTests {
    
    func test_getCloudDocuments_WhenAPICallIsSuccessful_ShouldUpdatePetListAndState() {
        petCloudDocumentRepositoryMock.requestShouldFail = false
        sut.getCloudDocuments()
        
        XCTAssertEqual(sut.petList.count, 1)
        XCTAssertEqual(sut.petList.first?.id, 999)
        XCTAssertEqual(sut.state, .requestSucceeded)
        
        XCTAssertEqual(petCloudDocumentRepositoryMock.calledMethods, [.getCloudDocuments])
    }
    
    func test_getCloudDocuments_WhenAPICallFails_ShouldUpdateRequestErrorAndState() {
        petCloudDocumentRepositoryMock.requestShouldFail = true
        sut.getCloudDocuments()
        
        XCTAssertEqual(sut.requestError, "serverError.default".localized)
        XCTAssertEqual(sut.state, .requestFailed)
        
        XCTAssertEqual(petCloudDocumentRepositoryMock.calledMethods, [.getCloudDocuments])
    }
}
