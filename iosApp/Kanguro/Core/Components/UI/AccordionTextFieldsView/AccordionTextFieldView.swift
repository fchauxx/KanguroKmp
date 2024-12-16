import UIKit

class AccordionTextFieldView: BaseView, NibOwnerLoadable, AccordionViewProtocol {
    
    // MARK: - IB Outlets
    @IBOutlet var stackView: UIStackView!
    @IBOutlet var actionCard: ActionCard!
    @IBOutlet var saveButton: CustomButton!
    
    // MARK: - Actions
    var didTapSaveAction: SimpleClosure = {}
    var didTapExpandAction: SimpleClosure = {}
    
    // MARK: - Stored Properties
    var isExpanded: Bool = false
    var allStackTextFields: [CustomTextFieldView] = []
    var title: String?
    
    // MARK: - Computed Properties
    var isContentValid: Bool {
        return !allStackTextFields.contains(where: { $0.textField.isContentValid == false })
    }
    
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

// MARK: - Life Cycle
extension AccordionTextFieldView {
    
    override func layoutSubviews() {
        super.layoutSubviews()
        setupLayout()
    }
}

// MARK: - Setup
extension AccordionTextFieldView {
    
    private func setupLayout() {
        updateViews()
        setupButtons()
        setupActionCard()
    }
    
    func setupActionCard() {
        guard let title = title else { return }
        actionCard.setup(actionCardData: ActionCardData(leadingTitle: title,
                                                        didTapAction: changeItemsStackStatus,
                                                        viewType: .accordion(fontSize: .p14)),
                         backgroundColor: .white)
    }
    
    func setupButtons() {
        saveButton.set(title: "accordionTextFieldsView.save.button".localized,
                       style: .primary)
        saveButton.isEnabled(false)
        saveButton.onTap { [weak self] in
            guard let self = self else { return }
            self.didTapSaveAction()
        }
    }
}

// MARK: - Private Methods
extension AccordionTextFieldView {
    
    func updateViews() {
        stackView.isHidden = !isExpanded
        saveButton.alpha = (saveButton.isEnabled) ? 1 : 0.5
    }
    
    func changeItemsStackStatus() {
        isExpanded.toggle()
        actionCard.update(isExpanded: isExpanded)
        animateExpansion()
        didTapExpandAction()
    }
    
    private func animateExpansion() {
        saveButton.isHidden = !isExpanded
        UIView.animate(withDuration: 0.5) { [weak self] in
            guard let self = self else { return }
            self.updateViews()
            self.stackView.superview?.layoutIfNeeded()
        }
    }
}

// MARK: - Public Methods
extension AccordionTextFieldView {
    
    func setup(title: String) {
        self.title = title
    }
    
    func setButtonLoading(_ isLoading: Bool) {
        saveButton.isLoading(isLoading)
    }
    
    func close() {
        isExpanded = false
        animateExpansion()
    }
    
    func setProfileButtonEnabled() {
        setButtonsEnabled(isContentValid)
    }
    
    func setPasswordButtonEnabled(_ enabled: Bool) {
        setButtonsEnabled(enabled && isContentValid)
    }
    
    func addItems(customTextFieldsData: [CustomTextFieldData]) {
        for item in customTextFieldsData {
            let customTextFieldView = CustomTextFieldView()
            customTextFieldView.set(title: item.title)
            customTextFieldView.set(text: item.placeholder ?? "")
            customTextFieldView.set(type: item.textFieldType,
                                    config: TextFieldConfig(title: item.title),
                                    actions: TextFieldActions(didChangeAction: { [weak self] text in
                guard self != nil else { return }
                item.valueDidChange?(text)
            }))
            allStackTextFields.append(customTextFieldView)
            if !item.isEditable { customTextFieldView.setDisabled() }
            stackView.addArrangedSubview(customTextFieldView)
        }
        layoutIfNeeded()
    }
    
    func setButtonsEnabled(_ isEnabled: Bool) {
        saveButton.isEnabled(isEnabled)
    }
}
