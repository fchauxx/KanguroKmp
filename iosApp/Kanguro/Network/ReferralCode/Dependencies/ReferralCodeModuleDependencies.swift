import Foundation
import Resolver

class ReferralCodeModuleDependencies {
    
    // MARK: - Stored Properties
    var referralCodeService: ReferralCodeModuleProtocol?
    
    // MARK: - Initializers
    init(referralCodeService: ReferralCodeModuleProtocol? = nil) {
        self.referralCodeService = referralCodeService
    }
}

// MARK: - ModuleDependencies
extension ReferralCodeModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        let referralCodeService = self.referralCodeService ?? ReferralCodeModule()
        Resolver.register { referralCodeService }
    }
}
