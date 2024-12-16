import Resolver
import KanguroUserDomain
import SwiftUI

public class AirvetInstructionViewModel: ObservableObject {

    // MARK: - Dependencies
    @LazyInjected var getRemoteUser: GetRemoteUser

    //MARK: - Wrapped Properties
    @Published var isLoading: Bool
    @Published var requestError: String
    @Published var airvetUserDetails: AirvetUserDetails

    public init(
        isLoading: Bool = true,
        requestError: String = "",
        airvetUserDetails: AirvetUserDetails = AirvetUserDetails(firstName: "", lastName: "", email: "", insuranceId: "")
    ) {
        self.isLoading = isLoading
        self.requestError = requestError
        self.airvetUserDetails = airvetUserDetails
    }
}

// MARK: - Network
extension AirvetInstructionViewModel {
    func getUser() {
        getRemoteUser.execute { response in
            switch response {
            case .failure(let error):
                self.isLoading = false
                self.requestError = error.errorMessage ?? "error"
            case .success(let user):
                self.isLoading = false
                self.airvetUserDetails = AirvetUserDetails(
                    firstName: user.givenName?.description ?? "",
                    lastName: user.surname?.description ?? "",
                    email: user.email?.description ?? "",
                    insuranceId: user.insuranceId?.description ?? "")
            }
        }
    }
}
