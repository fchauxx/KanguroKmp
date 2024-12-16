import Foundation
import KanguroUserDomain
import KanguroUserData

extension BankAccount {
    // MARK: - Computed Properties
    var jsonFormatted: String? {
        do {
            let remoteBankAccount = BankAccountMapper.reverseMap(input: self)
            let jsonData = try JSONEncoder().encode(remoteBankAccount)
            let jsonString = String(data: jsonData, encoding: .utf8)
            return jsonString
        } catch {
            return nil
        }
    }
}
