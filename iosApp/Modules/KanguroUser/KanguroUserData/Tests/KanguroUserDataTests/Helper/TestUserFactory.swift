import Foundation
import KanguroUserData
import KanguroUserDomain
import KanguroSharedDomain
import KanguroNetworkDomain

struct TestUserFactory {
    static func make(
        accessToken: String? = "ABCD",
        expiresOn: Date? = Date(timeIntervalSince1970: 1598627222),
        refreshToken: String? = "EFGH",
        id: String,
        idToken: String? = "IJKL",
        givenName: String? = "John",
        surname: String? = "Williams",
        email: String? = "john@williams.com",
        referralCode: String? = "MNOP",
        phone: String? = "+5511912345678",
        address: String? = "Calle Bolivar 10",
        isNeededDeleteData: Bool? = false,
        isPasswordUpdateNeeded: Bool? = false,
        donation: UserDonationCause? = nil,
        language: Language? = .Spanish,
        hasAccessBlocked: Bool? = false
    ) -> User {
        var userDonation = donation
        if userDonation == nil {
            userDonation = UserDonationCause(
                userId: id,
                charityId: 919,
                title: "UVXZ",
                cause: .Animals
            )
        }
        return User(
            accessToken: accessToken,
            expiresOn: expiresOn,
            refreshToken: refreshToken,
            id: id,
            idToken: idToken,
            givenName: givenName,
            surname: surname,
            email: email,
            referralCode: referralCode,
            phone: phone,
            address: address,
            isNeededDeleteData: isNeededDeleteData,
            isPasswordUpdateNeeded: isPasswordUpdateNeeded,
            donation: userDonation,
            language: language,
            hasAccessBlocked: hasAccessBlocked
            )
    }

    static func makeRemote(
    accessToken: String? = "ABCD",
    expiresOn: String? = "2020-08-28T15:07:02+00:00",
    refreshToken: String? = "EFGH",
    id: String,
    idToken: String? = "IJKL",
    givenName: String? = "John",
    surname: String? = "Williams",
    email: String? = "john@williams.com",
    referralCode: String? = "MNOP",
    phone: String? = "+5511912345678",
    address: String? = "Calle Bolivar 10",
    isNeededDeleteData: Bool? = false,
    isPasswordUpdateNeeded: Bool? = false,
    donation: RemoteUserDonationCause? = nil,
    language: String? = "Spanish",
    hasAccessBlocked: Bool? = false
    ) -> RemoteUser {
        var userDonation = donation
        if userDonation == nil {
            userDonation = RemoteUserDonationCause(
                userId: id,
                charityId: 919,
                title: "UVXZ",
                cause: .Animals
            )
        }
        return RemoteUser(
            accessToken: accessToken,
            expiresOn: expiresOn,
            refreshToken: refreshToken,
            id: id,
            idToken: idToken,
            givenName: givenName,
            surname: surname,
            email: email,
            referralCode: referralCode,
            phone: phone,
            address: address,
            isNeededDeleteData: isNeededDeleteData,
            isPasswordUpdateNeeded: isPasswordUpdateNeeded,
            donation: userDonation,
            language: language,
            hasAccessBlocked: hasAccessBlocked
        )
    }
}
