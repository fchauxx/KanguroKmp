import Foundation

public struct RemoteResumedPlanSummaryParameters: Codable {

    public let deductibleId: Double?
    public let liabilityId: Double?
    public let personalProperty: Double?

    public init(deductibleId: Double?,
                liabilityId: Double?,
                personalProperty: Double?) {
        self.liabilityId = liabilityId
        self.deductibleId = deductibleId
        self.personalProperty = personalProperty
    }
}
