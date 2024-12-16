import Foundation
import KanguroSharedData
import KanguroSharedDomain

struct TestKeycloakFactory {
    static func makeRemoteKeycloakResponse(
        access_token: String = "access_token_test",
        expired_in: Int = 888,
        refresh_token: String = "refresh_token_test",
        token_type: String = "token_type_test",
        session_state: String = "session_state_test",
        scope: String = "scope_test"
    ) -> RemoteKeycloakResponse {
        RemoteKeycloakResponse(access_token: access_token,
                         expired_in: expired_in,
                         refresh_token: refresh_token,
                         token_type: token_type,
                         session_state: session_state,
                         scope: scope)
    }

    static func makeKeycloakResponse(
        access_token: String = "access_token_test",
        expired_in: Int = 888,
        refresh_token: String = "refresh_token_test",
        token_type: String = "token_type_test",
        session_state: String = "session_state_test",
        scope: String = "scope_test"
    ) -> KeycloakResponse {
        KeycloakResponse(access_token: access_token,
                         expired_in: expired_in,
                         refresh_token: refresh_token,
                         token_type: token_type,
                         session_state: session_state,
                         scope: scope)
    }
}
