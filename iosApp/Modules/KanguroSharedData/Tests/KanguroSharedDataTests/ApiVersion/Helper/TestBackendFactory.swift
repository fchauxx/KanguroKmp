import Foundation
import KanguroSharedData
import KanguroSharedDomain

struct TestBackendFactory {
    static func makeRemoteApiVersion(
        version: String = "7.0.0"
    ) -> RemoteApiVersion {
        RemoteApiVersion(version: version)
    }

    static func makeMobileApiVersion(
        version: String = "7.0.0"
    ) -> ApiVersion {
        ApiVersion(
            version: version
        )
    }
    
    static func makeOldMobileApiVersion(
        version: String = "6.0.0"
    ) -> ApiVersion {
        ApiVersion(
            version: version
        )
    }
}
