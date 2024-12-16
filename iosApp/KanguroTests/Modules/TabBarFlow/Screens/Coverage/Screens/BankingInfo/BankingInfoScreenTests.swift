@testable import Kanguro
import XCTest
import SwiftUI

class BankingInfoScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var vc: BankingInfoViewController!
    var viewModel: BankingInfoViewModel!
    let bankName = "bank"
    let data = FilterData(id: 1, title: "bank")
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        vc = BankingInfoViewController()
        viewModel = BankingInfoViewModel(type: .edit)
        viewModel.bankAccount.bankName = bankName
        viewModel.bankDataList = [data]
        vc.viewModel = viewModel
        vc.loadView()
        vc.viewDidLoad()
    }
}

// MARK: - View State
extension BankingInfoScreenTests {
    
    func testChangedDataChangedState() {
        viewModel.isEditingBank = true
        vc.changed(state: .dataChanged)
        XCTAssertFalse(vc.dataFilterView.isHidden)
    }
    
    func testChangedGetBanksSucceededState() {
        vc.changed(state: .getBanksSucceeded)
        XCTAssertEqual(vc.dataFilterView.filteredData.first?.title,
                       data.title)
    }
    
    func testChangedGetBankAccountsSucceededState() {
        vc.changed(state: .getBankAccountSucceeded)
        XCTAssertEqual(vc.bankOptionsTextFieldView.textField.text, bankName)
    }
}

// MARK: - Setup
extension BankingInfoScreenTests {
    
    func testSetupLabels() {
        XCTAssertEqual(vc.reimbursementTitleLabel.text,
                       "bankingInfo.reimbursement.label".localized)
    }
    
    func testSetupButtons() {
        XCTAssertFalse(vc.saveButton.isEnabled)
        vc.saveButton.sendActions(for: .touchUpInside)
        XCTAssertEqual(viewModel.state, .loading)
    }
    
    func testSetupTextFields() {
        XCTAssertEqual(vc.bankOptionsTextFieldView.type,
                       .dataFilter)
    }
    
    func testSetupSelectionViews() {
        XCTAssertTrue(vc.checkingSelectionView.data?.isSelected ?? false)
    }
    
    func testUpdateBankNameViewsConfig() {
        vc.updateBankNameViewsConfig()
        XCTAssertEqual(vc.bankOptionsTextFieldView.textField.text, bankName)
    }
    
    func testShowDataFilterView() {
        vc.showDataFilterView()
        XCTAssertFalse(vc.dataFilterView.isHidden)
    }
    
    func testUpdateBankName() {
        vc.updateBankName(name: bankName)
        XCTAssertEqual(viewModel.bankAccount.bankName, bankName)
    }
    
    func testUpdateAccountViews() {
        vc.updateAccountViews()
        XCTAssertEqual(vc.bankOptionsTextFieldView.textField.text, bankName)
    }
    
    func testSetButtonEnabledByValidAccount() {
        vc.setButtonEnabledByValidAccount()
        XCTAssertEqual(vc.bankOptionsTextFieldView.textField.text, bankName)
        XCTAssertFalse(vc.saveButton.isEnabled)
    }
    
    func testSetupDataFilterView() {
        vc.setupDataFilterView()
        XCTAssertEqual(vc.dataFilterView.filteredData.first?.title,
                       data.title)
        
        vc.dataFilterView.didFinishAction(data.title)
        XCTAssertEqual(viewModel.bankAccount.bankName, data.title)
    }
    
    func testSetupActions() {
        //saving
        vc.didTapSelectionViewAction(1)
        XCTAssertFalse(vc.checkingSelectionView.data?.isSelected ?? true)
        //checking
        vc.didTapSelectionViewAction(0)
        XCTAssertTrue(vc.checkingSelectionView.data?.isSelected ?? false)
    }
}

// MARK: - View Model
extension BankingInfoScreenTests {
    
    func testUpdateRouting() {
        let routing = "routing"
        viewModel.update(routing: routing)
        XCTAssertEqual(routing, viewModel.bankAccount.routingNumber)
    }
    
    func testUpdateAccount() {
        let account = "1234"
        viewModel.update(account: account)
        XCTAssertEqual(account, viewModel.bankAccount.accountNumber)
    }
}
