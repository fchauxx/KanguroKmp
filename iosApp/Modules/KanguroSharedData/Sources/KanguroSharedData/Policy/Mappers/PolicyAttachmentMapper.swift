import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public struct PolicyAttachmentsMapper: ModelMapper {
    public typealias T = [PolicyAttachment]

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemotePolicyAttachment] = input as? [RemotePolicyAttachment] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let policyAttachments = input.map {
            PolicyAttachment(
                id: $0.id,
                name: $0.name,
                fileSize: $0.fileSize
            )
        }
        guard let result: T = policyAttachments as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
