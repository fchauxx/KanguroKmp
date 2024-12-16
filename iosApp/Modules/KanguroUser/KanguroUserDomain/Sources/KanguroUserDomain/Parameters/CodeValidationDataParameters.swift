import Foundation

public struct CodeValidationDataParameters {
    
    public var email: String
    public var code: String
    
    public init(email: String, code: String) {
        self.email = email
        self.code = code
    }
}
