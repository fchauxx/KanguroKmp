import Foundation

// MARK: - Public Methods
extension DirectPayUserInformationViewController {

    func updateNextButtonEnabledStatus() {
        nextButton.isEnabled(viewModel.isUserAllowedToContinueDTPClaimCreation)
    }

    func updatePetSelectionPicker(data: CustomPickerData) {
        // TODO: - Update this hardcoded value.
        viewModel.newDirectPayClaim.petId = data.id
        updateNextButtonEnabledStatus()
        formSectionList.updateTextfieldCustomPicker(pickerData: data, sectionItemId: 0, section: 0)
    }

    func updateVetEmail() {
        let email = viewModel.databaseSelectedVet?.email ?? ""
        let data = CustomTextFieldData(title: "directPay.userInformation.fourthField.title.label".localized,
                                       placeholder: viewModel.newVeterinarianEmail.isEmpty ? email : viewModel.newVeterinarianEmail,
                                       isEditable: true,
                                       textFieldType: .default)
        formSectionList.updateCustomTextField(data: data, section: 1)
        updateNextButtonEnabledStatus()
    }

    func updateVetName() {
        let name = viewModel.databaseSelectedVet?.veterinarianName ?? ""
        let data = CustomTextFieldData(title: "directPay.userInformation.fifthField.title.label".localized,
                                       placeholder: viewModel.newVeterinarianEmail.isEmpty ? name : "",
                                       isEditable: true,
                                       textFieldType: .default)
        formSectionList.updateCustomTextField(data: data, section: 1)
        updateNextButtonEnabledStatus()
    }

    func updateClinicName() {
        let clinicName = viewModel.databaseSelectedVet?.clinicName ?? ""
        let data = CustomTextFieldData(title: "directPay.userInformation.sixthField.title.label".localized,
                                       placeholder: viewModel.newVeterinarianEmail.isEmpty ? clinicName : "",
                                       isEditable: true,
                                       textFieldType: .default)
        formSectionList.updateCustomTextField(data: data, section: 1)
        updateNextButtonEnabledStatus()
    }
}
