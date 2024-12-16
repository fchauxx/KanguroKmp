import Foundation

public struct FirebaseTokenParameters {
    
    public var firebaseToken: String
    public var uuid: String
    
    public init(firebaseToken: String, uuid: String) {
        self.firebaseToken = firebaseToken
        self.uuid = uuid
    }
}
