import Foundation
import KanguroSharedDomain

public struct User: Equatable {

    public var accessToken: String?
    public var expiresOn: Date?
    public var refreshToken: String?
    public var id: String
    public var idToken: String?
    public var givenName: String?
    public var surname: String?
    public var email: String?
    public var referralCode: String?
    public var phone: String?
    public var address: String?
    public var isNeededDeleteData: Bool?
    public var isPasswordUpdateNeeded: Bool?
    public var donation: UserDonationCause?
    public var language: Language?
    public var hasAccessBlocked: Bool?
    public var insuranceId: Int?

    public init(
        accessToken: String? = nil,
        expiresOn: Date? = nil,
        refreshToken: String? = nil,
        id: String,
        idToken: String? = nil,
        givenName: String? = nil,
        surname: String? = nil,
        email: String? = nil,
        referralCode: String? = nil,
        phone: String? = nil,
        address: String? = nil,
        isNeededDeleteData: Bool? = nil,
        isPasswordUpdateNeeded: Bool? = nil,
        donation: UserDonationCause? = nil,
        language: Language? = nil,
        hasAccessBlocked: Bool? = nil,
        insuranceId: Int? = nil
    ) {
        self.accessToken = accessToken
        self.expiresOn = expiresOn
        self.refreshToken = refreshToken
        self.id = id
        self.idToken = idToken
        self.givenName = givenName
        self.surname = surname
        self.email = email
        self.referralCode = referralCode
        self.phone = phone
        self.address = address
        self.isNeededDeleteData = isNeededDeleteData
        self.isPasswordUpdateNeeded = isPasswordUpdateNeeded
        self.donation = donation
        self.language = language
        self.hasAccessBlocked = hasAccessBlocked
        self.insuranceId = insuranceId
    }

    public var isTokenExpired: Bool {
        guard let expiresOn = expiresOn else { return true }
            return Date() > expiresOn
        }

    public mutating func updateToken(token: Token) {
        self.accessToken = token.accessToken
        self.refreshToken = token.refreshToken
        self.idToken = token.idToken
        self.expiresOn = token.expiresOn
    }
}
