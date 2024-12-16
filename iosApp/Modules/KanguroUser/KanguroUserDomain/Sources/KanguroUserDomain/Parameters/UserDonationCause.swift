import Foundation
import KanguroSharedDomain

public struct UserDonationCause: Equatable {
    public var userId: String?
    public var charityId: Int?
    public var title: String?
    public var cause: DonationType?

    public init(
        userId: String?,
        charityId: Int?,
        title: String?,
        cause: DonationType?
    ) {
        self.userId = userId
        self.charityId = charityId
        self.title = title
        self.cause = cause
    }
}
