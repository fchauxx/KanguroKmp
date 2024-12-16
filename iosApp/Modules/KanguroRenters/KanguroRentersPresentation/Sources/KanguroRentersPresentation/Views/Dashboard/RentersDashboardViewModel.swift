import SwiftUI
import KanguroRentersDomain
import KanguroSharedDomain
import KanguroUserDomain

public class RentersDashboardViewModel: ObservableObject {
    
    // MARK: - Stored Properties
    var policies: [RenterPolicy]
    var user: User?
    
    // MARK: - Wrapped Properties
    @Environment(\.appLanguageValue) var language
    
    @Published public var renterPolicies: [RenterPolicy]?
    @Published var requestError: String?
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    
    public init(policies: [RenterPolicy], user: User?) {
        self.policies = policies
        self.user = user
    }
}
