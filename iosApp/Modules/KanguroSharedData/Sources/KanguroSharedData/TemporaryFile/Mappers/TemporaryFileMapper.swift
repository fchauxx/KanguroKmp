import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public struct TemporaryFileMapper: ModelMapper {

    public init() {}

    public typealias T = TemporaryFile

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteTemporaryFile = input as? RemoteTemporaryFile else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        
        let tempData = TemporaryFile(filename: input.filename,
                                     blobType: input.blobType,
                                     url: input.url,
                                     id: input.id)
        
        guard let result: T = tempData as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
