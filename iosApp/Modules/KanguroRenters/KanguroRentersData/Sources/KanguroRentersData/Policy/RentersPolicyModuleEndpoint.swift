import Foundation
import KanguroNetworkDomain

public enum RentersPolicyModuleEndpoint: Endpoint {

    case deleteScheduleItem(policyId: String, scheduledItemId: String)
    case createScheduledItem(policyId: String)
    case getScheduledItems(policyId: String)
    case getCategories
    case updateScheduledItemPricing(policyId: String)
    case policyPricing(type: String, queries: [String : String])
    case createTemporaryFile
    case getTemporaryFile
    case updateTemporaryFiles(scheduledItemId: String)
    case additionalParties(policyId: String)
    case getRenterPolicy(id: String)
    case getRenterPolicies
    case getRenterPersonalProperties
    case updateRenterPolicy(policyId: String)
    case updatePolicyPricing(policyId: String)
    
    public var path: String {
        switch self {
        case .deleteScheduleItem(let policyId, let scheduledItemId):
            return "Renters/Policy/\(policyId)/ScheduledItem/\(scheduledItemId)"
        case .createScheduledItem(let policyId):
            return "Renters/Policy/\(policyId)/ScheduledItem"
        case .getScheduledItems(let policyId):
            return "Renters/Policy/\(policyId)/ScheduledItems"
        case .getCategories:
            return "Renters/ScheduledItems/Types"
        case .updateScheduledItemPricing(let id):
            return "Renters/Policy/\(id)/ScheduledItem/Pricing"
        case .policyPricing(let type, let queries):
            return "Renters/Policy/Pricing/\(type)\(addQueries(with: queries))"
        case .createTemporaryFile:
            return "TemporaryFile"
        case .updateTemporaryFiles(let id):
            return "Renters/ScheduledItems/\(id)/Images"
        case .getTemporaryFile:
            return "TemporaryFile/UploadUrl"
        case .additionalParties(let id):
            return "Renters/Policy/\(id)/AdditionalParties"
        case .getRenterPolicy(let id):
            return "Renters/Policy/\(id)"
        case .getRenterPolicies:
            return "Renters/Policy"
        case .updateRenterPolicy(let id):
            return "Renters/Policy/\(id)"
        case .updatePolicyPricing(let id):
            return "Renters/Policy/\(id)/Pricing"
        case .getRenterPersonalProperties:
            return "Renters/Policy/Pricing/PersonalProperty"
        }
    }
    
    private func addQueries(with queries: [String : String]) -> String {
        var text = ""
        queries.forEach { key, value in
            text.append("?\(key)=\(value)")
        }
        return text
    }
}
