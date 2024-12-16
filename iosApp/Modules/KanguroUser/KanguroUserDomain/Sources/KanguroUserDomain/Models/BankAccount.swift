import Foundation

public enum AccountType: String {
    
    case checking
    case saving
}

public struct BankAccount: Equatable {
    
    // MARK: - Stored Properties
    public var accountNumber: String?
    public var routingNumber: String?
    public var bankName: String?
    public var accountType: AccountType?
    
    public init(
        accountNumber: String? = nil,
        routingNumber: String? = nil,
        bankName: String? = nil,
        accountType: AccountType? = nil
    ) {
        self.accountNumber = accountNumber
        self.routingNumber = routingNumber
        self.bankName = bankName
        self.accountType = accountType
    }
}
