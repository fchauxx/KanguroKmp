import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public struct BankOptionsMapper: ModelMapper {
    public typealias T = [BankOption]

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemoteBankOption] = input as? [RemoteBankOption] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let banks: [BankOption] = try input.map {
            try BankOptionMapper.map($0)
        }
        guard let result: T = banks as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

public struct BankOptionMapper: ModelMapper {
    public typealias T = BankOption

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteBankOption = input as? RemoteBankOption else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
       
        let bankOption = BankOption(id: input.id,
                                    name: input.name)
        guard let result: T = bankOption as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
