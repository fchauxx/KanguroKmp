import Foundation
import KanguroPetDomain

class CheckboxOptionView: BaseView, NibOwnerLoadable {

    // MARK: - IB Outlets
    @IBOutlet private var checkboxView: CheckboxView!
    @IBOutlet private var optionLabel: CustomLabel!

    // MARK: - Stored Properties
    var data: CheckboxLabelData

    // MARK: - Actions
    var didTapCheckboxAction: CheckboxDataClosure = { _ in }

    // MARK: - Computed Properties
    var isCheckboxSelected: Bool {
        return checkboxView.viewModel.isSelected ?? false
    }

    // MARK: - Initializers
    required init?(coder: NSCoder) {
        data = CheckboxLabelData(isSelected: false, option: "option text")
        super.init(coder: coder)
        self.loadNibContent()
    }
    override init(frame: CGRect) {
        data = CheckboxLabelData(isSelected: false, option: "option text")
        super.init(frame: frame)
        self.loadNibContent()
    }
}

// MARK: - Setup
extension CheckboxOptionView {

    func setup(data: CheckboxLabelData, isMultipleSelectionEnabled: Bool) {
        self.data = data
        setupLabels()
        setupCheckBox()
        setupViews(isMultipleSelectionEnabled: isMultipleSelectionEnabled)
    }

    private func setupLabels() {
        optionLabel.set(text: data.option, style: TextStyle(color: .secondaryDark, size: .p12))
    }

    private func setupCheckBox() {
        checkboxView.viewModel = CheckboxViewModel()
        checkboxView.setup(isEditable: true)
        checkboxView.update(didTapAction: { [weak self] selected in
            guard let self else { return }
            self.data.isSelected = !selected
            self.didTapCheckboxAction(self.data)
        })
    }

    private func setupViews(isMultipleSelectionEnabled: Bool) {
        if !isMultipleSelectionEnabled {
            checkboxView.update(isSingleChoice: true)
        }
    }
}

extension CheckboxOptionView {

    func deselectIfChecked() {
        guard let isChecked = checkboxView.viewModel.isSelected else { return }
        if isChecked {
            checkboxView.viewModel.update(isSelected: !isChecked)
        }
    }
}
