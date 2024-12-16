import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public struct CoverageDataListMapper: ModelMapper {
    public typealias T = [CoverageData]

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemoteCoverageData] = input as? [RemoteCoverageData] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let coverageDataList: [CoverageData] = try input.map {
            try CoverageDataMapper.map($0)
        }
        guard let result: T = coverageDataList as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

public struct CoverageDataMapper: ModelMapper {
    public typealias T = CoverageData

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteCoverageData = input as? RemoteCoverageData else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let coverageData = CoverageData(
            name: input.name,
            varName: input.varName,
            usedValue: input.usedValue,
            remainingValue: input.remainingValue,
            value: input.value,
            annualLimit: input.annualLimit,
            remainingLimit: input.remainingLimit,
            uses: input.uses,
            remainingUses: input.remainingUses,
            usesLimit: input.usesLimit
        )
        guard let result: T = coverageData as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
