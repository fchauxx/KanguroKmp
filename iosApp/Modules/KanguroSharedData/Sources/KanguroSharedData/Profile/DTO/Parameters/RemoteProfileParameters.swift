import Foundation

public struct RemoteProfileParameters: Codable {

    public var givenName: String?
    public var surname: String?
    public var phone: String?

    public init(givenName: String?, 
                surname: String?,
                phone: String?) {
        self.givenName = givenName
        self.surname = surname
        self.phone = phone
    }
}
