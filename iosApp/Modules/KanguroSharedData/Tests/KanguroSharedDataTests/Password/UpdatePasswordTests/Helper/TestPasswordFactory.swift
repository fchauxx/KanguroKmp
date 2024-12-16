import Foundation
import KanguroSharedData
import KanguroSharedDomain

struct TestPasswordFactory {
    static func makeRemotePassword(
        email: String = "teste@teste.com",
        currentPassword: String = "currentPwd",
        newPassword: String = "newPwd"
    ) -> RemotePasswordParameters {
        RemotePasswordParameters(email: email, currentPassword: currentPassword, newPassword: newPassword)
    }

    static func makePassword(
        email: String = "teste@teste.com",
        currentPassword: String = "currentPwd",
        newPassword: String = "newPwd"
    ) -> PasswordParameters {
        PasswordParameters(email: email, currentPassword: currentPassword, newPassword: newPassword)
    }
}
