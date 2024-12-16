import Foundation
import KanguroFeatureFlagDomain

public class GetFeatureFlagBoolUseCaseMock: GetFeatureFlagBoolUseCaseProtocol {
    
    var shouldShowRenters: Bool
    var shouldUseOTPValidation: Bool
    var shouldShowLiveVet: Bool

    public init(shouldShowRenters: Bool = false, shouldUseOTPValidation: Bool = false, shouldShowLiveVet: Bool = false) {
        self.shouldShowRenters = shouldShowRenters
        self.shouldUseOTPValidation = shouldUseOTPValidation
        self.shouldShowLiveVet = shouldShowLiveVet
    }
    
    public func execute(key: KanguroBoolFeatureFlagKeys) throws -> Bool {
        
        switch key {
        case .shouldShowRenters:
            return shouldShowRenters
        case .shouldUseOTPValidation:
            return shouldUseOTPValidation
        case .shouldShowLiveVet:
            return shouldShowLiveVet
        }
    }
}

