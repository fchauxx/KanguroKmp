import Foundation
import KanguroSharedDomain
import KanguroRentersDomain
import KanguroNetworkDomain

public struct RentersScheduledItemsMapper: ModelMapper {
    public typealias T = [ScheduledItem]
    
    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemoteScheduledItem] = input as? [RemoteScheduledItem] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let scheduledItems: [ScheduledItem] = try input.map {
            try RentersScheduledItemMapper.map($0)
        }
        guard let result: T = scheduledItems as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

public struct RentersScheduledItemMapper: ModelMapper {
    public typealias T = ScheduledItem
    
    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteScheduledItem = input as? RemoteScheduledItem else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        
        var category: ScheduledItemCategory? = nil
        var images: [ScheduledItemImage] = []
        var billingCycle: BillingCycle? = nil
        
        if let type = input.type,
           let remoteCategory = ScheduledItemCategoryType(rawValue: type.rawValue) {
            category = ScheduledItemCategory(category: remoteCategory, label: "")
        }
        
        if let remoteImages = input.images {
            remoteImages.forEach { image in
                if let imageType = image.type {
                    let type = ScheduledItemImageType(rawValue: imageType.rawValue)
                    images.append(ScheduledItemImage(id: image.id,
                                                     fileName: image.fileName,
                                                     type: type))
                }
            }
        }
        
        if let remoteBillingCycle: RemoteBillingCycle = input.billingCycle {
            billingCycle = BillingCycle(rawValue: remoteBillingCycle.rawValue)
        }
        let scheduledItem = ScheduledItem(id: input.id,
                                          name: input.name,
                                          category: category,
                                          images: images,
                                          valuation: input.valuation,
                                          billingCycle: billingCycle,
                                          total: input.total,
                                          intervalTotal: input.intervalTotal)
        
        guard let result: T = scheduledItem as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
