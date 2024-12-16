//
//  ForceUpdatePasswordViewController.swift
//  Kanguro
//
//  Created by Rodrigo Okido on 10/11/22.
//

import UIKit

class ForceUpdatePasswordViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: ForceUpdatePasswordViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet var passwordChangeDescription: CustomLabel!
    @IBOutlet private var firstPasswordInstruction: TopicLabelView!
    @IBOutlet private var secondPasswordInstruction: TopicLabelView!
    @IBOutlet private var thirdPasswordInstruction: TopicLabelView!
    @IBOutlet private var fourthPasswordInstruction: TopicLabelView!
    @IBOutlet var newPasswordTextfield: CustomTextFieldView!
    @IBOutlet var confirmNewPasswordTextfield: CustomTextFieldView!
    @IBOutlet private var savePasswordButtton: CustomButton!
    
    // MARK: - Actions
    var goToRootAction: SimpleClosure = {}
    var saveNewPasswordAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension ForceUpdatePasswordViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        self.hideKeyboardWhenTappedAround()
        observe(viewModel.$state) { [weak self] state in
            guard let self else { return }
            self.changed(state: state)
        }
    }
}

// MARK: - View State
extension ForceUpdatePasswordViewController {
    
    func changed(state: ForceChangePasswordViewState) {
        
        savePasswordButtton.isLoading(false)
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            setupLayout()
        case .dataChanged:
            savePasswordButtton.isEnabled(viewModel.isValidPassword)
        case .loading:
            savePasswordButtton.isLoading(true)
        case .requestFailed:
            showSimpleAlert(message: viewModel.requestError)
        case .requestSucceeded:
            showActionAlert(message: "password.success.alert".localized, action: saveNewPasswordAction)
        }
    }
}

// MARK: - Setup
extension ForceUpdatePasswordViewController {
    
    private func setupLayout() {
        setupLabels()
        setupButtons()
        setupTextFields()
    }
    
    func setupLabels() {
        titleLabel.set(text: "forceNewPassword.letstart.label".localized,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
        passwordChangeDescription.set(text: "forceNewPassword.description.label".localized,
                                      style: TextStyle(color: .secondaryDarkest, weight: .bold))
        setupPasswordInstructionLabels()
    }
    
    func setupPasswordInstructionLabels() {
        let passwordFirstCondition = "forceNewPassword.firstPasswordCondition.label".localized
        let passwordSecondCondition = "forceNewPassword.secondPasswordCondition.label".localized
        let passwordThirdCondition = "forceNewPassword.thirdPasswordCondition.label".localized
        let passwordFourthCondition = "forceNewPassword.fourthPasswordCondition.label".localized
        let firstCondition = "forceNewPassword.firstCondition.label".localized
        let secondCondition = "forceNewPassword.secondCondition.label".localized
        let thirdCondition = "forceNewPassword.thirdCondition.label".localized
        let fourthCondition = "forceNewPassword.fourthCondition.label".localized
        
        
        firstPasswordInstruction.setup(data: TopicLabelViewData(title: passwordFirstCondition,
                                                                highlightedTitle: firstCondition,
                                                                style: .highlighted(color: .secondaryDark)))
        secondPasswordInstruction.setup(data: TopicLabelViewData(title: passwordSecondCondition,
                                                                 highlightedTitle: secondCondition,
                                                                 style: .highlighted(color: .secondaryDark)))
        thirdPasswordInstruction.setup(data: TopicLabelViewData(title: passwordThirdCondition,
                                                                highlightedTitle: thirdCondition,
                                                                style: .highlighted(color: .secondaryDark)))
        fourthPasswordInstruction.setup(data: TopicLabelViewData(title: passwordFourthCondition,
                                                                 highlightedTitle: fourthCondition,
                                                                 style: .highlighted(color: .secondaryDark)))
    }
    
    func setupButtons() {
        savePasswordButtton.set(title: "forceNewPassword.save.label".localized, style: .primary)
        savePasswordButtton.isEnabled(false)
        savePasswordButtton.onTap { [weak self] in
            guard let self else { return }
            self.viewModel.updatePassword()
        }
    }
    
    func setupTextFields() {
        newPasswordTextfield.textField.becomeFirstResponder()
        newPasswordTextfield.set(type: .password,
                                 actions: TextFieldActions(didChangeAction: update(newPassword:)))
        newPasswordTextfield.set(title: "forceNewPassword.password.textfield".localized)
        
        confirmNewPasswordTextfield.set(type: .password,
                                        actions: TextFieldActions(didChangeAction: update(repeatedPassword:)))
        confirmNewPasswordTextfield.set(title: "forceNewPassword.confirmPasword.textfield".localized)
    }
}

// MARK: - Updates
extension ForceUpdatePasswordViewController {
    
    func update(newPassword: String) {
        viewModel.update(newPassword: newPassword)
    }
    
    func update(repeatedPassword: String) {
        viewModel.update(repeatedPassword: repeatedPassword)
    }
}

// MARK: - IB Actions
extension ForceUpdatePasswordViewController {
    
    @IBAction func goToRootTouchUpInside(_ sender: UIButton) {
        goToRootAction()
    }
}
