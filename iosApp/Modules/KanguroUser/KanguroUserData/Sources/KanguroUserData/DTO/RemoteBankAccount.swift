import Foundation

public enum RemoteAccountType: String, Codable {
    
    case checking
    case saving
}

public struct RemoteBankAccount: Codable {
    
    // MARK: - Stored Properties
    public var accountNumber: String?
    public var routingNumber: String?
    public var bankName: String?
    public var accountType: RemoteAccountType?
    
    public init(
        accountNumber: String? = nil,
        routingNumber: String? = nil,
        bankName: String? = nil,
        accountType: RemoteAccountType? = nil
    ) {
        self.accountNumber = accountNumber
        self.routingNumber = routingNumber
        self.bankName = bankName
        self.accountType = accountType
    }
}
