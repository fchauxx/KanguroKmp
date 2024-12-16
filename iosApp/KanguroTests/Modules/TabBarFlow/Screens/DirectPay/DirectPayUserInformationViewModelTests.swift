import XCTest
@testable import Kanguro
import KanguroSharedDomain
import KanguroPetDomain
import KanguroPetData


final class DirectPayUserInformationViewModelTests: XCTestCase {

    var sut: DirectPayUserInformationViewModel!

    // MARK: - Initializers
    override func setUp() {
        sut = DirectPayUserInformationViewModel(pets: [Pet(id: 99, name: "Willy", type: .Cat)])
        continueAfterFailure = false
    }

    override class func tearDown() {
        super.tearDown()
    }

    func test_isUserAllowedToContinueDTPClaimCreation_WhenUserDoNotFillAnyInputField_NextButtonShouldBeDisabled() {
        let newDTPClaim = TestPetVetDirectPaymentParametersFactory.makePetVetDirectPaymentParametersWithAllNil()
        sut.newDirectPayClaim = newDTPClaim
        XCTAssertFalse(sut.isUserAllowedToContinueDTPClaimCreation)
    }

    func test_isUserAllowedToContinueDTPClaimCreation_WhenUserFillOnlyOneInputField_NextButtonShouldBeDisabled() {
        let newDTPClaim = TestPetVetDirectPaymentParametersFactory.makePetVetDirectPaymentParametersWithAllNil()

        sut.newDirectPayClaim = newDTPClaim
        sut.newDirectPayClaim.petId = 99
        XCTAssertFalse(sut.isUserAllowedToContinueDTPClaimCreation)

        sut.newDirectPayClaim.petId = nil
        sut.newDirectPayClaim.type = .Accident
        XCTAssertFalse(sut.isUserAllowedToContinueDTPClaimCreation)

        sut.newDirectPayClaim.type = nil
        sut.newDirectPayClaim.invoiceDate = Date()
        XCTAssertFalse(sut.isUserAllowedToContinueDTPClaimCreation)

        sut.newDirectPayClaim.invoiceDate = nil
        sut.newDirectPayClaim.amount = 1000
        XCTAssertFalse(sut.isUserAllowedToContinueDTPClaimCreation)

        sut.newDirectPayClaim.amount = nil
        sut.newDirectPayClaim.veterinarianId = 77
        XCTAssertFalse(sut.isUserAllowedToContinueDTPClaimCreation)
    }

    func test_isUserAllowedToContinueDTPClaimCreation_WhenUserFillsPetAndTypeInputField_NextButtonShouldBeDisabled() {
        let newDTPClaim = TestPetVetDirectPaymentParametersFactory.makePetVetDirectPaymentParametersWithAllNil()
        sut.newDirectPayClaim = newDTPClaim
        sut.newDirectPayClaim.petId = 99
        sut.newDirectPayClaim.amount = 777
        sut.newDirectPayClaim.type = .Accident
        XCTAssertFalse(sut.isUserAllowedToContinueDTPClaimCreation)
    }

    func test_isUserAllowedToContinueDTPClaimCreation_WhenUserFillsPetAndTypeAndInvoiceDateInputField_NextButtonShouldBeDisabled() {
        let newDTPClaim = TestPetVetDirectPaymentParametersFactory.makePetVetDirectPaymentParametersWithAllNil()

        sut.newDirectPayClaim = newDTPClaim
        sut.newDirectPayClaim.petId = 99
        sut.newDirectPayClaim.amount = 777
        sut.newDirectPayClaim.type = .Accident
        sut.newDirectPayClaim.invoiceDate = Date()
        XCTAssertFalse(sut.isUserAllowedToContinueDTPClaimCreation)
    }


    func test_isUserAllowedToContinueDTPClaimCreation_WhenUserMissSomeVetDataOrDescription_NextButtonShouldBeDisabled() {
        let newDTPClaim = TestPetVetDirectPaymentParametersFactory.makePetVetDirectPaymentParametersWithAllNil()

        sut.newDirectPayClaim = newDTPClaim
        sut.newDirectPayClaim.petId = 99
        sut.newDirectPayClaim.amount = 777
        sut.newDirectPayClaim.type = .Accident
        sut.newDirectPayClaim.invoiceDate = Date()

        // Testing nil data
        XCTAssertFalse(sut.isUserAllowedToContinueDTPClaimCreation)
        sut.newDirectPayClaim.veterinarianEmail = "bob@email.com"
        XCTAssertFalse(sut.isUserAllowedToContinueDTPClaimCreation)
        sut.newDirectPayClaim.veterinarianName = "Bob"
        XCTAssertFalse(sut.isUserAllowedToContinueDTPClaimCreation)
        sut.newDirectPayClaim.veterinarianEmail = nil
        sut.newDirectPayClaim.veterinarianClinic = "Bobs Clinic"
        sut.newDirectPayClaim.description = ""
        XCTAssertFalse(sut.isUserAllowedToContinueDTPClaimCreation)

        // Testing with Empty data
        sut.newDirectPayClaim.veterinarianEmail = ""
        sut.newDirectPayClaim.veterinarianName = "Bob"
        sut.newDirectPayClaim.veterinarianClinic = "Bobs Clinic"
        sut.newDirectPayClaim.description = "My description"
        XCTAssertFalse(sut.isUserAllowedToContinueDTPClaimCreation)
        sut.newDirectPayClaim.veterinarianEmail = "bob@email.com"
        sut.newDirectPayClaim.veterinarianName = ""
        XCTAssertFalse(sut.isUserAllowedToContinueDTPClaimCreation)
        sut.newDirectPayClaim.veterinarianName = "Bob"
        sut.newDirectPayClaim.veterinarianClinic = ""
        XCTAssertFalse(sut.isUserAllowedToContinueDTPClaimCreation)

        //Testing description field empty
        sut.newDirectPayClaim.veterinarianEmail = "bob@email.com"
        sut.newDirectPayClaim.veterinarianName = "Bob"
        sut.newDirectPayClaim.veterinarianClinic = "Bobs Clinic"
        sut.newDirectPayClaim.description = ""
        XCTAssertFalse(sut.isUserAllowedToContinueDTPClaimCreation)
    }

