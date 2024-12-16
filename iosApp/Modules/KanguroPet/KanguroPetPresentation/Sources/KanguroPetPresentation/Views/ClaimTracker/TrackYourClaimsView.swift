import SwiftUI
import KanguroDesignSystem
import KanguroPetDomain

public struct TrackYourClaimsView: View {

    @Environment(\.appLanguageValue) var language
    
    @ObservedObject var viewModel: TrackYourClaimsViewModel

    private let onBackPressed: SimpleClosure
    private let onClaimDetailPressed: (PetClaim) -> Void
    private let onDirectPayToVetPressed: (PetClaim) -> Void
    private let onNewClaimPressed: SimpleClosure

    var lang: String {
        language.rawValue
    }

    public init(
        viewModel: TrackYourClaimsViewModel,
        onBackPressed: @escaping () -> Void,
        onClaimDetailPressed: @escaping (PetClaim) -> Void,
        onDirectPayToVetPressed: @escaping (PetClaim) -> Void,
        onNewClaimPressed: @escaping () -> Void
    ) {
        self.viewModel = viewModel
        self.onBackPressed = onBackPressed
        self.onClaimDetailPressed = onClaimDetailPressed
        self.onDirectPayToVetPressed = onDirectPayToVetPressed
        self.onNewClaimPressed = onNewClaimPressed
    }

    public var body: some View {
        ZStack(alignment: .bottomTrailing) {
            VStack(alignment: .leading, spacing: 0) {
                backButton()

                title()

                if viewModel.isLoading {
                    LoadingView(
                        backgroundColor: Color.neutralBackground,
                        spinningColor: Color.white
                    ).padding(.bottom, InsetSpacing.tabBar)
                } else if viewModel.requestFailed != nil {
                    errorContent(message: viewModel.requestFailed)
                } else if viewModel.petClaims.isEmpty {
                    emptyContent()
                } else {
                    mainContent()
                }
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)

            newQuoteButton()
        }
        .padding(.horizontal, InsetSpacing.xs)
        .padding(.bottom, InsetSpacing.tabBar)
        .background(Color.neutralBackground)

    }

    private func backButton() -> some View {
        Button(action: onBackPressed) {
            HStack {
                Image.backIcon
                    .resizable()
                    .frame(width: IconSize.sm, height: IconSize.sm)
                    .foregroundColor(.secondaryDark)

                Text("claims.claims.back".localized(lang))
                    .font(.lato(.latoBlack, size: 14))
                    .foregroundColor(.secondaryDark)
            }
            .padding(.trailing, StackSpacing.xxxs)
            .frame(height: 40)
        }
    }

    private func title() -> some View {
        Text("moreActions.trackClaim.card".localized(lang))
            .titleSecondaryDarkestBold()
            .padding(.vertical, StackSpacing.xxxs)
    }

    private func mainContent() -> some View {
        ScrollView(.vertical, showsIndicators: true) {
            Spacer().frame(height: StackSpacing.xxxs)

            ForEach(viewModel.petClaims.indices, id: \.self) { index in
                let petClaim = viewModel.petClaims[index]

                if let claimTrackerCardViewModel = petClaim.toClaimTrackerViewModel() {
                    if claimTrackerCardViewModel.claimStatus == .Denied {
                        DeniedClaimTrackerCard(
                            model: claimTrackerCardViewModel,
                            onDetailPressed: {
                                onClaimDetailPressed(petClaim)
                            },
                            onDirectPaymentPressed: {
                                onDirectPayToVetPressed(petClaim)
                            }
                        )
                    } else {
                        ClaimTrackerCard(
                            model: claimTrackerCardViewModel,
                            onDetailPressed: {
                                onClaimDetailPressed(petClaim)
                            },
                            onDirectPaymentPressed: {
                                onDirectPayToVetPressed(petClaim)
                            }
                        )
                    }
                }
            }
            .padding(.bottom, StackSpacing.nano)

            Spacer().frame(height: StackSpacing.md)
        }
        .frame(maxWidth: .infinity)
    }

    private func errorContent(message: String?) -> some View {
        DataStatusResponseView(
            image: Image.requestFailedImage,
            title: message ?? "scheduled.error1.state".localized(lang),
            titleStyle: TextStyle(
                font: .museo(.museoSansBold, size: 21),
                color: .secondaryDarkest
            ),
            subtitle: "scheduled.error2.state".localized(lang),
            subtitleStyle: TextStyle(
                font: .lato(.latoRegular, size: 14),
                color: .tertiaryDarkest,
                underlined: true
            )
        )
        .onTapGesture {
            viewModel.fetchClaims()
        }
    }

