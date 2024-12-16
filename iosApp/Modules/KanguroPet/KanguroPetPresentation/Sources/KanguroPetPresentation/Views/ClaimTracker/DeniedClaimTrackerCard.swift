import SwiftUI
import KanguroDesignSystem
import KanguroPetDomain
import KanguroSharedDomain

public struct DeniedClaimTrackerCard: View {

    private let viewModel: ClaimTrackerCardViewModel
    private let onDetailPressed: SimpleClosure

    @Environment(\.appLanguageValue) var language
    var lang: String {
        language.rawValue
    }

    public init(model: ClaimTrackerCardViewModel, onDetailPressed: @escaping () -> Void, onDirectPaymentPressed: @escaping () -> Void) {
        self.viewModel = model
        self.onDetailPressed = onDetailPressed
    }

    public var body: some View {
        ZStack(alignment: .leading) {
            HStack(alignment: .top) {
                petImage()
                petInformation()
                Spacer()
                detailButtonAndStatus()
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .padding(InsetSpacing.xxs)

            Rectangle()
                .foregroundColor(Color.negativeDark)
                .frame(width: InsetSpacing.nano)

        }
        .frame(maxHeight: 100)
        .background(Color.white)
        .cornerRadius(CornerRadius.sm)
        .overlay(
            RoundedRectangle(cornerRadius: CornerRadius.sm)
                .stroke(Color.neutralBackground, lineWidth: 1)
        )
    }

    private func petImage() -> some View {
        let image = if viewModel.petType == .Dog{
            Image.defaultDogImage
        } else {
            Image.defaultCatImage
        }

        return image
            .resizable()
            .aspectRatio(contentMode: .fill)
            .frame(width: 40, height: 40, alignment: .center)
            .clipShape(Circle())
    }

    private func petInformation() -> some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(viewModel.petName)
                .bodySecondaryDarkestBold()

            Text("claims.paid".localized(lang) + viewModel.claimAmountPaid)
                .font(.lato(.latoRegular, size: 14))
                .foregroundColor(.secondaryDark)

            Text(getClaimTypeText())
                .font(.lato(.latoRegular, size: 11))
                .foregroundStyle(Color.neutralMedium)

            labelValue(
                label: "claimCard.closed.in".localized(lang), 
                value: viewModel.claimLastUpdated
            )
        }
    }

    private func getClaimTypeText() -> String {
        switch viewModel.claimType {
        case .Illness:
            "claims.accident.label".localized(lang)
        case .Accident:
            "claims.accident.label".localized(lang)
        case .Other:
            "claims.other.label".localized(lang)
        }
    }

    private func labelValue(label: String, value: String)  -> some View {
        HStack(spacing: 0) {
            Text(label)
                .font(.lato(.latoRegular, size: 11))
                .foregroundStyle(Color.neutralMedium)
            Text(value)
                .font(.lato(.latoBold, size: 11))
                .foregroundStyle(Color.neutralMedium)
        }
    }

    private func detailButtonAndStatus() -> some View {
        VStack(alignment: .trailing, spacing: StackSpacing.xxxs) {
            DetailButton(label: "claimCard.detail".localized(lang), onPressed: onDetailPressed)

            HStack(spacing: 0) {
                Text("claimStatus.denied".localized(lang))
                    .font(.lato(.latoRegular, size: 14))
                    .foregroundColor(.negativeDarkest)

                Image.deniedFilled
                    .padding(.leading, StackSpacing.quarck)
            }
        }
    }
}

struct DeniedClaimTrackerCard_Previews: PreviewProvider {
    static var previews: some View {
        let viewModelDenied = ClaimTrackerCardViewModel(
            id: "",
            petName: "Oliver",
            petType: .Dog,
            claimType: .Accident,
            claimStatus: .Denied,
            claimLastUpdated: "Sep 04, 2021",
            claimAmount: "$100.00",
            claimAmountPaid: "$100.00",
            reimbursementProcess: .UserReimbursement,
            claimStatusDescription: nil,
            petPictureUrl: nil
        )

        DeniedClaimTrackerCard(
            model: viewModelDenied,
            onDetailPressed: {},
            onDirectPaymentPressed: {}
        )
    }
}
