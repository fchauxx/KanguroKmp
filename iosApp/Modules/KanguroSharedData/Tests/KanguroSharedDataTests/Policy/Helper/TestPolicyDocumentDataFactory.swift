import Foundation
import KanguroSharedData
import KanguroSharedDomain

struct TestPolicyDocumentDataFactory {
    static func makePolicyDocumentData(
        id: Int64? = Int64(Int(10)),
        name: String? = "MyDocumentData",
        filename: String? = "MyDocumentDataFilename"
    ) -> PolicyDocumentData {
        PolicyDocumentData(
            id: id,
            name: name,
            filename: filename
        )
    }

    static func makeRemotePolicyDocumentData(
        id: Int64? = Int64(Int(10)),
        name: String? = "MyDocumentData",
        filename: String? = "MyDocumentDataFilename"
    ) -> RemotePolicyDocumentData {
        RemotePolicyDocumentData(
            id: id,
            name: name,
            filename: filename
        )
    }
}
