import Foundation

public struct RemotePasswordParameters: Codable {
    
    public var email: String
    public var currentPassword: String
    public var newPassword: String
    
    public init(email: String, currentPassword: String, newPassword: String) {
        self.email = email
        self.currentPassword = currentPassword
        self.newPassword = newPassword
    }
}