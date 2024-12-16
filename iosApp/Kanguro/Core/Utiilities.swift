import SwiftUI
import KanguroRentersPresentation
import KanguroRentersDomain
import KanguroUserDomain
import KanguroSharedData
import KanguroSharedPresentation
import KanguroPetPresentation

struct Utilities {
        
    public static func openAirvet(airvetUserDetails: AirvetUserDetails){
        let airvetDeeplink = "\(Environment().airvetURL)?partner_id=\(Environment().airvetPartnerId)&unique_id=\(airvetUserDetails.insuranceId.description)&email=\(airvetUserDetails.email.description)&first=\(airvetUserDetails.firstName.description)&last=\(airvetUserDetails.lastName.description)"
        print("openAirvet \(airvetDeeplink)")
        if let url  = NSURL(string: airvetDeeplink),
           UIApplication.shared.canOpenURL(url as URL) {
            UIApplication.shared.open(url as URL)
        }
    }
}
