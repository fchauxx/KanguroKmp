import SwiftUI
import KanguroDesignSystem
import KanguroRentersDomain

public struct RentersFAQView: View {
    
    // MARK: - Wrapped Properties
    @ObservedObject var viewModel: RentersFAQViewModel
    @Environment(\.appLanguageValue) var language
    
    // MARK: - Computed Properties
    var backAction: SimpleClosure = {}
    var lang: String {
        language.rawValue
    }
    
    public init(viewModel: RentersFAQViewModel, backAction: @escaping SimpleClosure) {
        self.viewModel = viewModel
        self.backAction = backAction
    }
    
    public var body: some View {
        ZStack {
            if !viewModel.requestError.isEmpty {
                createErrorDataView()
            } else if !viewModel.isLoading {
                ScrollingViewScreenBase(
                    contentView: AnyView(createContentView()),
                    headerImage: Image.renterFaqImage,
                    backAction: backAction
                )
            } else {
                LoadingView()
            }
        }
        .onAppear {
            viewModel.getInformerDataList()
        }
    }
    
}

// MARK: - Content Views
public extension RentersFAQView {
    
    @ViewBuilder
    func createContentView() -> some View {
        VStack(alignment: .leading, spacing: StackSpacing.xs) {
            VStack(alignment: .leading, spacing: 0) {
                HighlightedText(text: "faq.title.label".localized(lang),
                                highlightedText: "faq.title.label".localized(lang),
                                baseStyle: TextStyle(font: .museo(.museoSansBold, size: 21),
                                                     color: .secondaryDarkest),
                                highlightedStyle: TextStyle(font: .museo(.museoSansBold, size: 21),
                                                            color: .primaryDarkest))
                
                Text("faq.subtitle.label".localized(lang)).bodySecondaryDarkestBold()
                
            }
            ScrollView(.vertical) {
                ForEach(viewModel.informerDataList.indices, id: \.self) { index in
                    AccordionButtonView(title: $viewModel.informerDataList[index].value.wrappedValue ?? "error",
                                        insideView: AnyView(createCardContent(label: viewModel.informerDataList[index].description)))
                }
            }
        }
        .padding(.horizontal, InsetSpacing.xs)
    }
    @ViewBuilder
    private func createCardContent(label: String?) -> some View {
        Text(label ?? "error")
            .foregroundColor(.gray)
            .padding([.horizontal, .bottom], InsetSpacing.xxs)
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
            viewModel.getInformerDataList()
        }
    }
}
