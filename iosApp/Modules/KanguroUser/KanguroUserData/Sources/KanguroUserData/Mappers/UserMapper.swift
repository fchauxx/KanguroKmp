import Foundation
import KanguroSharedDomain
import KanguroUserDomain
import KanguroNetworkDomain

public struct UserMapper: ModelMapper {
    public typealias T = User
    
    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteUser = input as? RemoteUser else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        
        var donationCause: UserDonationCause? = nil
        var language: Language? = nil
        
        if let remoteDonationCause = input.donation {
            guard let cause = remoteDonationCause.cause?.rawValue,
                  let donationType = DonationType(rawValue: cause) else { throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map") }
            donationCause = UserDonationCause(userId: remoteDonationCause.userId,
                                              charityId: remoteDonationCause.charityId,
                                              title: remoteDonationCause.title,
                                              cause: donationType)
        }
        if let remoteLanguage = input.language {
            switch remoteLanguage.lowercased() {
            case "en", "english":
                language = .English
            case "es", "spanish":
                language = .Spanish
            default: break
            }
        }
        
        guard let user = User(
            accessToken: input.accessToken,
            expiresOn: input.expiresOn?.date,
            refreshToken: input.refreshToken,
            id: input.id,
            idToken: input.idToken,
            givenName: input.givenName,
            surname: input.surname,
            email: input.email,
            referralCode: input.referralCode,
            phone: input.phone,
            address: input.address,
            isNeededDeleteData: input.isNeededDeleteData,
            isPasswordUpdateNeeded: input.isPasswordUpdateNeeded,
            donation: donationCause,
            language: language,
            hasAccessBlocked: input.hasAccessBlocked,
            insuranceId: input.insuranceId
        ) as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return user
    }
}
