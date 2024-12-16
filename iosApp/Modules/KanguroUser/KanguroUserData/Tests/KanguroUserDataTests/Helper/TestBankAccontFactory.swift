import Foundation
import KanguroUserDomain
import KanguroUserData

struct TestBankAccountFactory {
    static func make(
        accountNumber: String? = "1234",
        routingNumber: String? = "5678",
        bankName: String? = "BR Bank",
        accountType: AccountType? = AccountType.checking
    ) -> BankAccount {
        BankAccount(
            accountNumber: accountNumber,
            routingNumber: routingNumber,
            bankName: bankName,
            accountType: accountType
        )
    }

    static func makeRemote(
        accountNumber: String? = "1234",
        routingNumber: String? = "5678",
        bankName: String? = "BR Bank",
        accountType: RemoteAccountType? = .checking
    ) -> RemoteBankAccount {
        RemoteBankAccount(
            accountNumber: accountNumber,
            routingNumber: routingNumber,
            bankName: bankName,
            accountType: .checking
        )
    }
}
