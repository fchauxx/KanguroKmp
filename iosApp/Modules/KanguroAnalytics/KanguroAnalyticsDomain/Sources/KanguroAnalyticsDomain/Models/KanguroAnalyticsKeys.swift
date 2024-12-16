import Foundation

public struct KanguroAnalyticsEnums {
    
    public enum Screen: String {
        
        // MARK: - Onboarding
        case Welcome
        case LoginEmail
        case LoginPassword
        case ForgotPassword
        case ForceUpdatePassword
        case Cellphone
        case EmailValidation
        case BlockedUserAccount
        
        // MARK: - AdditionalInfo
        case AdditionalInfo
        case DonationCause
        case DonationType
        case MapDetail
        
        // MARK: - TabBar
        case Dashboard
        case Coverages
        case CentralChatbot
        case Cloud
        case More
        
        // MARK: - NewClaim
        case FileClaimChatbot
        case MedicalHistoryChatbot
        case CommunicationChatbot
        case PledgeOfHonor
        case Feedback
        
        // MARK: - Dashboard
        case VetAdvice
        case FrequentlyQuestions
        case NewPetParents

        // MARK: - Direct Pay to Vet
        case DTPUserInformationForm
        case DTPAlmostDone
        case DTPShareWithVet

        // MARK: - Coverage
        case Claims
        case ClaimDetails
        case CoverageDetails
        case PaymentSettings
        case CoverageWhatsCovered
        case PreventiveWhatsCovered
        case PaymentMethod
        case BankingInfo
        case BillingPreferences
        
        // MARK: - More
        case Profile
        case Reminders
        case SupportCause
        case Settings
        case ReferFriends
        
        public var value: String { self.rawValue }
    }
}
