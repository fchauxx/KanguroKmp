import Foundation

public struct RemoteURLRedirect: Codable {
    
    public var redirectTo: String
    
    public init(redirectTo: String) {
        self.redirectTo = redirectTo
    }
}
