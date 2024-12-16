import Foundation

public protocol ClaimProtocol: Equatable {
    
    var id: String { get set }
    var status: ClaimStatus? { get set }
    var decision: String? { get set }
    var createdAt: Date? { get set }
    var updatedAt: Date? { get set }
    var incidentDate: Date? { get set }
    var description: String? { get set }
    var prefixId: String? { get set }
    var amount: Double? { get set }
    var amountPaid: Double? { get set }
    var amountTransferred: Double? { get set }
    var deductibleContributionAmount: Double? { get set }
    var chatbotSessionsIds: [String]? { get set }
    var isPendingCommunication: Bool { get set }
    
}
