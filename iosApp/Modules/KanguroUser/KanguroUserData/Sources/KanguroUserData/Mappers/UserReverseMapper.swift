import Foundation
import KanguroUserDomain
import KanguroSharedDomain

extension UserMapper {

    public static func reverseMap(_ input: User) throws -> RemoteUser {
        var remoteDonationCause: RemoteUserDonationCause?
        var remoteLanguage: String?

        if let donationCause = input.donation {
            guard let remoteCause = donationCause.cause?.rawValue,
                  let remoteDonationType = RemoteUserDonationType(rawValue: remoteCause) else { throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map") }
            remoteDonationCause = RemoteUserDonationCause(
                userId: donationCause.userId,
                charityId: donationCause.charityId,
                title: donationCause.title,
                cause: remoteDonationType
            )
        }
        if let language = input.language {
            switch language {
            case .English:
                remoteLanguage = "English"
            case .Spanish:
                remoteLanguage = "Spanish"
            }
        }

        let remoteUser = RemoteUser(
            accessToken: input.accessToken,
            expiresOn: input.expiresOn?.isoString,
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
            donation: remoteDonationCause,
            language: remoteLanguage,
            hasAccessBlocked: input.hasAccessBlocked,
            insuranceId: input.insuranceId
        )
        return remoteUser
    }
}
