import Foundation
import KanguroSharedData
import KanguroSharedDomain

struct TestCoverageDataFactory {
    static func makeCoverageData(
        name: String = "MyCoverageData",
        varName: String? = "name",
        usedValue: Double? = Double(10),
        remainingValue: Double? = Double(9),
        value: Double? = Double(1),
        annualLimit: Double? = Double(1000),
        remainingLimit: Double? = Double(990),
        uses: Int? = 2,
        remainingUses: Int? = 18,
        usesLimit: Int? = 20
    ) -> CoverageData {
        CoverageData(
            name: name,
            varName: varName,
            usedValue: usedValue,
            remainingValue: remainingValue,
            value: value,
            annualLimit: annualLimit,
            remainingLimit: remainingLimit,
            uses: uses,
            remainingUses: remainingUses,
            usesLimit: usesLimit
        )
    }

    static func makeRemoteCoverageData(
        name: String = "MyCoverageData",
        varName: String? = "name",
        usedValue: Double? = Double(10),
        remainingValue: Double? = Double(9),
        value: Double? = Double(1),
        annualLimit: Double? = Double(1000),
        remainingLimit: Double? = Double(990),
        uses: Int? = 2,
        remainingUses: Int? = 18,
        usesLimit: Int? = 20
    ) -> RemoteCoverageData {
        RemoteCoverageData(
            name: name,
            varName: varName,
            usedValue: usedValue,
            remainingValue: remainingValue,
            value: value,
            annualLimit: annualLimit,
            remainingLimit: remainingLimit,
            uses: uses,
            remainingUses: remainingUses,
            usesLimit: usesLimit
        )
    }
}
