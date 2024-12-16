import Foundation

public struct RemoteDonationCause: Codable {
    
    public var id: Int
    public var attributes: RemoteDonationAttributes
    
    public init(id: Int, attributes: RemoteDonationAttributes) {
        self.id = id
        self.attributes = attributes
    }
}

// MARK: - DonationAttributes
public struct RemoteDonationAttributes: Codable {
    public var title: String
    public var abreviatedTitle: String
    public var description: String
    public var locale: String
    public var charityKey: Int
    public var canBeChosenByUser: Bool
    public var cause: RemoteDonationType
    
    public init(title: String,
                abreviatedTitle: String,
                description: String,
                locale: String,
                charityKey: Int,
                canBeChosenByUser: Bool,
                cause: RemoteDonationType) {
        self.title = title
        self.abreviatedTitle = abreviatedTitle
        self.description = description
        self.locale = locale
        self.charityKey = charityKey
        self.canBeChosenByUser = canBeChosenByUser
        self.cause = cause
    }
}
