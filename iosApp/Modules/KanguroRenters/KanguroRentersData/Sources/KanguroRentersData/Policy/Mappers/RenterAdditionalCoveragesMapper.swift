import Foundation
import KanguroSharedDomain
import KanguroRentersDomain
import KanguroNetworkDomain

public struct RenterAdditionalCoveragesMapper: ModelMapper {
    public typealias T = [RenterAdditionalCoverage]

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemoteRenterAdditionalCoverage] = input as? [RemoteRenterAdditionalCoverage] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let rentersAdditionalCoverages: [RenterAdditionalCoverage] = try input.map {
            try RenterAdditionalCoverageMapper.map($0)
        }
        guard let result: T = rentersAdditionalCoverages as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

public struct RenterAdditionalCoverageMapper: ModelMapper {
    public typealias T = RenterAdditionalCoverage
    
    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteRenterAdditionalCoverage = input as? RemoteRenterAdditionalCoverage else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        var additionalCoverageType: RenterAdditionalCoverageType? = nil
        if let remoteAdditionalCoverageType: RemoteRenterAdditionalCoverageType = input.type {
            additionalCoverageType = RenterAdditionalCoverageType(rawValue: remoteAdditionalCoverageType.rawValue)
        }

        let renterAdditionalCoverage = RenterAdditionalCoverage(type: additionalCoverageType,
                                                                coverageLimit: input.coverageLimit,
                                                                deductibleLimit: input.deductibleLimit)
        guard let result: T = renterAdditionalCoverage as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
