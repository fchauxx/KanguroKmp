import Foundation
import KanguroSharedDomain

struct ClaimTrackerViewItemData {
    
    // MARK: - Stored Properties
    var status: ClaimStatus
    var isConfirmed: Bool
    var hasPendingCommunications: Bool
    var isNextItemConfirmed: Bool
    var isNextLastConfirmedItem: Bool
    
    // MARK: - Computed Properties
    var shouldShowCommunicationProgress: Bool {
        return isNextLastConfirmedItem && hasPendingCommunications
    }
    var shouldBeAlertView: Bool {
        return !isNextItemConfirmed && hasPendingCommunications && isConfirmed
    }
}
