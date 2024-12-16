import Foundation
import KanguroSharedDomain
import KanguroRentersDomain
import KanguroNetworkDomain

public struct RentersScheduledItemsCategoryMapper: ModelMapper {
    public typealias T = [ScheduledItemCategory]
    
    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemoteScheduledItemCategory] = input as? [RemoteScheduledItemCategory] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let scheduledItems: [ScheduledItemCategory] = try input.map {
            try RentersScheduledItemCategoryMapper.map($0)
        }
        guard let result: T = scheduledItems as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

public struct RentersScheduledItemCategoryMapper: ModelMapper {
    public typealias T = ScheduledItemCategory
    
    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteScheduledItemCategory = input as? RemoteScheduledItemCategory else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        
        var scheduledItem: ScheduledItemCategory? = nil
        
        if let category = ScheduledItemCategoryType(rawValue: input.id.rawValue) {
            scheduledItem = ScheduledItemCategory(category: category,
                                                  label: input.label)
        }
        
        guard let result: T = scheduledItem as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
