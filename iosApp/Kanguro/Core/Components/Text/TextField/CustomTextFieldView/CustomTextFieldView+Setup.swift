import UIKit

// MARK: - Setup
extension CustomTextFieldView {
    
    func setLayout() {
        
        guard let type = type else { return }
        if type.isLogin && isTitleEmpty {
            titleLabel.text = type.title
            set(placeholder: type.placeholder)
        }
        
        resetTextField()
        
        switch type {
        case .email:
            guard let icon = UIImage(named: "ic-mail") else { return }
            setLeadingIcon(icon)
            self.type = .email
            textField.keyboardType = .emailAddress
        case .password:
            guard let leadingIcon = UIImage(named: "ic-lock"),
                  let traillingIcon = UIImage(named: "ic-eye") else { return }
            setTraillingIcon(traillingIcon)
            setLeadingIcon(leadingIcon)
            traillingButtonAction = secureTextEntry
            textField.isSecureTextEntry = true
        case .picker:
            guard let icon = UIImage(systemName: "chevron.down") else { return }
            setTraillingIcon(icon)
            setupPicker()
        case .calendarChatbot, .calendar:
            setupDatePicker()
            setDatePickerRange(maxDate: Date())
        case .search:
            guard let icon = UIImage(systemName: "magnifyingglass") else { return }
            setTraillingIcon(icon)
            fullButton.isHidden = true
        case .customSearchVet:
            guard let icon = UIImage(systemName: "magnifyingglass") else { return }
            setTraillingIcon(icon)
            fullButton.isHidden = false
        case .cellphone:
            textField.setPhoneFormattedText()
            textField.keyboardType = .phonePad
        case .bankAccount, .bankRoutingNumber:
            textField.keyboardType = .phonePad
        case .chatbot:
            titleLabel.isHidden = true
            textFieldView.cornerRadius = 16
            textFieldView.borderColor = .secondaryLight
            traillingIconDistanceConstraint.constant = 0
            textField.keyboardType = .default
        case .currencyChatbot, .defaultCurrency:
            textField.keyboardType = .decimalPad
            textField.set(textMask: CurrencyTextMask())
        case .dataFilter:
            guard let icon = UIImage(systemName: "magnifyingglass") else { return }
            setTraillingIcon(icon)
            textField.keyboardType = .default
        case .customPicker:
            guard let icon = UIImage(systemName: "chevron.down") else { return }
            setTraillingIcon(icon)
            fullButton.isHidden = false
        default:
            textField.keyboardType = .default
        }
        if type.isChatbot { setupChatbotCasesConfig() }
    }
    
    func setupTraillingButtonActionConfigCall() {
        if type != .calendarChatbot {
            textFieldDidEndEditingAction = { [weak self] in
                guard let self = self else { return }
                if !self.textField.isEmpty {
                    self.traillingButtonAction()
                }
            }
        }
    }
    
    func setupChatbotCasesConfig() {
        guard let icon = UIImage(named: "ic-send") else { return }
        setTraillingIcon(icon)
        textFieldView.backgroundColor = .white
        titleLabel.isHidden = true
        textFieldViewHeightConstraint.constant = 32
        textFieldHeightConstraint.constant = 32
        setupTraillingButtonActionConfigCall()
    }
    
    func setup(actions: TextFieldActions, and config: TextFieldConfig) {
        
        guard let type = type else { return }
        
        var actions = actions
        
        self.textFieldDidBeginEditingAction = actions.textFieldDidBeginEditingAction
        self.textFieldDidEndEditingAction = actions.textFieldDidEndEditingAction
        self.textFieldDidChangeAction = actions.didChangeAction
        
        actions.textFieldDidBeginEditingAction = textFieldDidBeginEditing
        actions.textFieldDidEndEditingAction = textFieldDidEndEditing
        
        self.textField.setup(type: type, config: config, actions: actions)
        self.textField.keyboardType = type.keyboardType
        
        self.leadingButtonAction = actions.leadingButtonAction
        self.traillingButtonAction = actions.trailingButtonAction
        self.fullButtonAction = actions.fullButtonAction
        self.pickerSelectedAction = actions.pickerSelectedAction
    }
    
    func setupPicker() {
        picker.delegate = self
        textField.inputView = picker
        traillingButtonAction = { [weak self] in
            guard let self = self else { return }
            self.textField.becomeFirstResponder()
        }
    }
    
    func setupDatePicker() {
        datePicker.preferredDatePickerStyle = .wheels
        datePicker.datePickerMode = .date
        datePicker.addTarget(self, action: #selector(handleDatePicker(sender:)), for: .allEvents)
        textField.inputView = datePicker
        textFieldDidEndEditingAction = { [weak self] in
            guard let self = self else { return }
            self.handleDatePicker(sender: self.datePicker)
            self.traillingButtonAction()
        }
    }
    
    func setTraillingIcon(_ icon: UIImage) {
        traillingIconView.isHidden = false
        traillingIconImageView.image = icon
    }
    
    func setLeadingIcon(_ icon: UIImage) {
        leadingIconView.isHidden = false
        leadingIconImageView.image = icon
    }
}
