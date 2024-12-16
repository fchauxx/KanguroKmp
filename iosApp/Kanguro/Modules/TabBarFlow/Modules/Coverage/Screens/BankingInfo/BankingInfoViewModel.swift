import UIKit
import KanguroUserDomain
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain

enum BankingInfoViewState {
    
    case started
    case dataChanged
    case loading
    case requestFailed
    case getBanksSucceeded
    case getBankAccountSucceeded
    case putBankAccountSucceeded
}

enum BankingInfoType {
    
    case edit
    case chatbot
}

class BankingInfoViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var getUserBankAccountService: GetUserBankAccountUseCaseProtocol
    @LazyInjected var updateUserBankAccountService: UpdateUserBankAccountUseCaseProtocol
    @LazyInjected var getBanksService: GetBanksUseCaseProtocol
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    
    // MARK: - Published Properties
    @Published var state: BankingInfoViewState = .started
    
    // MARK: - Stored Properties
    var bankDataList: [FilterData] = []
    var bankAccount = BankAccount()
    var type: BankingInfoType
    var requestError = ""
    var isEditingBank: Bool = false
    
    // MARK: - Initializers
    init(type: BankingInfoType) {
        self.type = type
    }
}

// MARK: - Analytics
extension BankingInfoViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .BankingInfo)
    }
}

// MARK: - Public Methods
extension BankingInfoViewModel {
    
    func update(accountType: AccountType) {
        self.bankAccount.accountType = accountType
        self.state = .dataChanged
    }
    
    func update(bank: String, isEditingBank: Bool) {
        self.bankAccount.bankName = bank
        self.isEditingBank = isEditingBank
        self.state = .dataChanged
    }
    
    func update(routing: String) {
        self.bankAccount.routingNumber = routing
        self.state = .dataChanged
    }
    
    func update(account: String) {
        self.bankAccount.accountNumber = account
        self.state = .dataChanged
    }
    
    func updateBankDataList(bankOptionsList: [BankOption]) {
        for bank in bankOptionsList {
            guard let id = bank.id,
                  let name = bank.name else { return }
            let filterData = FilterData(id: id, title: name)
            bankDataList.append(filterData)
        }
    }
}

// MARK: - Network
extension BankingInfoViewModel {
    
    func getBankAccount() {
        state = .loading
        getUserBankAccountService.execute { response in
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let bankAccount):
                self.bankAccount = bankAccount
                self.state = .getBankAccountSucceeded
            }
        }
    }
    
    func getBanks() {
        state = .loading
        getBanksService.execute { response in
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let bankDataList):
                self.updateBankDataList(bankOptionsList: bankDataList)
                self.state = .getBanksSucceeded
            }
        }
    }
    
    func updateUserBankAccount() {
        state = .loading
        updateUserBankAccountService.execute(bankAccount) { response in
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success:
                self.state = .putBankAccountSucceeded
            }
        }
    }
}
