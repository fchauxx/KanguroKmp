import SwiftUI
import KanguroRentersDomain

public extension DwellingType {
    
    var image: Image {
        switch self {
        case .SingleFamily:
            return Image.statusSingleFamily
        case .MultiFamily:
            return Image.statusMultiFamily
        case .Apartment:
            return Image.statusApartment
        case .Townhouse:
            return Image.statusTownhouse
        case .StudentHousing:
            return Image.statusStudent
        }
    }
    var icon: Image {
        switch self {
        case .SingleFamily:
            return Image.iconStatusSingleFamily
        case .MultiFamily:
            return Image.iconStatusMultiFamily
        case .Apartment:
            return Image.iconStatusApartment
        case .Townhouse:
            return Image.iconStatusTownhouse
        case .StudentHousing:
            return Image.iconStatusStudent
        }
    }
    
    var imageName: String {
        switch self {
        case .SingleFamily:
            return "status-single-family"
        case .MultiFamily:
            return "status-multi-family"
        case .Apartment:
            return "status-apartment"
        case .Townhouse:
            return "status-townhouse"
        case .StudentHousing:
            return "status-student"
        }
    }
}
