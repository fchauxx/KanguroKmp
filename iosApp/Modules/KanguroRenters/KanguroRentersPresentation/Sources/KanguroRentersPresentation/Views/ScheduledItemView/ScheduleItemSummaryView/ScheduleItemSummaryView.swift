import SwiftUI
import KanguroDesignSystem
import KanguroSharedDomain
import KanguroRentersDomain

public struct ScheduleItemSummaryView: View, PresenterNavigationProtocol {
    
    // MARK: - Dependencies
    @ObservedObject var viewModel: ScheduleItemSummaryViewModel
    @State var deleteScheduledItemPopUp: Bool = false
    @Environment(\.dismiss) var dismiss
    @Environment(\.appLanguageValue) var language
    
    var lang: String {
        language.rawValue
    }
    
    // MARK: Stored Properties
    var didTapUploadOrEditPicture: ScheduledItemClosure
    var didTapAddMoreItems: StringClosure
    
    // MARK: - Actions
    var backAction: SimpleClosure?
    var closeAction: SimpleClosure?
    
    // MARK: - Initializer
    public init(viewModel: ScheduleItemSummaryViewModel,
                didTapUploadOrEditPicture: @escaping ScheduledItemClosure,
                didTapAddMoreItems: @escaping StringClosure,
                backAction: SimpleClosure? = nil,
                closeAction: SimpleClosure? = nil
    ) {
        self.viewModel = viewModel
        self.didTapUploadOrEditPicture = didTapUploadOrEditPicture
        self.didTapAddMoreItems = didTapAddMoreItems
        self.backAction = backAction
        self.closeAction = closeAction
    }
    
    public var body: some View {
        ZStack {
            VStack {
                if viewModel.isLoading {
                    LoadingView()
                } else {
                    PresenterNavigationHeaderView(closeAction: {
                        self.dismiss()
                    })
                    .padding(.horizontal, InsetSpacing.xxs)
                    .padding(.top, InsetSpacing.xs)
                    if viewModel.showError {
                        createErrorDataView()
                    } else {
                        VStack {
                            if !viewModel.scheduledItemsList.isEmpty {
                                ScheduledItemViewHeader(text: "scheduled.header.label".localized(lang))
                                    .padding(.bottom, InsetSpacing.xs)
                                
                                ScrollView(showsIndicators: false) {
                                    ForEach(viewModel.scheduledItemsList) { item in
                                        ItemCardView(id: item.id ?? "",
                                                     title: item.name ?? "",
                                                     category: item.category?.category.rawValue ?? "",
                                                     subtitle: item.valuation?.getCurrencyFormatted() ?? "",
                                                     isConfirmed: item.hasAtLeastOneImageForType,
                                                     didTapEditButtonAction: { 
                                            didTapUploadOrEditPicture(item)
                                        }, didTapDeleteButtonAction: { id in
                                            viewModel.selectedScheduledItemToDeleteId = id
                                            self.deleteScheduledItemPopUp = true
                                        })
                                    }
                                }
                            } else {
                                createEmptyDataView()
                            }
                            Spacer()
//                            createBottomButtons()
                        }
                        .padding([.leading, .trailing, .bottom], InsetSpacing.md)
                    } //: Main VStack
                }
            }
            if deleteScheduledItemPopUp {
                createDeleteItemPopUp()
            }
        } //: ZStack
        .onAppear {
            viewModel.getScheduledItems()
        }
    }
    
    @ViewBuilder
    private func createEmptyDataView() -> some View {
        DataStatusResponseView(image: Image.guitarImage,
                               title: "scheduled.emptyList1.state".localized(lang),
                               titleStyle: TextStyle(font: .museo(.museoSansBold, size: 21),
                                                     color: .secondaryDarkest),
                               subtitle: "scheduled.emptyList2.state".localized(lang),
                               subtitleStyle: TextStyle(font: .lato(.latoRegular, size: 14),
                                                        color: .secondaryDarkest))
    }
    
    @ViewBuilder
    private func createErrorDataView() -> some View {
        DataStatusResponseView(image: Image.searchImage,
                               title: "scheduled.error1.state".localized(lang),
                               titleStyle: TextStyle(font: .museo(.museoSansBold, size: 21),
                                                     color: .secondaryDarkest),
                               subtitle: "scheduled.error2.state".localized(lang),
                               subtitleStyle: TextStyle(font: .lato(.latoRegular, size: 14),
                                                        color: .tertiaryDarkest,
                                                        underlined: true))
        .onTapGesture {
            viewModel.getScheduledItems()
        }
    }
    
    @ViewBuilder
    private func createBottomButtons() -> some View {
        SecondaryButtonView("scheduled.addMore.label".localized(lang),
                            height: HeightSize.lg) {
            didTapAddMoreItems(viewModel.policyId)
        }
        PrimaryButtonView("common.done.label".localized(lang),
                          height: HeightSize.lg) {
            viewModel.submitItems()
            self.dismiss()
        }
    }
    
    @ViewBuilder
    private func createDeleteItemPopUp() -> some View {
        PopUpActionView(popUpImage: Image.delete2Icon,
                        popUpDescription: "scheduled.delete.item.label".localized(lang),
                        mainActionLabel: "scheduled.delete.confirm.label".localized(lang),
                        secondaryActionLabel: "common.cancel.label".localized(lang),
                        mainActionBackground: .negativeDarkest) {
            self.deleteScheduledItemPopUp = false
        } confirmAction: {
            guard let id = viewModel.selectedScheduledItemToDeleteId else { return }
            viewModel.deleteScheduledItem(id: id)
        }
    }
}

