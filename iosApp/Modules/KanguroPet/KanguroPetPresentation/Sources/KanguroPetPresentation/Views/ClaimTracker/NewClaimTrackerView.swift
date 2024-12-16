import Foundation
import SwiftUI
import KanguroSharedDomain
import KanguroDesignSystem

enum LinkStatus {
    case completed
    case inProgress
    case alert
}

public struct NewClaimTrackerView: View {
    
    let claimStatus: ClaimStatus

    @Environment(\.appLanguageValue) var language
    var lang: String {
        language.rawValue
    }

    public init(claimStatus: ClaimStatus) {
        self.claimStatus = claimStatus
    }

    public var body: some View {
        VStack {
            switch claimStatus {
            case .Submitted:
                stepSubmitted()
            case .InReview:
                stepInReview()
            case .Closed, .Approved, .Paid:
                stepClosed()
            case .PendingMedicalHistory:
                stepPendingMedicalHistory()
            case .MedicalHistoryInReview:
                stepMedicalHistoryInReview()
            default:
                stepGeneric()
            }
        }
    }

    private func stepGeneric() -> some View {
        HStack(spacing: 0) {
            // Step Submitted
            incompleteStep(label: "claimStatus.submitted".localized(lang), number: 1, isStart: true)

            // Step inReview
            incompleteStep(label: "claimStatus.review".localized(lang), number: 2)

            // Step Medical Records
            incompleteStep(label: "claimStatus.medicalHistoryInReview".localized(lang), number: 3)

            // Step Closed
            incompleteStep(label: "claimStatus.closed".localized(lang), number: 4, isEnd: true)
        }.frame(maxWidth: .infinity)
    }

    private func stepSubmitted() -> some View {
        HStack(spacing: 0) {
            // Step Submitted
            completedStep(label: "claimStatus.submitted".localized(lang), number: 1, isStart: true)

            // Step inReview
            incompleteStep(label: "claimStatus.review".localized(lang), number: 2)

            // Step Medical Records
            incompleteStep(label: "claimStatus.medicalHistoryInReview".localized(lang), number: 3)

            // Step Closed
            incompleteStep(label: "claimStatus.closed".localized(lang), number: 4, isEnd: true)
        }.frame(maxWidth: .infinity)
    }

    private func stepInReview() -> some View {
        HStack(spacing: 0) {
            // Step Submitted
            completedStep(label: "claimStatus.submitted".localized(lang), number: 1, isStart: true)

            // Step inReview
            inProgressStep(label: "claimStatus.review".localized(lang), number: 2)

            // Step Medical Records
            incompleteStep(label: "claimStatus.medicalHistoryInReview".localized(lang), number: 3)

            // Step Closed
            incompleteStep(label: "claimStatus.closed".localized(lang), number: 4, isEnd: true)
        }.frame(maxWidth: .infinity)
    }

    private func stepMedicalHistoryInReview() -> some View {
        HStack(spacing: 0) {
            // Step Submitted
            completedStep(label: "claimStatus.submitted".localized(lang), number: 1, isStart: true)

            // Step inReview
            completedStep(label: "claimStatus.review".localized(lang), number: 2)

            // Step Medical Records
            inProgressStep(label: "claimStatus.medicalHistoryInReview".localized(lang), number: 3)

            // Step Closed
            incompleteStep(label: "claimStatus.closed".localized(lang), number: 4, isEnd: true)
        }.frame(maxWidth: .infinity)
    }

    private func stepPendingMedicalHistory() -> some View {
        HStack(spacing: 0) {
            // Step Submitted
            completedStep(label: "claimStatus.submitted".localized(lang), number: 1, isStart: true)

            // Step inReview
            completedStep(label: "claimStatus.review".localized(lang), number: 2)

            // Step Medical Records
            alertStep(label: "claimStatus.pendingMedicalHistory".localized(lang), number: 3)

            // Step Closed
            incompleteStep(label: "claimStatus.closed".localized(lang), number: 4, isEnd: true)
        }.frame(maxWidth: .infinity)
    }

    private func stepClosed() -> some View {
        HStack(spacing: 0) {
            // Step Submitted
            completedStep(label: "claimStatus.submitted".localized(lang), number: 1, isStart: true)

            // Step inReview
            completedStep(label: "claimStatus.review".localized(lang), number: 2)

            // Step Medical Records
            completedStep(label: "claimStatus.medicalHistoryInReview".localized(lang), number: 3)

            // Step Closed
            completedStep(label: "claimStatus.closed".localized(lang), number: 4, isEnd: true)
        }.frame(maxWidth: .infinity)
    }

