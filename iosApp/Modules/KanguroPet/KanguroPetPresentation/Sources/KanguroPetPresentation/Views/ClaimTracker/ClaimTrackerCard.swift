import SwiftUI
import KanguroDesignSystem
import KanguroPetDomain
import KanguroSharedDomain
import Kingfisher

public struct ClaimTrackerCard: View {

    private let viewModel: ClaimTrackerCardViewModel
    private let onDetailPressed: SimpleClosure
    private let onDirectPaymentPressed: SimpleClosure

    @Environment(\.appLanguageValue) var language
    var lang: String {
        language.rawValue
    }

    public init(model: ClaimTrackerCardViewModel, onDetailPressed: @escaping () -> Void, onDirectPaymentPressed: @escaping () -> Void) {
        self.viewModel = model
        self.onDetailPressed = onDetailPressed
        self.onDirectPaymentPressed = onDirectPaymentPressed
    }

    public var body: some View {
        VStack(spacing: StackSpacing.xxs) {
            headerSection()
                .padding([.horizontal, .top], StackSpacing.xxxs)

            NewClaimTrackerView(claimStatus: viewModel.claimStatus)
                .padding(
                    .bottom,
                    viewModel.reimbursementProcess == .VeterinarianReimbursement || viewModel.claimStatusDescription != nil ? 0 : StackSpacing.xxxs
                )

            if let description = viewModel.claimStatusDescription {
                Button(action: onDetailPressed) {
                    DescriptionContainer(
                        description: description,
                        isAlert: viewModel.claimStatus == .PendingMedicalHistory
                    )
                }.padding(.horizontal, StackSpacing.xxxs)
                    .padding(
                        .bottom,
                        viewModel.reimbursementProcess == .VeterinarianReimbursement ? 0 : StackSpacing.xxxs
                    )
            }

            if viewModel.reimbursementProcess == .VeterinarianReimbursement {
                Button(action: {
                    onDirectPaymentPressed()
                }, label: {
                    HStack(spacing: StackSpacing.nano) {
                        Text("claimCard.direct.pay".localized(lang))
                            .foregroundStyle(Color.white)
                            .underline()
                            .font(.lato(.latoBold, size: 12))
                        Image.share.foregroundColor(Color.white)

                    }
                    .frame(maxWidth: .infinity)
                })
                .frame(maxWidth: .infinity)
                .frame(height: 32)
                .background(Color.secondaryDarkest)
            }
        }
        .background(Color.white)
        .cornerRadius(CornerRadius.sm)
        .overlay(
            RoundedRectangle(cornerRadius: CornerRadius.sm)
                .stroke(Color.neutralBackground, lineWidth: 1)
        )
    }
    private func headerSection() -> some View {
        HStack(alignment: .top) {
            petImage()

            petInformation()
                .padding(.trailing, StackSpacing.nano)

            Spacer()

            DetailButton(label: "claimCard.detail".localized(lang), onPressed: onDetailPressed)
        }
    }

    private func petImage() -> some View {
        let defaultImage = viewModel.petType == .Dog ? Image.defaultDogImage : Image.defaultCatImage

        return KFImage(URL(string: viewModel.petPictureUrl ?? ""))
            .placeholder {
                defaultImage
                    .resizable()
                    .aspectRatio(contentMode: .fill)
                    .frame(width: 40, height: 40, alignment: .center)
            }
            .resizable()
            .aspectRatio(contentMode: .fill)
            .frame(width: 40, height: 40, alignment: .center)
            .clipShape(Circle())
    }

    private func petInformation() -> some View {
        VStack(alignment: .leading, spacing: StackSpacing.quarck) {
            Text(viewModel.petName)
                .bodySecondaryDarkestBold()

            labelValue(
                label: "claimCard.claim.amount".localized(lang),
                value: viewModel.claimAmount
            )

            labelValue(
                label: "claimCard.last.update".localized(lang),
                value: viewModel.claimLastUpdated
            )
        }
    }

    private func labelValue(label: String, value: String)  -> some View {
        HStack(spacing: 0) {
            Text(label)
                .font(.lato(.latoRegular, size: 11))
                .foregroundStyle(Color.secondaryDark)
            Text(value)
                .font(.lato(.latoBold, size: 11))
                .foregroundStyle(Color.secondaryDark)
        }
    }
}

struct ClaimTrackerCard_Previews: PreviewProvider {
    static var previews: some View {

        let viewModelSubmitted = ClaimTrackerCardViewModel(
            id: "",
            petName: "Oliver",
            petType: .Dog,
            claimType: .Accident,
            claimStatus: .Submitted,
            claimLastUpdated: "Sep 04, 2021",
            claimAmount: "$100.00",
            claimAmountPaid: "$100.00",
            reimbursementProcess: .UserReimbursement,
            petPictureUrl: nil
        )

        let viewModelInReview = ClaimTrackerCardViewModel(
            id: "",
            petName: "Oliver",
            petType: .Dog,
            claimType: .Accident,
            claimStatus: .InReview,
            claimLastUpdated: "Sep 04, 2021",
            claimAmount: "$100.00",
            claimAmountPaid: "$100.00",
            reimbursementProcess: .VeterinarianReimbursement,
            claimStatusDescription: "Your claim is in review",
            petPictureUrl: nil
        )

        let viewModelPendingMedicalHistory = ClaimTrackerCardViewModel(
            id: "",
            petName: "Oliver",
            petType: .Dog,
            claimType: .Accident,
            claimStatus: .PendingMedicalHistory,
            claimLastUpdated: "Sep 04, 2021",
            claimAmount: "$100.00",
            claimAmountPaid: "$100.00",
            reimbursementProcess: .UserReimbursement,
            claimStatusDescription: "We received your claim, however either no Medical Records were provided or they are incomplete. No worries we are hard at work to secure those from. \n\n Additionally, if you have them, please click here.",
            petPictureUrl: nil
        )

        return ScrollView {
            ClaimTrackerCard(model: viewModelSubmitted, onDetailPressed: {}, onDirectPaymentPressed: {})
            ClaimTrackerCard(model: viewModelInReview, onDetailPressed: {}, onDirectPaymentPressed: {})
            ClaimTrackerCard(model: viewModelPendingMedicalHistory, onDetailPressed: {}, onDirectPaymentPressed: {})
        }.padding()
    }
}
