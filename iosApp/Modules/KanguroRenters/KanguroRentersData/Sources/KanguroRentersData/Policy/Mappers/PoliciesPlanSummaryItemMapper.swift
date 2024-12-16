import Foundation
import KanguroSharedDomain
import KanguroRentersDomain
import KanguroNetworkDomain

public struct PoliciesPlanSummaryItemMapper: ModelMapper {
    public typealias T = PlanSummaryItemData
    
    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemotePlanSummaryItemData = input as? RemotePlanSummaryItemData else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        let planSummary = PlanSummaryItemData(id: input.id, value: input.value)
        
        guard let result: T = planSummary as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

public struct PoliciesPlanSummaryItemsMapper: ModelMapper {
    public typealias T = [PlanSummaryItemData]
    
    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemotePlanSummaryItemData] = input as? [RemotePlanSummaryItemData] else { throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map") }
        let items: [PlanSummaryItemData] = try input.map {
            return try PoliciesPlanSummaryItemMapper.map($0)
        }
        guard let result = items as? T else { throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map") }
        return result
    }
}
