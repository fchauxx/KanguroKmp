import UIKit
import Combine

class BeforeGetStartedViewModel {
    
    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    var claimValue: Double?

    // MARK: - Computed Properties
    var minimumClaimValue: Bool {
        guard let claimValue else { return false }
        return claimValue >= 900 ? true : false
    }
}

// MARK: - Public Methods
extension BeforeGetStartedViewModel {
    
    func update(claimValue: String) {
        let value = claimValue.replacingOccurrences(of: ",", with: "")
        if let claimValueText = Double(value.onlyNumbersAndPuntuaction) {
            self.claimValue = claimValueText
        }
    }
}
