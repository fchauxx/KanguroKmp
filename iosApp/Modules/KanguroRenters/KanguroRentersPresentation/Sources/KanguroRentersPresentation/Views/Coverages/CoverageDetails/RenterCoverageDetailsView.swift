import SwiftUI
import KanguroSharedDomain
import KanguroDesignSystem

public struct RenterCoverageDetailsView: View {
    
    // MARK: - Wrapped Properties
    @ObservedObject var viewModel: RenterCoverageDetailsViewModel
    @State var popUpData: PopUpData?
    @Environment(\.appLanguageValue) var language
    
    // MARK: - Stored Properties
    //temporary until we have edits enabled
    var editPolicyPopUpData: PopUpData {
        PopUpData(title: "dashboard.popUp.editPolicy.title".localized(lang),
                  description: "dashboard.popUp.editPolicy.description".localized(lang),
                  highlightedData: (text: "rentersuw@kanguroinsurance.com",
                                    style: TextStyle(font: .lato(.latoRegular, size: 13),
                                                     color: .tertiaryDarkest,
                                                     underlined: true)))
    }
    
    var fileClaimUpUpData: PopUpData {
        PopUpData(title: "moreActions.fileClaim.card".localized(lang),
                  description: "dashboard.fileClaim.popup.label".localized(lang),
                  highlightedData: (text: "rentersclaims@kanguroinsurance.com",
                                    style: TextStyle(font: .lato(.latoRegular, size: 13),
                                                     color: .tertiaryDarkest,
                                                     underlined: true)))
    }
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    
    // MARK: - Actions
    var backAction: SimpleClosure
    var didTapEditPolicyDetailsAction: SimpleClosure
    var didTapEditAdditionalCoverageAction: SimpleClosure
    var didTapMyScheduledItemsAction: SimpleClosure
    var didTapEditAdditionalPartiesAction: SimpleClosure
    var didTapBillingPreferencesAction: SimpleClosure
    var didTapFileAClaimAction: SimpleClosure
    var didTapTrackYourClaimAction: SimpleClosure
    var didTapChangeMyAddressAction: SimpleClosure
    var didTapFaqAction: SimpleClosure
    var didTapPhoneNumberAction: SimpleClosure
    var didTapClaimEmailAction: SimpleClosure
    var didTapPopUpEmailAction: StringClosure
    var didTapWhatisCoveredAction: SimpleClosure
    var didTapDocumentAction: AnyClosure = { _ in }
    
    public init(viewModel: RenterCoverageDetailsViewModel,
                popUpData: PopUpData? = nil,
                backAction: @escaping SimpleClosure,
                didTapEditPolicyDetailsAction: @escaping SimpleClosure,
                didTapEditAdditionalCoverageAction: @escaping SimpleClosure,
                didTapMyScheduledItemsAction: @escaping SimpleClosure,
                didTapEditAdditionalPartiesAction: @escaping SimpleClosure,
                didTapBillingPreferencesAction: @escaping SimpleClosure,
                didTapFileAClaimAction: @escaping SimpleClosure,
                didTapTrackYourClaimAction: @escaping SimpleClosure,
                didTapChangeMyAddressAction: @escaping SimpleClosure,
                didTapFaqAction: @escaping SimpleClosure,
                didTapPhoneNumberAction: @escaping SimpleClosure,
                didTapClaimEmailAction: @escaping SimpleClosure,
                didTapPopUpEmailAction: @escaping StringClosure,
                didTapWhatisCoveredAction: @escaping SimpleClosure,
                didTapDocumentAction: @escaping  AnyClosure = { _ in }
    ) {
        self.popUpData = popUpData
        self.viewModel = viewModel
        self.backAction = backAction
        self.didTapEditPolicyDetailsAction = didTapEditPolicyDetailsAction
        self.didTapEditAdditionalCoverageAction = didTapEditAdditionalCoverageAction
        self.didTapMyScheduledItemsAction = didTapMyScheduledItemsAction
        self.didTapEditAdditionalPartiesAction = didTapEditAdditionalPartiesAction
        self.didTapBillingPreferencesAction = didTapBillingPreferencesAction
        self.didTapFileAClaimAction = didTapFileAClaimAction
        self.didTapTrackYourClaimAction = didTapTrackYourClaimAction
        self.didTapChangeMyAddressAction = didTapChangeMyAddressAction
        self.didTapFaqAction = didTapFaqAction
        self.didTapPhoneNumberAction = didTapPhoneNumberAction
        self.didTapClaimEmailAction = didTapClaimEmailAction
        self.didTapPopUpEmailAction = didTapPopUpEmailAction
        self.didTapWhatisCoveredAction = didTapWhatisCoveredAction
        self.didTapDocumentAction = didTapDocumentAction
    }
    
    public var body: some View {
        ZStack {
            if !viewModel.requestError.isEmpty {
                createErrorDataView()
            } else if !viewModel.isLoading {
                ScrollingViewScreenBase(
                    contentView: AnyView(createContentView()),
                    headerImage: viewModel.policy.dwellingType?.image,
                    backAction: backAction
                )
                
                if let popUpData {
                    PopUpInfoView(
                        data: popUpData,
                        closeAction: {
                            self.popUpData = nil
                        },
                        didTapPopUpEmailAction: didTapPopUpEmailAction
                    )
                }
            } else {
                LoadingView()
            }
        }
        .onAppear {
            viewModel.getData()
        }
    }
    
    @ViewBuilder
    private func createErrorDataView() -> some View {
        DataStatusResponseView(image: Image.errorImage,
                               title: "scheduled.error1.state".localized(lang),
                               titleStyle: TextStyle(font: .museo(.museoSansBold, size: 21),
                                                     color: .secondaryDarkest),
                               subtitle: "scheduled.error2.state".localized(lang),
                               subtitleStyle: TextStyle(font: .lato(.latoRegular, size: 14),
                                                        color: .tertiaryDarkest,
                                                        underlined: true))
        .onTapGesture {
            viewModel.getData()
        }
    }
}
