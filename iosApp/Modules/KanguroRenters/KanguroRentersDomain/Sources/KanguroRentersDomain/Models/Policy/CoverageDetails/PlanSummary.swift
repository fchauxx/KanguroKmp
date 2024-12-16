import Foundation

public struct PlanSummaryItemData: Hashable {
    
    public var id: Double
    public var value: Double
    
    public init(id: Double, value: Double) {
        self.id = id
        self.value = value
    }
}

public struct PlanSummary: Hashable {
    
    public var liability: PlanSummaryItemData?
    public var deductible: PlanSummaryItemData?
    public var personalProperty: PlanSummaryItemData?
    public var lossOfUse: PlanSummaryItemData?
    
    public init(liability: PlanSummaryItemData? = nil,
                deductible: PlanSummaryItemData? = nil,
                personalProperty: PlanSummaryItemData? = nil,
                lossOfUse: PlanSummaryItemData? = nil) {
        self.liability = liability
        self.deductible = deductible
        self.personalProperty = personalProperty
        self.lossOfUse = lossOfUse
    }
}
