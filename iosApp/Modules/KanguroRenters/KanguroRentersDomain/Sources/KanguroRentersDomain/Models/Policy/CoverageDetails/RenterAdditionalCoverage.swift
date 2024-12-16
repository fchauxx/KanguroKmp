import Foundation

public enum RenterAdditionalCoverageType: String, Hashable {

    case FraudProtection
    case ReplacementCost
    case WaterSewerBackup
}

public struct RenterAdditionalCoverage: Hashable {

    public let type: RenterAdditionalCoverageType?
    public let coverageLimit: Double?
    public let deductibleLimit: Double?

    public init(type: RenterAdditionalCoverageType?,
                coverageLimit: Double?,
                deductibleLimit: Double?) {
        self.type = type
        self.coverageLimit = coverageLimit
        self.deductibleLimit = deductibleLimit
    }
}
