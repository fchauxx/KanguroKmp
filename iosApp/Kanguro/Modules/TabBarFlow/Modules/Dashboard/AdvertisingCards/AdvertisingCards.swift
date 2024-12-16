import Foundation
import KanguroDesignSystem

class AdvertisingCards {

    // MARK: - Static Properties
    static var advertisingCards: [AdvertisingCardViewData] = [
        AdvertisingCardViewData(id: 0, imageName: "banner-roam", URL: "", partnerName: "ROAM"),
        AdvertisingCardViewData(id: 1, imageName: "banner-missing-pets", URL: "", partnerName: "MISSINGPETS")
    ]

    // MARK: - Static Methods
    static func getAdvertisingCards() -> [AdvertisingCardViewData] {
        return advertisingCards
    }
}
