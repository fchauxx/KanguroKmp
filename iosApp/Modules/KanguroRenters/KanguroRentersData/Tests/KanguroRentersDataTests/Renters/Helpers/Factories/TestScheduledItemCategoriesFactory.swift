import Foundation
import KanguroSharedDomain
import KanguroRentersDomain
import KanguroRentersData

struct TestScheduledItemCategoriesFactory {

    static func makeRemoteScheduledItemCategory(
        id: String = "scheduled_item_id",
        label: String = ""
    ) -> RemoteScheduledItemCategory {
        RemoteScheduledItemCategory(id: .Electronics,
                                    label: "")
    }

    static func makeScheduledItemCategory(
        id: String = "scheduled_item_id",
        label: String = ""
    ) -> ScheduledItemCategory {
        ScheduledItemCategory(category: ScheduledItemCategoryType.Electronics,
                              label: "")
    }
}
