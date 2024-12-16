import UIKit
import KanguroUserDomain

class BankingInfoViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: BankingInfoViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet var reimbursementTitleLabel: CustomLabel!
    @IBOutlet private var bankingTitleLabel: CustomLabel!
    
    @IBOutlet private var accountTypeLabel: CustomLabel!
    @IBOutlet var checkingSelectionView: SelectionView!
    @IBOutlet private var savingSelectionView: SelectionView!
    
    @IBOutlet var bankOptionsTextFieldView: CustomTextFieldView!
    @IBOutlet private var routingTextFieldView: CustomTextFieldView!
    @IBOutlet private var accountTextFieldView: CustomTextFieldView!
    
    @IBOutlet var dataFilterView: DataFilterView!
    
    @IBOutlet var saveButton: CustomButton!
    
    // MARK: - Computed Properties
    private var isValidTextFields: Bool {
        return (bankOptionsTextFieldView.textField.isContentValid &&
                routingTextFieldView.textField.isContentValid &&
                accountTextFieldView.textField.isContentValid)
    }
    
    // MARK: Actions
    var backAction: SimpleClosure = {}
    var didTapSelectionViewAction: IntClosure = { _ in }
    var didSaveBankAccountAction: BankAccountClosure = { _ in }
}

// MARK: - Life Cycle
extension BankingInfoViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        hideKeyboardWhenTappedAround()
        setupLayout()
        observe(viewModel.$state) { [weak self] state in
            guard let self else { return }
            self.changed(state: state)
        }
    }
}

// MARK: - View State
extension BankingInfoViewController {
    
    func changed(state: BankingInfoViewState) {
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            viewModel.getBankAccount()
            viewModel.getBanks()
        case .loading:
            showLoadingView(shouldAnimate: false)
        case .dataChanged:
            saveButton.isEnabled(isValidTextFields)
            dataFilterView.isHidden = !viewModel.isEditingBank
            updateBankNameViewsConfig()
        case .requestFailed:
            hideLoadingView()
            showActionAlert(message: viewModel.requestError,
                            action: backAction)
        case .getBanksSucceeded:
            setupDataFilterView()
        case .getBankAccountSucceeded:
            hideLoadingView()
            setButtonEnabledByValidAccount()
            updateAccountViews()
        case .putBankAccountSucceeded:
            hideLoadingView()
            switch viewModel.type {
            case .edit:
                showActionAlert(message: "serverSuccess.default".localized,
                                action: backAction)
            case .chatbot:
                self.backAction()
                self.didSaveBankAccountAction(self.viewModel.bankAccount)
            }
        }
    }
}

// MARK: - Setup
extension BankingInfoViewController {
    
    private func setupLayout() {
        setupActions()
        setupLabels()
        setupButtons()
        setupTextFields()
        setupSelectionViews(.checking)
    }
    
