import Foundation
import KanguroSharedData
import KanguroSharedDomain

struct TestTemporaryFileFactory {

    static func makeRemoteTemporaryFile(
        url: String? = "https://my-extraordinary-image-example.com",
        id: Int? = 77
    ) -> RemoteTemporaryFile {
        RemoteTemporaryFile(url: url, id: id)
    }

    static func makeTemporaryFile(
        url: String? = "https://my-extraordinary-image-example.com",
        id: Int? = 77
    ) -> TemporaryFile {
        TemporaryFile(url: url, id: id)
    }
}
