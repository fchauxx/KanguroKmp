import Foundation

public struct RemotePlanSummaryItemData: Codable {
    
    public let id: Double
    public let value: Double
    
    public init(id: Double, value: Double) {
        self.id = id
        self.value = value
    }
}

public struct RemotePlanSummary: Codable {
    
    public let liability: RemotePlanSummaryItemData?
    public let deductible: RemotePlanSummaryItemData?
    public let personalProperty: RemotePlanSummaryItemData?
    public let lossOfUse: RemotePlanSummaryItemData?
    
    public init(liability: RemotePlanSummaryItemData? = nil,
                deductible: RemotePlanSummaryItemData? = nil,
                personalProperty: RemotePlanSummaryItemData? = nil,
                lossOfUse: RemotePlanSummaryItemData? = nil) {
        self.liability = liability
        self.deductible = deductible
        self.personalProperty = personalProperty
        self.lossOfUse = lossOfUse
    }
}
