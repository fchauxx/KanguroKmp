import Foundation

public struct RemoteResetPasswordParameters: Codable {
    
    public var email: String
    
    public init(email: String) {
        self.email = email
    }
}
