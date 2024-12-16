import UIKit

class FormularySectionListView: BaseView, NibOwnerLoadable {
    
    // MARK: - IBOutlets
    @IBOutlet private var stackView: UIStackView!
    
    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
    }
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.loadNibContent()
    }
}

// MARK: - Setup
extension FormularySectionListView {
    
    func setupForm(formSectionDataList: [FormularySectionData]) {
        for index in 0..<formSectionDataList.count {
            let newFormSectionData = FormularySectionView()
            newFormSectionData.tag = index
            newFormSectionData.setup(sectionData: formSectionDataList[index])
            stackView.addArrangedSubview(newFormSectionData)
        }
    }
}

// MARK: - Custom Textfields Updates
extension FormularySectionListView {
    
    func updateTextfieldCustomPicker(pickerData: CustomPickerData, sectionItemId: Int, section: Int) {
        if let currentFormularySectionIndex = stackView.arrangedSubviews.firstIndex(where: { $0.tag == section }),
           let currentFormularySection = stackView.arrangedSubviews[currentFormularySectionIndex] as? FormularySectionView,
           let currentTextfieldCustomPicker = currentFormularySection.stackView.arrangedSubviews[sectionItemId] as? CustomTextFieldView,
           let leadingIcon = pickerData.icon {
            
            currentTextfieldCustomPicker.textField.text = pickerData.label
            currentTextfieldCustomPicker.setLeadingIcon(leadingIcon)
        }
    }
    
    func updateCustomTextField(data: CustomTextFieldData, section: Int) {
        if let currentFormularySection = stackView.arrangedSubviews[section] as? FormularySectionView {
            for index in 0..<currentFormularySection.stackView.arrangedSubviews.count {
                if let currentTextfield = currentFormularySection.stackView.arrangedSubviews[index] as? CustomTextFieldView,
                   currentTextfield.title == data.title {
                    currentTextfield.textField.text = data.placeholder
                    currentTextfield.textField.type = data.textFieldType
                }
            }
        }
    }
}
