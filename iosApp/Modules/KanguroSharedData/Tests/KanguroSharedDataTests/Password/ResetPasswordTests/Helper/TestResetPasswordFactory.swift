import Foundation
import KanguroSharedData
import KanguroSharedDomain

struct TestResetPasswordFactory {
    static func makeRemoteResetPassword(
        email: String = "teste@teste.com"
    ) -> RemoteResetPasswordParameters {
        RemoteResetPasswordParameters(email: email)
    }

    static func makeResetPassword(
        email: String = "teste@teste.com"
    ) -> ResetPasswordParameters {
        ResetPasswordParameters(email: email)
    }
}
