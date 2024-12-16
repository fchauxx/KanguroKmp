import Foundation

struct ChatSummary: Codable {

    // MARK: - Stored Properties
    let pet: String
    let claim: String
    let date: Date
    let attachments: Int
    let amount: Double
    let paymentMethod: String?
    let claimId: String?

    // MARK: - Computed Properties
    var summaryData: [SummaryData] {
        return [SummaryData(leadingTitle: "summary.pet".localized,
                            traillingTitle: pet,
                            summaryType: .regular),
                SummaryData(leadingTitle: "summary.claim".localized,
                            traillingTitle: claim,
                            summaryType: .regular),
                SummaryData(leadingTitle: "summary.date".localized,
                            traillingTitle: date.USADate_UTC,
                            summaryType: .regular),
                SummaryData(leadingTitle: "summary.attachments".localized,
                            traillingTitle: "\(attachments)",
                            summaryType: .regular),
                SummaryData(leadingTitle: "summary.amount".localized,
                            traillingTitle: "$\(amount)",
                            summaryType: .regular),
                SummaryData(leadingTitle: "summary.reimbursementMethod".localized,
                            traillingTitle: "summary.bankAccount".localized,
                            summaryType: .regular)]
    }
}
