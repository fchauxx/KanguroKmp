import UIKit

enum CheckboxOptionListOrientation {

    case vertical
    case horizontal
}

class CheckboxOptionListView: BaseView, NibOwnerLoadable {

    @IBOutlet private var stackView: UIStackView!
    @IBOutlet private var title: CustomLabel!

    // MARK: - Stored Properties
    private var checkboxListData: CheckboxListLabelData
    var checkboxOptionList: [CheckboxOptionView]
    var selectedOptions: [CheckboxLabelData]

    // MARK: - Actions
    var userAllSelectedCheckbox: SelectedCheckboxDataClosure = { _ in }
    var didTapCheckboxAction: CheckboxDataClosure = { _ in }

    // MARK: - Initializers
    required init?(coder: NSCoder) {
        checkboxListData = CheckboxListLabelData(checkboxQuestionTitle: "",
                                                 isMultipleSelectionEnabled: false,
                                                 options: [],
                                                 optionsSelected: { _ in })
        checkboxOptionList = []
        selectedOptions = []
        super.init(coder: coder)
        self.loadNibContent()
    }
    override init(frame: CGRect) {
        checkboxListData = CheckboxListLabelData(checkboxQuestionTitle: "",
                                                 isMultipleSelectionEnabled: false,
                                                 options: [],
                                                 optionsSelected: { _ in })
        checkboxOptionList = []
        selectedOptions = []
        super.init(frame: frame)
        self.loadNibContent()
    }
}

// MARK: - Setup
extension CheckboxOptionListView {

    func setup(checkboxListData: CheckboxListLabelData,
               selectedOptions: [CheckboxLabelData] = [],
               allSelectedCheckbox: @escaping SelectedCheckboxDataClosure) {

        self.checkboxListData = checkboxListData
        self.selectedOptions = selectedOptions
        self.userAllSelectedCheckbox = allSelectedCheckbox

        setupActions()
        setupLabels(title: checkboxListData.checkboxQuestionTitle)
        setupViews()
        setupCheckboxList(optionsOrientation: checkboxListData.listOrientation)
    }

    func setupLabels(title: String) {
        self.title.set(text: title, style: TextStyle(color: .secondaryDark, size: .p12))
    }

    func setupViews() {
        stackView.axis = (checkboxListData.listOrientation == .horizontal) ? .horizontal : .vertical
    }

    func setupCheckboxOptions() {
        for checkboxItem in checkboxListData.options {
            let checkboxOption = CheckboxOptionView()
            checkboxOption.setup(data: checkboxItem,
                                 isMultipleSelectionEnabled: checkboxListData.isMultipleSelectionEnabled)
            checkboxOptionList.append(checkboxOption)
        }
    }

    func setupCheckboxList(optionsOrientation: CheckboxOptionListOrientation) {
        stackView.removellArrangedSubviews()
        setupCheckboxOptions()
        for checkboxItem in checkboxOptionList {
            checkboxItem.setup(data: checkboxItem.data,
                               isMultipleSelectionEnabled: checkboxListData.isMultipleSelectionEnabled)
            stackView.addArrangedSubview(checkboxItem)
            checkboxItem.didTapCheckboxAction = didTapCheckboxAction
        }
    }

    func setupActions() {
        didTapCheckboxAction = { [weak self] optionItem in
            guard let self else { return }
            self.setOption(optionItem)
        }
    }
}

// MARK: - Public Methods
extension CheckboxOptionListView {
    
    func setOption(_ option: CheckboxLabelData) {
        if checkboxListData.isMultipleSelectionEnabled {
            updateMultiSelectedOptions(option)
        } else {
            updateSingleSelectedOption(option)
        }
        userAllSelectedCheckbox(selectedOptions)
    }

    func updateMultiSelectedOptions(_ option: CheckboxLabelData) {
        if option.isSelected {
            selectedOptions.append(option)
        } else {
            selectedOptions = selectedOptions.filter { $0.id != option.id }
        }
    }

    func updateSingleSelectedOption(_ option: CheckboxLabelData) {
        if option.isSelected {
            selectedOptions = [option]
            guard let selectedBox = selectedOptions.first else { return }
            checkboxOptionList = checkboxOptionList.map { item in
                if item.data.id != selectedBox.id {
                    item.deselectIfChecked()
                }
                return item
            }
        } else {
            selectedOptions.removeAll()
        }
    }
}

