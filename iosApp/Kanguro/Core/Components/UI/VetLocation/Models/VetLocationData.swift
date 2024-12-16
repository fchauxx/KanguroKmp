import UIKit
import MapKit

struct VetLocationData {
    
    // MARK: - Stored Properties
    var location: MKMapItem?
    var distance: Double?
    
    // MARK: - Computed Properties
    var name: String? {
        return location?.name
    }
    var phoneNumber: String? {
        return location?.phoneNumber
    }
    
    var adress: String? {
        guard let location,
              let number = location.placemark.subThoroughfare,
              let street = location.placemark.thoroughfare else { return nil }
        
        return "\(number) \(street)"
    }
    var adressText: String? {
        guard let distance,
              let adress else { return nil }
        return "\(distance.getMeterToMilesFormatted()) mi - \(adress)"
    }
    
    var isOpen: Bool?
    var status: (String, UIColor)? {
        guard let isOpen else { return nil }
        if isOpen {
            return ("commom.open".localized, .positiveDark)
        } else {
            return ("commom.closed".localized, .negativeDarkest)
        }
    }
}
