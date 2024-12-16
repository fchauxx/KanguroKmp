import UIKit

class CustomTextFieldView: UIView, NibOwnerLoadable, FormViewProtocol {
    
    // MARK: - IB Outlets
    @IBOutlet var textFieldView: UIView!
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet var textField: CustomTextField!
    @IBOutlet var errorLabel: CustomLabel!
    
    @IBOutlet var leadingIconView: UIView!
    @IBOutlet var leadingIconImageView: UIImageView!
    @IBOutlet var traillingIconView: UIView!
    @IBOutlet var traillingIconImageView: UIImageView!
    @IBOutlet var fullButton: UIButton!
    @IBOutlet var textFieldHeightConstraint: NSLayoutConstraint!
    @IBOutlet var textFieldViewHeightConstraint: NSLayoutConstraint!
    @IBOutlet var traillingIconDistanceConstraint: NSLayoutConstraint!
    
    // MARK: - Stored Properties
    var type: TextFieldType?
    var title: String?
    lazy var picker = UIPickerView()
    lazy var pickerData: [TextFieldPickerData] = []
    lazy var datePicker = UIDatePicker()
    
    // MARK: - Computed Properties
    var isTitleEmpty: Bool {
        return (title == nil || title == "")
    }
    
    // MARK: - Actions
    var textFieldDidChangeAction: StringClosure = { _ in }
    var textFieldDidBeginEditingAction: SimpleClosure = {}
    var textFieldDidEndEditingAction: SimpleClosure = {}
    var returnKeyAction: SimpleClosure = {}
    var leadingButtonAction: SimpleClosure = {}
    var traillingButtonAction: SimpleClosure = {}
    var fullButtonAction: SimpleClosure = {}
    var pickerSelectedAction: TextFieldPickerDataClosure = { _ in }

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
extension CustomTextFieldView {
    
    override func layoutSubviews() {
        super.layoutSubviews()
        backgroundColor = .clear
    }
}

// MARK: - Public Methods
extension CustomTextFieldView {
    
    func set(type: TextFieldType,
             config: TextFieldConfig = TextFieldConfig(),
             actions: TextFieldActions = TextFieldActions()) {
        
        self.type = type
        set(title: config.title)
        setup(actions: actions, and: config)
        setLayout()
    }
    
    func set(text: String) {
        textField.set(text: text)
    }
    
    func set(placeholder: String, color: UIColor? = .secondaryMedium) {
        textField.set(placeholder: placeholder, color: color)
    }
    
    func set(textMask: TextMask?) {
        textField.set(textMask: textMask)
    }
    
    func set(title: String, color: UIColor = .disabledBlue) {
        self.title = title
        titleLabel.set(text: title, style: TextStyle(color: color))
        titleLabel.isHidden = false
    }
    
    func set(pickerData: [TextFieldPickerData], startWithFirstValue: Bool = false) {
        self.pickerData = pickerData
        if startWithFirstValue {
            if let firstData = pickerData.first {
                textField.text = firstData.title
                pickerSelectedAction(firstData)
            }
        }
        picker.reloadAllComponents()
    }
    
    func setDatePickerRange(minDate: Date? = nil, maxDate: Date? = nil) {
       datePicker.minimumDate = minDate
       datePicker.maximumDate = maxDate
    }
    
    func setDisabled() {
        textField.textColor = .neutralLightest
        textFieldView.backgroundColor = .neutralBackground
        textFieldView.borderColor = .neutralLightest
        textField.isEnabled = false
    }
    
    func clear() {
        textField.text = ""
    }
}

// MARK: - Private Methods
extension CustomTextFieldView {
    
    func resetTextField() {
        textField.inputView = nil
        textField.set(textMask: nil)
        textField.reloadInputViews()
    }
}

// MARK: - UITextFieldDelegate
extension CustomTextFieldView {
    
    func textFieldDidBeginEditing() {
        titleLabel.set(style: TextStyle(color: .secondaryDarkest))
        textFieldView.borderColor = .disabledBlue
        leadingIconImageView.tintColor = .disabledBlue
        if type == .bankAccount { clearBankAccountType() }
        textFieldDidBeginEditingAction()
    }
    
    func textFieldDidEndEditing() {
        titleLabel.set(style: TextStyle(color: .disabledBlue))
        textFieldView.borderColor = .disabledBlue
        leadingIconImageView.tintColor = .disabledBlue
        textFieldDidEndEditingAction()
    }
}

// MARK: - IB Actions
extension CustomTextFieldView {
    
    @IBAction func leadingButtonTouchUpInside(_ sender: UIButton) {
        leadingButtonAction()
    }
    
    @IBAction func traillingButtonTouchUpInside(_ sender: UIButton) {
        if !textField.isEmpty { traillingButtonAction() }
    }
    
    @IBAction func fullButtonTouchUpInside(_ sender: UIButton) {
        fullButtonAction()
    }
}

// MARK: - Actions
extension CustomTextFieldView {
    
    func secureTextEntry() {
        textField.isSecureTextEntry = !textField.isSecureTextEntry
        let image = textField.isSecureTextEntry ? UIImage(named: "ic-eye") : UIImage(named: "ic-eye-slash")
        traillingIconImageView.image = image
    }
    
    func clearBankAccountType() {
            textField.text = ""
            textField.isSecureTextEntry = false
    }
}

// MARK: - UIPickerViewDataSource/UIPickerViewDelegate
extension CustomTextFieldView: UIPickerViewDataSource, UIPickerViewDelegate {
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return pickerData.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if textField.isEmpty, let firstTitle = pickerData.first?.title {
            textField.set(text: firstTitle)
            textFieldDidChangeAction(firstTitle)
        }
        return pickerData[row].title
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        textField.set(text: pickerData[row].title)
        pickerSelectedAction(pickerData[row])
    }
}

// MARK: - DatePicker
extension CustomTextFieldView {
    
    @objc
    func handleDatePicker(sender: UIDatePicker) {
        textField.set(text: sender.date.MMddyyy)
        textFieldDidChangeAction(sender.date.isoString)
    }
}
