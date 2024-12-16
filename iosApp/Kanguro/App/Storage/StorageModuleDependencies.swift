import Foundation
import Resolver
import KanguroStorageData
import KanguroStorageDomain

class StorageModuleDependencies {
    
    // MARK: - Stored Properties
    var keyChainStorage: SecureStorage?
    var userDefaultsStorage: Storage?
    
    // MARK: - Initializers
    init(keyChainStorage: SecureStorage? = nil,
         userDefaultsStorage: Storage? = nil) {
        self.keyChainStorage = keyChainStorage
        self.userDefaultsStorage = userDefaultsStorage
    }
}

// MARK: - Setup
extension StorageModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        let keyChainStorage = self.keyChainStorage ?? SecureStorageImplementation()
        Resolver.register { keyChainStorage as SecureStorage }
        
        let userDefaultsStorage = self.userDefaultsStorage ?? StorageImplementation()
        Resolver.register { userDefaultsStorage as Storage }
    }
}
