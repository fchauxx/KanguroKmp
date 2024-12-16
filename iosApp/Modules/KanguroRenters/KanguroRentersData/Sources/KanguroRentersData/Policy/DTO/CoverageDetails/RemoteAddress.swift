import Foundation

public struct RemoteAddress: Codable {
    
    public let state: String?
    public let city: String?
    public let streetNumber: String?
    public let streetName: String?
    public var zipCode: String?
    public let complement: String?
    
    enum CodingKeys : String, CodingKey {
        case state, city, streetNumber, streetName, zipCode, complement
    }
    
    public init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        
        state = try? container.decode(
            String.self,
            forKey: .state
        )
        city = try? container.decode(
            String.self,
            forKey: .city
        )
        streetNumber = try? container.decode(
            String.self,
            forKey: .streetNumber
        )
        streetName = try? container.decode(
            String.self,
            forKey: .streetName
        )
        complement = try? container.decode(
            String.self,
            forKey: .complement
        )
        
        // Handle zipCode as String or Int
        if let zipCodeString = try? container.decode(
            String.self,
            forKey: .zipCode
        ) {
            zipCode = zipCodeString
        } else if let zipCodeInt = try? container.decode(
            Int.self,
            forKey: .zipCode
        ) {
            zipCode = String(zipCodeInt)
        } else {
            zipCode = nil
        }
    }
    
    public init(
        state: String?,
        city: String?,
        streetNumber: String?,
        streetName: String?,
        zipCode: String?,
        complement: String?
    ) {
        self.state = state
        self.city = city
        self.streetNumber = streetNumber
        self.streetName = streetName
        self.zipCode = zipCode
        self.complement = complement
    }
}
