import Foundation

public struct RemoteCoverageData: Codable {

    // MARK: - Stored Properties
    public var name: String
    public var varName: String?
    public var usedValue: Double?
    public var remainingValue: Double?
    public var value: Double?
    public var annualLimit: Double?
    public var remainingLimit: Double?
    public var uses: Int?
    public var remainingUses: Int?
    public var usesLimit: Int?

    public init(
        name: String,
        varName: String? = nil,
        usedValue: Double? = nil,
        remainingValue: Double? = nil,
        value: Double? = nil,
        annualLimit: Double? = nil,
        remainingLimit: Double? = nil,
        uses: Int? = nil,
        remainingUses: Int? = nil,
        usesLimit: Int? = nil
    ) {
        self.name = name
        self.varName = varName
        self.usedValue = usedValue
        self.remainingValue = remainingValue
        self.value = value
        self.annualLimit = annualLimit
        self.remainingLimit = remainingLimit
        self.uses = uses
        self.remainingUses = remainingUses
        self.usesLimit = usesLimit
    }
}
