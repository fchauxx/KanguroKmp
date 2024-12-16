import SwiftUI
import KanguroSharedDomain

public enum RentersPage: Identifiable, Hashable {
    
    case rentersDashboard(renterPolicies: [RenterPolicy])
    case rentersUpselling
    case tabBarChat(product: ProductType)
    case claimsChat(policy: RenterPolicy)
    case onboardingChat(policy: RenterPolicy)
    case coverageDetails(policy: RenterPolicy)
    case editPolicyDetails(policy: RenterPolicy)
    case scheduleItemSummaryView(policy: RenterPolicy)
    case scheduleItemAddPictureView
    case scheduleItemCategoryView
    case scheduleNewItem
    case editAdditionalCoverage(policy: RenterPolicy)
    case rentersFAQ
    case whatisCoveredRenters
    
    case uploadFileVideo
    case getQuote
    case paymentSettings
    case paymentMethods
    case bankingInfo
    case supportCause
    case donationCause(type: DonationType, donationCauses: [DonationCause])
    case donationTypeCause
    case homeFAQ
    case airvetInstruction

    // MARK: - Computed Properties
    public var id: String {
        switch self {
        case .rentersDashboard:
            return "rentersDashboard"
        case .rentersUpselling:
            return "rentersUpselling"
        case .tabBarChat:
            return "tabBarChat"
        case .onboardingChat:
            return "onboardingChat"
        case .claimsChat:
            return "claimsChat"
        case .coverageDetails:
            return "coverageDetails"
        case .editPolicyDetails:
            return "editPolicyDetails"
        case .scheduleItemSummaryView:
            return "scheduleItemSummaryView"
        case .scheduleItemAddPictureView:
            return "scheduleItemAddPictureView"
        case .scheduleItemCategoryView:
            return "scheduleItemCategoryView"
        case .scheduleNewItem:
            return "scheduleNewItem"
        case .getQuote:
            return "getQuote"
        case .paymentSettings:
            return "paymentSettings"
        case .paymentMethods:
            return "paymentMethods"
        case .bankingInfo:
            return "bankingInfo"
        case .supportCause:
            return "supportCause"
        case .donationCause:
            return "donationCause"
        case .donationTypeCause:
            return "donationTypeCause"
        case .editAdditionalCoverage:
            return "editAdditionalCoverage"
        case .rentersFAQ:
            return "rentersFAQ"
        case .uploadFileVideo:
            return "uploadFileVideo"
        case .whatisCoveredRenters:
            return "whatisCoveredRenters"
        case .homeFAQ:
            return "homeFAQ"
        case .airvetInstruction:
            return "airvetInstruction"
        }
        
    }
    private var canGoBackList: [RentersPage] {
        []
    }
}
