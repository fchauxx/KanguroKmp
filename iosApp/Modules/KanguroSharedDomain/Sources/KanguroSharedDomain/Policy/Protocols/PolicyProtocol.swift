import Foundation

public protocol PolicyProtocol {
    var id: String? { get set }
    var startDate: Date? { get set }
    var waitingPeriod: Date?  { get set }
    var waitingPeriodRemainingDays: Double?  { get set }
    var endDate: Date?  { get set }
    var deductable: Deductable?  { get set }
    var reimbursment: Reimbursment?  { get set }
    var sumInsured: SumInsured?  { get set }
    var status: PolicyStatus?  { get set }
    var feedbackRate: Int?  { get set }
    var payment: Payment?  { get set }
    var preventive: Bool?  { get set }
    var policyExternalId: Int?  { get set }
    var policyOfferId: Int?  { get set }
    var isFuture: Bool? { get set }
}
