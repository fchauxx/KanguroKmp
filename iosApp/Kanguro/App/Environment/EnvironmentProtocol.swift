import Foundation

protocol EnvironmentProtocol: AnyObject {
    
    var infoDictionary: [String: Any] { get }
    
    var baseURL: String { get }
    
    var apiKey: String  { get }
    
    var target: String  { get }
    
    var cloudInsuranceTarget: String { get }
    
    var compatibleBackendVersion: String { get }
    
    var sentryEnv: String { get }
    
    var sentryDsn: String { get }
    
    var airvetPartnerId: String { get }
    
    var airvetURL: String { get }
}
