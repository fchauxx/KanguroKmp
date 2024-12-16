import Foundation
import KanguroSharedDomain
import KanguroUserDomain
import Resolver

extension User {

    static var getLanguage: () -> Language = {
        let getLocalUser: GetLocalUser = Resolver.resolve()
        guard let user = try? getLocalUser.execute().get(),
              let language = user.language else {
            return .English
        }
        return language
    }
}
