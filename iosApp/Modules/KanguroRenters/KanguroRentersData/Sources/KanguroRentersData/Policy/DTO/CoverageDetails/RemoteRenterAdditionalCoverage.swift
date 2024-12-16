import Foundation

public enum RemoteRenterAdditionalCoverageType: String, Codable {

    case FraudProtection
    case ReplacementCost
    case WaterSewerBackup
}

public struct RemoteRenterAdditionalCoverage: Codable {

    public let type: RemoteRenterAdditionalCoverageType?
    public let coverageLimit: Double?
    public let deductibleLimit: Double?

    public init(type: RemoteRenterAdditionalCoverageType?,
                coverageLimit: Double?,
                deductibleLimit: Double?) {
        self.type = type
        self.coverageLimit = coverageLimit
        self.deductibleLimit = deductibleLimit
    }
}