    private func incompleteStep(label: String, number: Int, isStart: Bool = false, isEnd: Bool = false) -> some View {
        VStack {
            HStack(spacing: 0) {
                stepLink(status: nil, isHidden: isStart)
                incompletedIcon(stepNumber: number)
                stepLink(status: nil, isHidden: isEnd)
            }

            stepLabel(text: label, color: Color.secondaryLight)
        }
    }

    private func completedStep(label: String, number: Int, isStart: Bool = false, isEnd: Bool = false) -> some View {
        VStack {
            HStack(spacing: 0) {
                stepLink(status: .completed, isHidden: isStart)
                completedIcon()
                stepLink(status: .completed, isHidden: isEnd)
            }

            stepLabel(text: label, color: Color.secondaryMedium)
        }
    }

    private func inProgressStep(label: String, number: Int, isStart: Bool = false, isEnd: Bool = false) -> some View {
        VStack {
            HStack(spacing: 0) {
                stepLink(status: .completed, isHidden: isStart)
                inProgressIcon(stepNumber: number)
                stepLink(status: nil, isHidden: isEnd)
            }

            stepLabel(text: label, color: Color.secondaryDark)
        }
    }

    private func alertStep(label: String, number: Int, isStart: Bool = false, isEnd: Bool = false) -> some View {
        VStack {
            HStack(spacing: 0) {
                stepLink(status: .alert, isHidden: isStart)
                alertIcon()
                stepLink(status: nil, isHidden: isEnd)
            }

            stepLabel(text: label, color: Color.secondaryDark)
        }
    }

    private func completedIcon() -> some View {
        VStack {
            ZStack {
                Circle()
                    .fill(Color.neutralBackground)
                    .frame(width: 40, height: 40)

                Circle()
                    .fill(Color.positiveMedium)
                    .frame(width: 36, height: 36)

                Image.stepCompleted
            }
        }
    }

    private func incompletedIcon(stepNumber: Int, isAlert: Bool = false) -> some View {
        VStack {
            ZStack {
                Circle()
                    .fill(Color.neutralBackground)
                    .frame(width: 40, height: 40)


                Text("\(stepNumber)")
                    .font(.system(size: 20, weight: .bold))
                    .foregroundStyle(Color.secondaryLight)
            }
        }
    }

    private func inProgressIcon(stepNumber: Int) -> some View {
        ZStack {
            Circle()
                .fill(Color.positiveMedium)
                .frame(width: 40, height: 40)

            Circle()
                .fill(Color.neutralBackground)
                .frame(width: 36, height: 36)

            Text("\(stepNumber)")
                .font(.system(size: 20, weight: .bold))
                .foregroundStyle(Color.positiveMedium)
        }
    }

    private func alertIcon() -> some View {
        ZStack {
            Circle()
                .fill(Color.neutralBackground)
                .frame(width: 40, height: 40)

            Circle()
                .fill(Color.warningMedium)
                .frame(width: 36, height: 36)

            Image.stepAlert
        }
    }

    private func stepLabel(text: String, color: Color) -> some View {
        Text(text)
            .font(.system(size: 12, weight: .bold))
            .lineLimit(2, reservesSpace: true)
            .multilineTextAlignment(.center)
            .foregroundStyle(color)
            .padding(.horizontal, StackSpacing.semiquarck)
    }

    private func stepLink(status: LinkStatus?, isHidden: Bool = false) -> some View {
        ZStack {
            Rectangle()
                .fill(isHidden ? .clear : Color.neutralBackground)
                .frame(minHeight: 8, maxHeight: 8)

            if let status, !isHidden {
                switch status {
                case .completed:
                    Rectangle()
                        .fill(Color.positiveMedium)
                        .frame(minHeight: 4, maxHeight: 4)

                case .inProgress:
                    Rectangle()
                        .fill(Color.positiveMedium)
                        .frame(minHeight: 4, maxHeight: 4)
                        .padding(.trailing, 30 )

                case .alert:
                    Rectangle()
                        .fill(
                            LinearGradient(
                                gradient: Gradient(
                                    colors: [ Color.positiveMedium, Color.warningMedium]
                                ),
                                startPoint: .leading,
                                endPoint: .trailing
                            )
                        )
                        .frame(minHeight: 4, maxHeight: 4)
                }
            }
        }
    }

}

struct NewClaimTrackerView_Previews: PreviewProvider {
    static var previews: some View {
        VStack(spacing: 20){
            NewClaimTrackerView(claimStatus: .Unknown)
            NewClaimTrackerView(claimStatus: .Submitted)
            NewClaimTrackerView(claimStatus: .InReview)
            NewClaimTrackerView(claimStatus: .MedicalHistoryInReview)
            NewClaimTrackerView(claimStatus: .PendingMedicalHistory)
            NewClaimTrackerView(claimStatus: .Closed)
        }.frame(width: .infinity)
    }
}
