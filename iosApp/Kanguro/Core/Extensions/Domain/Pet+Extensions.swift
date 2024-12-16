import Foundation
import KanguroSharedDomain
import Kingfisher
import UIKit
import KanguroPetDomain

extension Pet {
    var petPictureResource: KF.ImageResource? {
        guard let petPictureUrl = petPictureUrl,
              let url = URL(string: petPictureUrl) else { return nil }
        return KF.ImageResource(downloadURL: url, cacheKey: petPictureUrl.urlWithoutQuery)
    }
    var placeholderImage: UIImage? {
        let dogImage = UIImage(named: "defaultPicture-dog")
        let catImage = UIImage(named: "defaultPicture-cat")
        return (type == .Dog) ? dogImage : catImage
    }
}
