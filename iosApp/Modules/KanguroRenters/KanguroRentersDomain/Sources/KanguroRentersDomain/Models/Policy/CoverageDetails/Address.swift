import Foundation

public struct Address: Hashable {
    
    public let state: String?
    public let city: String?
    public let streetNumber: String?
    public let streetName: String?
    public let zipCode: String?
    public let complement: String?
    
    public init(state: String?,
                city: String?,
                streetNumber: String?,
                streetName: String?,
                zipCode: String?,
                complement: String?) {
        self.state = state
        self.city = city
        self.streetNumber = streetNumber
        self.streetName = streetName
        self.zipCode = zipCode
        self.complement = complement
    }
}
