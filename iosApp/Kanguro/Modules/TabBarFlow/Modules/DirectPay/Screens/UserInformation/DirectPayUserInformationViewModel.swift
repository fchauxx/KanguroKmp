import Foundation
import KanguroAnalyticsDomain
import KanguroPetDomain
import KanguroUserDomain
import UIKit
import Resolver

class DirectPayUserInformationViewModel {

    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var getLocalUserService: GetLocalUser

    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started

    // MARK: - Stored Properties
    var pets: [Pet]
    var databaseSelectedVet: Veterinarian?
    var newVeterinarianEmail: String
    var newDirectPayClaim: PetVetDirectPaymentParameters
    var isVetSelectedNotInDatabase: Bool

    // MARK: - Computed Properties
    var user: User? {
        guard let user: User = try? getLocalUserService.execute().get() else { return nil }
        return user
    }
    var userName: String {
        guard let user: User = try? getLocalUserService.execute().get(),
              let givenName = user.givenName else { return "" }
        if let surname = user.surname {
            return "\(givenName) \(surname)"
        } else {
            return givenName
        }
    }
    var pickFirstPet: CustomPickerData? {
        guard let pet = pets.first else { return nil }
        newDirectPayClaim.petId = pet.id
        return CustomPickerData(id: pet.id,
                                icon: pet.type == .Cat ? UIImage(named: "ic-cat") : UIImage(named: "ic-dog"),
                                label: pet.name ?? "")
    }
    var petPickerList: [CustomPickerData] {
        return pets.map { pet in
            CustomPickerData(id: pet.id,
                             icon: pet.type == .Cat ? UIImage(named: "ic-cat") : UIImage(named: "ic-dog"),
                             label: pet.name ?? "")
        }
    }
    var isUserAllowedToContinueDTPClaimCreation: Bool {
        return newDirectPayClaim.petId != nil &&
        newDirectPayClaim.type != nil &&
        newDirectPayClaim.invoiceDate != nil &&
        (newDirectPayClaim.veterinarianEmail != nil && newDirectPayClaim.veterinarianEmail != "") &&
        (newDirectPayClaim.veterinarianName != nil && newDirectPayClaim.veterinarianName != "") &&
        (newDirectPayClaim.veterinarianClinic != nil && newDirectPayClaim.veterinarianClinic != "") &&
        newDirectPayClaim.amount != nil &&
        (newDirectPayClaim.description != nil && newDirectPayClaim.description != "")
    }

    // MARK: - Initializers
    init(pets: [Pet],
         databaseSelectedVet: Veterinarian? = nil,
         newVeterinarianEmail: String = "",
         newDirectPayClaim: PetVetDirectPaymentParameters = PetVetDirectPaymentParameters(),
         isVetSelectedNotInDatabase: Bool = false) {
        self.pets = pets
        self.databaseSelectedVet = databaseSelectedVet
        self.newVeterinarianEmail = newVeterinarianEmail
        self.newDirectPayClaim = newDirectPayClaim
        self.isVetSelectedNotInDatabase = isVetSelectedNotInDatabase
    }
}

// MARK: - Analytics
extension DirectPayUserInformationViewModel {

    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .DTPUserInformationForm)
    }
}

// MARK: - Public Methods
extension DirectPayUserInformationViewModel {

    func update(selectedVet: Veterinarian) {
        self.databaseSelectedVet = selectedVet
        isVetSelectedNotInDatabase = false
        newVeterinarianEmail = ""
        newDirectPayClaim.veterinarianEmail = selectedVet.email
        newDirectPayClaim.veterinarianName = selectedVet.veterinarianName
        newDirectPayClaim.veterinarianClinic = selectedVet.clinicName
        newDirectPayClaim.veterinarianId = selectedVet.id
        state = .dataChanged
    }

    func update(newVetEmail: String) {
        databaseSelectedVet = nil
        isVetSelectedNotInDatabase = true
        newVeterinarianEmail = newVetEmail
        newDirectPayClaim.veterinarianEmail = newVetEmail
        newDirectPayClaim.veterinarianId = nil
        newDirectPayClaim.veterinarianName = nil
        newDirectPayClaim.veterinarianClinic = nil
        state = .dataChanged
    }

    func update(dtpClaimDescription: String) {
        newDirectPayClaim.description = dtpClaimDescription
    }

    func setCheckboxSelection(selectedItem: [CheckboxLabelData]) {
        if var typeSelected = selectedItem.first?.option {
            if typeSelected == PetClaimType.Accident.es_localization || typeSelected == PetClaimType.Illness.es_localization  {
                typeSelected = typeSelected == PetClaimType.Accident.es_localization ? PetClaimType.Accident.rawValue : PetClaimType.Illness.rawValue
            }
            let type = PetClaimType(rawValue: typeSelected)
            newDirectPayClaim.type = type
        } else {
            newDirectPayClaim.type = nil
        }
    }
}
