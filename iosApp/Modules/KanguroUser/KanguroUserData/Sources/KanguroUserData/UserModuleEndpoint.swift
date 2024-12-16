import KanguroSharedDomain
import KanguroNetworkDomain

public enum UserModuleEndpoint: Endpoint {
    
    case login
    case refreshToken
    case deleteAccount
    case bankAccount
    case firebaseToken
    case otpValidation(email: String, code: String)
    case otpSendRequest
    case hasAccessBlocked(userID:String)
    case getUser
    case syncUserDonation
    
    public var path: String {
        switch self {
        case .login:
            return "user/login"
        case .refreshToken:
            return "user/refreshToken"
        case .deleteAccount:
            return "user/updateDeleteUserDataFlag"
        case .bankAccount:
            return "user/UserAccount"
        case .firebaseToken:
            return "user/UserFirebaseToken"
        case .otpValidation(let email, let code):
            return "user/otpsms/validate?Email=\(email)&Code=\(code)"
        case .otpSendRequest:
            return "user/otpsms/request"
        case .hasAccessBlocked(userID: let userID):
            return "user/\(userID)/HasAccessBlocked"
        case .getUser:
            return "user/"
        case .syncUserDonation:
            return "Donation/SyncUserDonation"
        }
    }
}
