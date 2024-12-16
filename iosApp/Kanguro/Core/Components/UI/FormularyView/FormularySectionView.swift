import UIKit

public protocol FormViewProtocol {}
public protocol FormDataProtocol {}

class FormularySectionView: BaseView, NibOwnerLoadable {

    // MARK: - IB Outlets
    @IBOutlet var stackView: UIStackView!
    @IBOutlet private var sectionTitle: CustomLabel!

    // MARK: - Stored Properties
    var sectionData: FormularySectionData

    // MARK: - Initializers
    required init?(coder: NSCoder) {
        sectionData = FormularySectionData(formFields: [])
        super.init(coder: coder)
        self.loadNibContent()
    }
    override init(frame: CGRect) {
        sectionData = FormularySectionData(formFields: [])
        super.init(frame: frame)
        self.loadNibContent()
    }
}

// MARK: - Setup
extension FormularySectionView {

    func setup(sectionData: FormularySectionData) {
        self.sectionData = sectionData
        setupLabels(sectionTitle: sectionData.sectionTitle)
        setupFields(fields: sectionData.formFields)
    }

    func setupLabels(sectionTitle: String?) {
        if let sectionTitle {
            self.sectionTitle.set(text: sectionTitle,
                                  style: TextStyle(color: .secondaryDark,
                                                   weight: .bold))
        } else {
            self.sectionTitle.isHidden = true
        }
    }

    func setupFields(fields: [FormDataProtocol]) {
        for fieldData in fields {
            if fieldData is CustomTextFieldData {
                createTextfields(customTextFieldData: fieldData as? CustomTextFieldData)
            } else if fieldData is CheckboxListLabelData {
                createCheckboxFields(checkboxListData: fieldData as? CheckboxListLabelData)
            } else {
                continue
            }
        }
    }
}

// MARK: - Create Texfield Fields
extension FormularySectionView {

    func createTextfields(customTextFieldData: CustomTextFieldData?) {
        guard let customTextFieldData else { return }
        let customTextFieldView = CustomTextFieldView()
        
        setTextfieldsIcons(customTextFieldData: customTextFieldData, customTextFieldView)
        setTexfieldEditStatus(customTextFieldData: customTextFieldData, customTextFieldView)
        setTextfieldActions(customTextFieldData: customTextFieldData, customTextFieldView)
        setTextfieldLabels(customTextFieldData: customTextFieldData, customTextFieldView)

        stackView.addArrangedSubview(customTextFieldView)
        stackView.addArrangedSubview(UIView())
        layoutIfNeeded()
    }

    private func setTextfieldLabels(customTextFieldData: CustomTextFieldData?,
                                    _ customTextFieldView: CustomTextFieldView) {
        guard let customTextFieldData else { return }
        customTextFieldView.set(title: customTextFieldData.title, color: .secondaryDark)
        customTextFieldView.set(placeholder: customTextFieldData.placeholder ?? "",
                                color: customTextFieldView.type == .customPicker ? .secondaryDarkest : .secondaryLight)
    }

    private func setTextfieldsIcons(customTextFieldData: CustomTextFieldData?,
                                    _ customTextFieldView: CustomTextFieldView) {
        guard let customTextFieldData,
              let icon = customTextFieldData.leadingIcon else { return }
        customTextFieldView.setLeadingIcon(icon)
    }

    private func setTexfieldEditStatus(customTextFieldData: CustomTextFieldData?,
                                       _ customTextFieldView: CustomTextFieldView) {
        guard let customTextFieldData else { return }
        if !customTextFieldData.isEditable { customTextFieldView.setDisabled() }
    }

    private func setTextfieldActions(customTextFieldData: CustomTextFieldData?,
                                     _ customTextFieldView: CustomTextFieldView) {
        guard let customTextFieldData else { return }

        switch customTextFieldData.textFieldType {
        case .customPicker, .customSearchVet:
            customTextFieldView.set(type: customTextFieldData.textFieldType,
                                    config: TextFieldConfig(title: customTextFieldData.title),
                                    actions: TextFieldActions(fullButtonAction: {
                customTextFieldData.didTappedCustomTextfieldOption?()
            }))
        default:
            customTextFieldView.set(type: customTextFieldData.textFieldType,
                                    config: TextFieldConfig(title: customTextFieldData.title),
                                    actions: TextFieldActions(didChangeAction: { [weak self] text in
                guard self != nil else { return }
                customTextFieldData.valueDidChange?(text)
            }))
        }
    }
}

// MARK: - Create Checkbox
extension FormularySectionView {

    func createCheckboxFields(checkboxListData: CheckboxListLabelData?) {
        guard let checkboxListData else { return }

        let checkboxList = CheckboxOptionListView()
        checkboxList.setup(checkboxListData: checkboxListData,
                           allSelectedCheckbox: { [weak self] selectedOptions in
            guard self != nil else { return }
            checkboxListData.optionsSelected(selectedOptions)
        })
        stackView.addArrangedSubview(checkboxList)
    }
}
