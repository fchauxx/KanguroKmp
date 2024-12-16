import Foundation

public struct DonationCause: Hashable {
    
    public var id: Int
    public var attributes: DonationAttributes
    
    public init(id: Int, attributes: DonationAttributes) {
        self.id = id
        self.attributes = attributes
    }
}

// MARK: - DonationAttributes
public struct DonationAttributes: Hashable {
    
    public var title: String
    public var abreviatedTitle: String
    public var description: String
    public var locale: String
    public var charityKey: Int
    public var canBeChosenByUser: Bool
    public var cause: DonationType
    
    public init(title: String,
                abreviatedTitle: String,
                description: String,
                locale: String,
                charityKey: Int,
                canBeChosenByUser: Bool,
                cause: DonationType) {
        self.title = title
        self.abreviatedTitle = abreviatedTitle
        self.description = description
        self.locale = locale
        self.charityKey = charityKey
        self.canBeChosenByUser = canBeChosenByUser
        self.cause = cause
    }
}
