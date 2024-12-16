import Foundation

public struct RemoteBankOption: Codable {
    
    public var id: Int?
    public var name: String?
    
    public init(id: Int? = nil, name: String? = nil) {
        self.id = id
        self.name = name
    }
}