    private func setupLabels() {
        reimbursementTitleLabel.set(text: "bankingInfo.reimbursement.label".localized,
                                    style: TextStyle(color: .secondaryMedium, weight: .bold, size: .p16))
        bankingTitleLabel.set(text: "bankingInfo.banking.label".localized,
                              style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h24, font: .raleway))
        accountTypeLabel.set(text: "bankingInfo.accountType.label".localized,
                             style: TextStyle(color: .secondaryMedium, size: .p12))
    }
    
    private func setupTextFields() {
        bankOptionsTextFieldView.set(
            type: .dataFilter,
            actions: TextFieldActions(
                didChangeAction: updateBankName(name:),
                textFieldDidBeginEditingAction: { [weak self] in
                    guard let self else { return }
                    self.removeTextFieldsMasks()
                    self.showDataFilterView()
                }
            )
        )

        bankOptionsTextFieldView.traillingButtonAction = { [weak self] in
            guard let self else { return }
            self.dataFilterView.isHidden = false
        }

        bankOptionsTextFieldView.set(title: "bankingInfo.bankName.label".localized)
        
        routingTextFieldView.set(
            type: .bankRoutingNumber,
            actions: TextFieldActions(
                didChangeAction: viewModel.update(routing:),
                textFieldDidBeginEditingAction: { [weak self] in
                    guard let self else { return }
                    self.removeTextFieldsMasks()
                }
            )
        )

        routingTextFieldView.set(title: "bankingInfo.routing.label".localized)
        
        accountTextFieldView.set(
            type: .bankAccount,
            actions: TextFieldActions(
                didChangeAction: viewModel.update(account:),
                textFieldDidBeginEditingAction: { [weak self] in
                    guard let self else { return }
                    self.removeTextFieldsMasks()
                }
            )
        )

        accountTextFieldView.set(title: "bankingInfo.account.label".localized)
    }

    private func removeTextFieldsMasks() {
        self.accountTextFieldView.set(text: self.viewModel.bankAccount.accountNumber ?? "")
        self.routingTextFieldView.set(text: self.viewModel.bankAccount.routingNumber ?? "")
    }

    func setupDataFilterView() {
        dataFilterView.setup(data: viewModel.bankDataList, hasExternalTextField: true)
        dataFilterView.didFinishAction = { [weak self] bankName in
            guard let self else { return }
            self.viewModel.update(bank: bankName, isEditingBank: false)
        }
    }
    
    private func setupActions() {
        didTapSelectionViewAction = { [weak self] tag in
            guard let self else { return }
            let accountType: AccountType = (tag == 0) ? .checking : .saving
            self.removeTextFieldsMasks()
            self.setupSelectionViews(accountType)
            self.viewModel.update(accountType: accountType)
        }
    }
    
    private func setupSelectionViews(_ accountType: AccountType) {
        checkingSelectionView.setup(data: SelectionViewData(title: "bankingInfo.checking.label".localized,
                                                            didTapButtonAction: didTapSelectionViewAction))
        savingSelectionView.setup(data: SelectionViewData(title: "bankingInfo.saving.label".localized,
                                                          didTapButtonAction: didTapSelectionViewAction))
        switch accountType {
        case .checking:
            checkingSelectionView.isSelected(true)
            savingSelectionView.isSelected(false)
        case .saving:
            checkingSelectionView.isSelected(false)
            savingSelectionView.isSelected(true)
        }
    }
    
    private func setupButtons() {
        let title = (viewModel.type == .edit) ? "bankingInfo.save.label".localized : "bankingInfo.continue.label".localized
        saveButton.set(title: title,
                       style: .primary)
        saveButton.onTap { [weak self] in
            guard let self else { return }
            self.viewModel.updateUserBankAccount()
        }
        saveButton.isEnabled(false)
    }
}

// MARK: - Private Methods
extension BankingInfoViewController {
    
    func updateAccountViews() {
        setupSelectionViews(viewModel.bankAccount.accountType ?? .checking)
        routingTextFieldView.set(text: viewModel.bankAccount.routingNumber?.getBankAccountFormatted ?? "")
        accountTextFieldView.set(text: viewModel.bankAccount.accountNumber?.getBankAccountFormatted ?? "")
        bankOptionsTextFieldView.set(text: viewModel.bankAccount.bankName ?? "")
    }
    
    func updateBankNameViewsConfig() {
        guard let bankName = viewModel.bankAccount.bankName else { return }
        dataFilterView.update(text: bankName)
        bankOptionsTextFieldView.textField.text = bankName
    }
    
    func showDataFilterView() {
        dataFilterView.isHidden = false
    }
    
    func updateBankName(name: String) {
        viewModel.update(bank: name, isEditingBank: true)
    }
    
    func setButtonEnabledByValidAccount() {
        bankOptionsTextFieldView.set(text: viewModel.bankAccount.bankName ?? "")
        routingTextFieldView.set(text: viewModel.bankAccount.routingNumber ?? "")
        accountTextFieldView.set(text: viewModel.bankAccount.accountNumber ?? "")
        saveButton.isEnabled(viewModel.type == .chatbot && isValidTextFields)
    }
}

// MARK: - IB Actions
extension BankingInfoViewController {
    
    @IBAction private func closeButtonTouchUpInside(_ sender: UIButton) {
        backAction()
    }
}
