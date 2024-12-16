import Foundation
import KanguroSharedData
import KanguroSharedDomain

struct TestBankOptionFactory {
    static func makeRemoteBankOptionsResponse(
        id: Int = 777,
        name: String = "BankTest"
    ) -> [RemoteBankOption] {
        [RemoteBankOption(id: id, name: name)]
    }

    static func makeBankOptionsResponse(
        id: Int = 777,
        name: String = "BankTest"
    ) -> [BankOption] {
        [BankOption(id: id, name: name)]
    }
}
