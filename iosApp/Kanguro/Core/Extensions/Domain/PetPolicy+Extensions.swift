import Foundation
import KanguroSharedDomain
import KanguroPetDomain

extension PetPolicy {
    // MARK: Computed Properties
    var startDateFormatted: String {
        guard let date = self.startDate else { return "" }
        return date.MMddyyy_UTC
    }
    var endDateFormatted: String {
        guard let date = self.endDate else { return "" }
        return date.MMddyyy_UTC
    }
    var waitingPeriodFormatted: String {
        guard let date = self.waitingPeriod else { return "" }
        return date.MMddyyy_UTC
    }
}
