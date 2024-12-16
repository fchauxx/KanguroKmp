import Foundation
import KanguroSharedDomain
import KanguroUserDomain
import KanguroNetworkDomain

public struct BankAccountMapper: ModelMapper {
    public typealias T = BankAccount

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteBankAccount = input as? RemoteBankAccount else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        var accountType: AccountType? = nil
        if let remoteAccountType: RemoteAccountType = input.accountType {
            accountType = AccountType(rawValue: remoteAccountType.rawValue)
        }
        let bankAccount = BankAccount(
            accountNumber: input.accountNumber,
            routingNumber: input.routingNumber,
            bankName: input.bankName,
            accountType: accountType
        )
        guard let result: T = bankAccount as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

extension BankAccountMapper {
    
    public static func reverseMap(input: BankAccount?) -> RemoteBankAccount? {
        
        guard let input else { return nil }
        var remoteAccountType: RemoteAccountType? = nil
        if let accountType: AccountType = input.accountType {
            remoteAccountType = RemoteAccountType(rawValue: accountType.rawValue)
        }
        return RemoteBankAccount(
            accountNumber: input.accountNumber,
            routingNumber: input.routingNumber,
            bankName: input.bankName,
            accountType: remoteAccountType
        )
    }
}
