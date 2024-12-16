import Foundation

public struct RemoteApiVersion: Codable {
    
    public var version: String
    
    public init(version: String) {
        self.version = version
    }
}
