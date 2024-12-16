import Foundation
import KanguroPetDomain

public extension PetClaim {
    func toClaimTrackerViewModel() -> ClaimTrackerCardViewModel? {
        guard let petName = self.pet?.name,
              let petType = self.pet?.type,
              let claimType = self.type,
              let claimStatus = self.status,
              let claimLastUpdated = getDateFormatted(date: self.updatedAt),
              let amountFormatted = getAmountFormatted(value: self.amount),
              let amountPaidFormatted = getAmountFormatted(value: self.amountTransferred),
              let reimbursementProcess = self.reimbursementProcess
        else { return nil }

        return  ClaimTrackerCardViewModel(
            id: self.id,
            petName: petName,
            petType: petType,
            claimType: claimType,
            claimStatus: claimStatus,
            claimLastUpdated: claimLastUpdated,
            claimAmount: amountFormatted,
            claimAmountPaid: amountPaidFormatted,
            reimbursementProcess: reimbursementProcess,
            claimStatusDescription: self.statusDescription?.description,
            petPictureUrl: self.pet?.petPictureUrl
        )
    }

    private func getAmountFormatted(value: Double?) -> String? {
        guard let value else { return nil }
        return "$\(String(format: "%.2f", value))"
    }

    private func getDateFormatted(date: Date?) -> String? {
        guard let date else { return nil}
        let formatter = DateFormatter()
        formatter.timeZone = .current
        formatter.dateFormat = "MMM dd, yyyy"
        return formatter.string(from: date)
    }
}
