import Foundation
import SwiftUI

public struct AdvertisingCardViewData: Identifiable {

    public let id: Int
    public let imageName: String
    public let URL: String
    public let partnerName: String

    public init(id: Int, imageName: String, URL: String, partnerName: String) {
        self.id = id
        self.imageName = imageName
        self.URL = URL
        self.partnerName = partnerName
    }
}
