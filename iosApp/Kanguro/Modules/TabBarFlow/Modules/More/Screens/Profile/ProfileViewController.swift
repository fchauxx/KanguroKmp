
import UIKit

class ProfileViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: ProfileViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet var editProfileAccordionTextFieldView: AccordionTextFieldView!
    @IBOutlet var editPasswordAccordionTextFieldView: AccordionTextFieldView!
    @IBOutlet var deleteAccountAccordionCardView: AccordionDeleteUserAccountView!
    @IBOutlet var deletePolicyAccordionCardView: AccordionDeleteUserAccountView!
    
    // MARK: - Computed Properties
    var accordionViews: [AccordionViewProtocol] {
        return [editProfileAccordionTextFieldView,
                editPasswordAccordionTextFieldView,
                deleteAccountAccordionCardView,
                deletePolicyAccordionCardView]
    }
    var accordionTextFieldViews: [AccordionTextFieldView] {
        return [editProfileAccordionTextFieldView,
                editPasswordAccordionTextFieldView]
    }
    
    // MARK: - Actions
    var goBackAction: SimpleClosure = {}
    var notificateDisappearingAction: SimpleClosure = {}
    var logoutAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension ProfileViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
    
    //TODO: Erase ASAP
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        notificateDisappearingAction()
    }
}

// MARK: - View State
extension ProfileViewController {
    
    func changed(state: ProfileViewState) {
        
        setButtonsLoading(false)
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
        case .loading:
            setButtonsLoading(true)
        case .dataChanged:
            editProfileAccordionTextFieldView.setProfileButtonEnabled()
            editPasswordAccordionTextFieldView.setPasswordButtonEnabled(viewModel.validatePasswordData())
        case .logoutUser:
            logoutAction()
        case .requestFailed:
            showSimpleAlert(message: viewModel.requestError)
        case .requestSucceeded:
            showSimpleAlert(message: "profile.success.alert".localized)
            accordionTextFieldViews.forEach { $0.setButtonsEnabled(false) }
        }
    }
}

// MARK: - Setup
extension ProfileViewController {
    
    private func setupLayout() {
        setupLabels()
        setupActions()
        setupProfileAccordionTextFieldView()
        setupPasswordAccordionTextFieldView()
        setupDeleteAccountAccordionView()
        setupDeletePolicyAccordionView()
    }
    
    private func setupLabels() {
        titleLabel.set(text: "profile.title.label".localized,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h24))
    }
    
    private func setupProfileAccordionTextFieldView() {
        editProfileAccordionTextFieldView.didTapSaveAction = { [weak self] in
            guard let self = self else { return }
            self.viewModel.updateProfileData()
        }
        editProfileAccordionTextFieldView.setup(title: "profile.editProfile.textFieldView".localized)
        editProfileAccordionTextFieldView.addItems(customTextFieldsData: [
            CustomTextFieldData(title: "profile.firstName.label".localized,
                                placeholder: viewModel.user?.givenName,
                                isEditable: false,
                                textFieldType: .default,
                                valueDidChange: { [weak self] firstName in
                                    guard let self = self else { return }
                                    self.viewModel.update(firstName: firstName)
                                }),
            CustomTextFieldData(title: "profile.lastName.label".localized,
                                placeholder: viewModel.user?.surname,
                                isEditable: false,
                                textFieldType: .default,
                                valueDidChange: { [weak self] lastName in
                                    guard let self = self else { return }
                                    self.viewModel.update(lastName: lastName)
                                }),
            CustomTextFieldData(title: "profile.email.label".localized,
                                placeholder: viewModel.user?.email,
                                isEditable: false,
                                textFieldType: .default),
            CustomTextFieldData(title: "profile.phone.label".localized,
                                placeholder: viewModel.user?.phone,
                                textFieldType: .cellphone,
                                valueDidChange: { [weak self] phoneNumber in
                                    guard let self = self else { return }
                                    self.viewModel.update(phoneNumber: phoneNumber)
                                }),
        ])
    }
    
    private func setupPasswordAccordionTextFieldView() {
        editPasswordAccordionTextFieldView.didTapSaveAction = { [weak self] in
            guard let self = self else { return }
            self.viewModel.updatePassword()
        }
        editPasswordAccordionTextFieldView.setup(title: "profile.editPassword.textFieldView".localized)
        editPasswordAccordionTextFieldView.addItems(customTextFieldsData: [
            CustomTextFieldData(title: "profile.oldPassword.label".localized,
                                textFieldType: .password,
                                valueDidChange: { [weak self] oldPassword in
                                    guard let self = self else { return }
                                    self.viewModel.update(oldPassword: oldPassword)
                                }),
            CustomTextFieldData(title: "profile.newPassword.label".localized,
                                textFieldType: .password,
                                valueDidChange: { [weak self] password in
                                    guard let self = self else { return }
                                    self.viewModel.update(password: password)
                                }),
            CustomTextFieldData(title: "profile.repeatPassword.label".localized,
                                textFieldType: .password,
                                valueDidChange: { [weak self] repeatedPassword in
                                    guard let self = self else { return }
                                    self.viewModel.update(repeatedPassword: repeatedPassword)
                                }),
        ])
    }
    
    private func setupDeleteAccountAccordionView() {
        deleteAccountAccordionCardView.viewModel = AccordionDeleteUserAccountViewModel()
        deleteAccountAccordionCardView.logoutAction = viewModel.logout
        deleteAccountAccordionCardView.setup(title: "profile.account.label".localized, type: .account)
        deleteAccountAccordionCardView.showLoadingAction = { [weak self] in
            guard let self = self else { return }
            self.showLoadingView(shouldAnimate: false)
        }
        deleteAccountAccordionCardView.hideLoadingAction = { [weak self] in
            guard let self = self else { return }
            self.hideLoadingView()
        }
    }
    
    private func setupDeletePolicyAccordionView() {
        deletePolicyAccordionCardView.viewModel = AccordionDeleteUserAccountViewModel()
        deletePolicyAccordionCardView.setup(title: "profile.other.label".localized, type: .policy)
        deletePolicyAccordionCardView.showLoadingAction = { [weak self] in
            guard let self = self else { return }
            self.showLoadingView(shouldAnimate: false)
        }
        deletePolicyAccordionCardView.hideLoadingAction = { [weak self] in
            guard let self = self else { return }
            self.hideLoadingView()
        }
    }
    
    private func setButtonsLoading(_ isLoading: Bool) {
        editProfileAccordionTextFieldView.setButtonLoading(isLoading)
        editPasswordAccordionTextFieldView.setButtonLoading(isLoading)
    }
    
    private func setupActions() {
        for item in accordionViews {
            item.didTapExpandAction = { [weak self] in
                guard let self = self else { return }
                self.closeAccordionViews(openView: item)
            }
        }
    }
    
    func closeAccordionViews(openView: AccordionViewProtocol) {
        var accordionViews = accordionViews
        if let index = accordionViews.firstIndex(where: { $0 === openView }) {
            accordionViews.remove(at: index)
        }
        if openView.isExpanded {
            accordionViews.forEach {
                if $0.isExpanded { $0.close() }
            }
        }
    }
}

// MARK: - IB Actions
extension ProfileViewController {
    
    @IBAction private func closeTouchUpInside(_ sender: UIButton) {
        goBackAction()
    }
}
