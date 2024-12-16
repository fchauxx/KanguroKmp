import Foundation
import KanguroNetworkDomain

public enum TemporaryFileModuleEndpoint: Endpoint {

    case createTemporaryFile
    case getTemporaryFile

    public var path: String {
        switch self {
        case .createTemporaryFile:
            return "TemporaryFile"
        case .getTemporaryFile:
            return "TemporaryFile/UploadUrl"
        }
    }
}
