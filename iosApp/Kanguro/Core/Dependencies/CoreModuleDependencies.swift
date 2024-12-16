import Foundation
import Resolver

// MARK: - ModuleDependencies
class CoreModuleDependencies: ModuleDependencies {
    
    // MARK: - Stored Properties
    var encoder: JSONEncoder?
    var decoder: JSONDecoder?
    var environment: Environment?
    
    // MARK: - Initializers
    init(encoder: JSONEncoder? = nil,
         decoder: JSONDecoder? = nil,
         environment: Environment? = nil) {
        self.encoder = encoder
        self.decoder = decoder
        self.environment = environment
    }
}

// MARK: - ModuleDependencies
extension CoreModuleDependencies {
    
    func setupDependencies() {
        
        let encoder = self.encoder ?? JSONEncoder()
        encoder.dateEncodingStrategy = .formatted(.iso8601Full)
        Resolver.register { encoder }
        
        let decoder = self.decoder ?? JSONDecoder()
        decoder.dateDecodingStrategy = .formatted(.iso8601Full)
        Resolver.register { decoder }
        
        let environment = self.environment ?? Environment()
        Resolver.register { environment as EnvironmentProtocol }
    }
}