    private func emptyContent() -> some View {
        DataStatusResponseView(
            image: Image.searchImage,
            title: "claimStatus.noClaim.label".localized(lang),
            titleStyle: TextStyle(
                font: .museo(.museoSansBold, size: 21),
                color: .secondaryDarkest
            )
        )
        .onTapGesture {
            viewModel.fetchClaims()
        }
    }

    private func newQuoteButton() -> some View {
        Button(action: onNewClaimPressed) {
            Image.addSingleIcon
                .renderingMode(.template)
                .foregroundColor(.white)
                .background(
                    Circle()
                        .foregroundStyle(Color.secondaryDarkest)
                        .frame(width: 40, height: 40)
                )
                .frame(width: 40, height: 40)
        }.padding(.bottom, InsetSpacing.xs)
    }
}

struct TrackYourClaimsView_Preview: PreviewProvider {
    static var previews: some View {
        let viewModelLoading = TrackYourClaimsViewModel()

        let viewModelError = TrackYourClaimsViewModel().tap(
            block: {
                $0.isLoading = false
                $0.requestFailed = "Something went wrong, please check your internet."
            }
        )

        let viewModelClaims = TrackYourClaimsViewModel().tap(
            block: {
                $0.isLoading = false
                $0.requestFailed = nil
                $0.petClaims = [
                    PetClaim(
                        id: "pet_id",
                        pet: Pet(
                            id: 1,
                            name: "Oliver",
                            type: .Dog,
                            petPictureUrl: nil
                        ),
                        type: .Accident,
                        status: .Submitted,
                        updatedAt: Date(),
                        amount: 100.0,
                        amountTransferred: 120.00,
                        reimbursementProcess: .VeterinarianReimbursement
                    ),
                    PetClaim(
                        id: "pet_id",
                        pet: Pet(
                            id: 1,
                            name: "Oliver",
                            type: .Dog,
                            petPictureUrl: nil
                        ),
                        type: .Accident,
                        status: .InReview,
                        updatedAt: Date(),
                        amount: 100.0,
                        amountTransferred: 120.00,
                        reimbursementProcess: .UserReimbursement
                    ),
                    PetClaim(
                        id: "pet_id",
                        pet: Pet(
                            id: 1,
                            name: "Oliver",
                            type: .Dog,
                            petPictureUrl: nil
                        ),
                        type: .Accident,
                        status: .PendingMedicalHistory,
                        updatedAt: Date(),
                        amount: 100.0,
                        amountTransferred: 120.00,
                        reimbursementProcess: .UserReimbursement,
                        statusDescription: ClaimStatusDescription(description: "You have pending medical history", type: .Info)
                    ),
                    PetClaim(
                        id: "pet_id",
                        pet: Pet(
                            id: 1,
                            name: "Oliver",
                            type: .Dog,
                            petPictureUrl: nil
                        ),
                        type: .Accident,
                        status: .Denied,
                        updatedAt: Date(),
                        amount: 100.0,
                        amountTransferred: 120.00,
                        reimbursementProcess: .UserReimbursement
                    ),
                    PetClaim(
                        id: "pet_id",
                        pet: Pet(
                            id: 1,
                            name: "Oliver",
                            type: .Dog,
                            petPictureUrl: nil
                        ),
                        type: .Accident,
                        status: .MedicalHistoryInReview,
                        updatedAt: Date(),
                        amount: 100.0,
                        amountTransferred: 120.00,
                        reimbursementProcess: .UserReimbursement
                    )
                ]
            }
        )

        let viewModelEmpty = TrackYourClaimsViewModel().tap(
            block: {
                $0.isLoading = false
                $0.requestFailed = nil
            }
        )

        TrackYourClaimsView(
            viewModel: viewModelClaims,
            onBackPressed: { },
            onClaimDetailPressed: {_ in },
            onDirectPayToVetPressed: {_ in },
            onNewClaimPressed: { }
        )

        TrackYourClaimsView(
            viewModel: viewModelLoading,
            onBackPressed: { },
            onClaimDetailPressed: {_ in },
            onDirectPayToVetPressed: {_ in },
            onNewClaimPressed: { }
        )

        TrackYourClaimsView(
            viewModel: viewModelError,
            onBackPressed: { },
            onClaimDetailPressed: {_ in },
            onDirectPayToVetPressed: {_ in },
            onNewClaimPressed: { }
        )

        TrackYourClaimsView(
            viewModel: viewModelEmpty,
            onBackPressed: { },
            onClaimDetailPressed: {_ in },
            onDirectPayToVetPressed: {_ in },
            onNewClaimPressed: { }
        )
    }
}