    func test_setCheckboxSelection_WhenUserChecksSomeOption_ShouldSetTypeCorrectlyRegardlessOfAppLanguage() {
        let newDTPClaim = TestPetVetDirectPaymentParametersFactory.makePetVetDirectPaymentParametersWithAllNil()
        var data: [CheckboxLabelData] = [CheckboxLabelData(isSelected: true, option: "Accident")]

        sut.setCheckboxSelection(selectedItem: data)
        XCTAssertEqual(sut.newDirectPayClaim.type, .Accident)

        data = [CheckboxLabelData(isSelected: true, option: "Illness")]
        sut.setCheckboxSelection(selectedItem: data)
        XCTAssertEqual(sut.newDirectPayClaim.type, .Illness)

        data = [CheckboxLabelData(isSelected: true, option: "Accidente")]
        sut.setCheckboxSelection(selectedItem: data)
        XCTAssertEqual(sut.newDirectPayClaim.type, .Accident)

        data = [CheckboxLabelData(isSelected: true, option: "Enfermedad")]
        sut.setCheckboxSelection(selectedItem: data)
        XCTAssertEqual(sut.newDirectPayClaim.type, .Illness)
    }

    func test_setCheckboxSelection_WhenUserUnchecksClaimTypes_ShouldSetTypeOfNewDTPClaimToNil() {
        let newDTPClaim = TestPetVetDirectPaymentParametersFactory.makePetVetDirectPaymentParametersWithAllNil()
        var data: [CheckboxLabelData] = []

        sut.setCheckboxSelection(selectedItem: data)
        XCTAssertNil(sut.newDirectPayClaim.type)
    }

    func test_isUserAllowedToContinueDTPClaimCreation_WhenUserFillsAllInputFieldButNoVetId_NextButtonShouldBeEnabled() {
        let newDTPClaim = TestPetVetDirectPaymentParametersFactory.makePetVetDirectPaymentParametersWithAllNil()

        sut.newDirectPayClaim = newDTPClaim
        sut.newDirectPayClaim.veterinarianId = nil
        sut.newDirectPayClaim.petId = 99
        sut.newDirectPayClaim.type = .Accident
        sut.newDirectPayClaim.invoiceDate = Date()
        sut.newDirectPayClaim.veterinarianEmail = "bob@email.com"
        sut.newDirectPayClaim.veterinarianName = "Bob"
        sut.newDirectPayClaim.veterinarianClinic = "Bob's Clinic"
        sut.newDirectPayClaim.amount = 777
        sut.newDirectPayClaim.description = "My Description"
        XCTAssertTrue(sut.isUserAllowedToContinueDTPClaimCreation)
    }

    func test_isUserAllowedToContinueDTPClaimCreation_WhenUserFillsAllInputField_NextButtonShouldBeEnabled() {
        let newDTPClaim = TestPetVetDirectPaymentParametersFactory.makePetVetDirectPaymentParametersWithAllNil()

        sut.newDirectPayClaim = newDTPClaim
        sut.newDirectPayClaim.petId = 99
        sut.newDirectPayClaim.type = .Accident
        sut.newDirectPayClaim.invoiceDate = Date()
        sut.newDirectPayClaim.veterinarianEmail = "bob@email.com"
        sut.newDirectPayClaim.veterinarianName = "Bob"
        sut.newDirectPayClaim.veterinarianClinic = "Bob's Clinic"
        sut.newDirectPayClaim.amount = 777
        sut.newDirectPayClaim.veterinarianId = 99
        sut.newDirectPayClaim.description = "My Description"
        XCTAssertTrue(sut.isUserAllowedToContinueDTPClaimCreation)
    }

    func test_pickFirstPet_WhenPetListIsEmpty_ShouldReturnNil() {
        sut.pets = []
        XCTAssertNil(sut.pickFirstPet)
    }

    func test_pickFirstPet_WhenPetListIsNotEmpty_ShouldReturnACustomPickerDataOfTheFirstPet() {
        XCTAssertEqual(sut.pickFirstPet, CustomPickerData(id: 99,
                                                          icon: UIImage(named: "ic-cat"),
                                                          label: "Willy"))
    }

    func test_petPickerList_WhenPetListIsEmpty_ShouldReturnEmptyCustomPickerDataArray() {

        sut.pets = []
        XCTAssertEqual(sut.petPickerList, [])
    }

    func test_petPickerList_WhenPetListIsNotEmpty_ShouldReturnACustomPickerDataList() {

        XCTAssertEqual(sut.petPickerList, [CustomPickerData(id: 99,
                                                            icon: UIImage(named: "ic-cat"),
                                                            label: "Willy")])
    }
}
