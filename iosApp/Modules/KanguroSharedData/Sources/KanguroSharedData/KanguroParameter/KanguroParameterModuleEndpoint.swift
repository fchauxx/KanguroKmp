import Foundation
import KanguroNetworkDomain

public enum KanguroParametersModuleEndpoint: Endpoint {
    
    case getInformationTopics(key: String)
    
    public var path: String {
        switch self {
        case .getInformationTopics(let key):
            return "KanguroParameter/GetInformationTopicByKeyAndLanguage/\(key)"
        }
    }
}
