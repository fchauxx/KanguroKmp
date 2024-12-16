import Foundation

struct CheckboxListLabelData: FormDataProtocol {

    let checkboxQuestionTitle: String
    let isMultipleSelectionEnabled: Bool
    let options: [CheckboxLabelData]
    let listOrientation: CheckboxOptionListOrientation
    let optionsSelected: SelectedCheckboxDataClosure

    init(checkboxQuestionTitle: String,
         isMultipleSelectionEnabled: Bool,
         options: [CheckboxLabelData],
         listOrientation: CheckboxOptionListOrientation = .horizontal,
         optionsSelected: @escaping SelectedCheckboxDataClosure) {
        self.checkboxQuestionTitle = checkboxQuestionTitle
        self.isMultipleSelectionEnabled = isMultipleSelectionEnabled
        self.options = options
        self.listOrientation = listOrientation
        self.optionsSelected = optionsSelected
    }
}

struct CheckboxLabelData {

    let id: UUID = UUID()
    var isSelected: Bool
    var option: String
}
