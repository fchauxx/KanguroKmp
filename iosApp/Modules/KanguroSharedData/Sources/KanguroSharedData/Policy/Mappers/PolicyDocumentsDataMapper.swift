import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public struct PolicyDocumentsMapper: ModelMapper {
    public typealias T = [PolicyDocumentData]

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemotePolicyDocumentData] = input as? [RemotePolicyDocumentData] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let policyDocuments: [PolicyDocumentData] = try input.map {
            try PolicyDocumentMapper.map($0)
        }
        guard let result: T = policyDocuments as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

public struct PolicyDocumentMapper: ModelMapper {
    public typealias T = PolicyDocumentData

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemotePolicyDocumentData = input as? RemotePolicyDocumentData else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let policyDocument = PolicyDocumentData(
            id: input.id,
            name: input.name,
            filename: input.filename
        )
        guard let result: T = policyDocument as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
