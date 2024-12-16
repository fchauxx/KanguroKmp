import Foundation

public struct KeycloakResponse: Equatable {
    
    public var access_token: String?
    public var expired_in: Int?
    public var refresh_token: String?
    public var token_type: String?
    public var session_state: String?
    public var scope: String?
    
    public init(access_token: String? = nil, expired_in: Int? = nil, refresh_token: String? = nil, token_type: String? = nil, session_state: String? = nil, scope: String? = nil) {
        self.access_token = access_token
        self.expired_in = expired_in
        self.refresh_token = refresh_token
        self.token_type = token_type
        self.session_state = session_state
        self.scope = scope
    }
}
