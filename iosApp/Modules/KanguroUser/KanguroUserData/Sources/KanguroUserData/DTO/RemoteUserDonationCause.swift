import Foundation

public struct RemoteUserDonationCause: Codable {
    public var userId: String?
    public var charityId: Int?
    public var title: String?
    public var cause: RemoteUserDonationType?

    public init(
        userId: String?,
        charityId: Int?,
        title: String?,
        cause: RemoteUserDonationType?
    ) {
        self.userId = userId
        self.charityId = charityId
        self.title = title
        self.cause = cause
    }
}
