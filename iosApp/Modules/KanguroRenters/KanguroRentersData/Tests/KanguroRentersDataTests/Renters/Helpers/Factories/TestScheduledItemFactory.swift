import Foundation
import KanguroSharedDomain
import KanguroRentersDomain
import KanguroRentersData

struct TestScheduledItemFactory {

    static func makeRemoteScheduledItem(
        id: String = "scheduled_item_id",
        name: String? = "Playstation 9",
        type: RemoteScheduledItemCategoryType? = .Electronics,
        images: [RemoteScheduledItemImage]? = [
            RemoteScheduledItemImage(id: 777, fileName: "test", type: .ItemWithReceiptOrAppraisal)
        ]
    ) -> RemoteScheduledItem {
        RemoteScheduledItem(id: id,
                            name: name,
                            type: type,
                            images: images)
    }

    static func makeScheduledItem(
        id: String = "scheduled_item_id",
        name: String? = "Playstation 9",
        type: ScheduledItemCategory? = ScheduledItemCategory(category: .Electronics,
                                                             label: ""),
        images: [ScheduledItemImage]? = [
            ScheduledItemImage(id: 777, fileName: "test", type: .Item)
        ]
    ) -> ScheduledItem {
        ScheduledItem(id: id,
                      name: name,
                      category: type,
                      images: images)
    }
}
