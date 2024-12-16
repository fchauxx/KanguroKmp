import Foundation
import KanguroRentersDomain

public struct TestScheduledItemFactory {

    static func makeScheduledItem(
        id: String = "id",
        name: String = "Jewelry",
        category:  ScheduledItemCategory = ScheduledItemCategory(category: .ElectricScooter,
                                                                 label: "Electric"),
        images: [ScheduledItemImage] = [],
        valuation: Double = 499,
        billingCycle: BillingCycle = .YEARLY,
        total: Double = 800,
        intervalTotal: Double = 400
    ) -> ScheduledItem {
        ScheduledItem(id: id,
                      name: name,
                      category: category,
                      images: images,
                      valuation: valuation,
                      billingCycle: billingCycle,
                      total: total,
                      intervalTotal: intervalTotal)
    }
}
