import Foundation
import KanguroNetworkData
import KanguroUserDomain
import KanguroNetworkDomain

struct TestTokenFactory {
    static func make(
        accessToken: String? = "ABCD",
        expiresOn: Date? = Date(timeIntervalSince1970: 1598627222),
        refreshToken: String? = "AEIOU",
        idToken: String? = "FGHJ"
    ) -> Token {
        return Token(
            accessToken: accessToken,
            expiresOn: expiresOn,
            refreshToken: refreshToken,
            idToken: idToken
        )
    }

    static func makeRemote(
        accessToken: String? = "ABCD",
        expiresOn: String? = "2020-08-28T15:07:02+00:00",
        refreshToken: String? = "AEIOU",
        idToken: String? = "FGHJ"
    ) -> RemoteToken {
        RemoteToken(
            accessToken: accessToken,
            expiresOn: expiresOn,
            refreshToken: refreshToken,
            idToken: idToken
        )
    }
}
