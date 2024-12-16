import SwiftUI
import KanguroDesignSystem

public struct PetFeedbackView: View {

    enum Rating: Int, CaseIterable {
         case one = 1
         case two
         case three
         case four
         case five
    }

    let sendButtonId: Int = 1

    // MARK: Property Wrappers
    @ObservedObject var viewModel: PetFeedbackViewModel
    @Environment(\.appLanguageValue) var language
    @State var value: String = ""
    @FocusState var textFieldIsFocused: Bool
    @State private var selectedRating: Rating? = .none
    @State private var showRequestErrorAlert: Bool = false

    public init(viewModel: PetFeedbackViewModel) {
        self.viewModel = viewModel
    }

    // MARK: Computed Properties
    var lang: String {
        language.rawValue
    }

    var feedbackImage: Image {
        switch selectedRating {
        case .one:
            return Image.feedback1
        case .two:
            return Image.feedback2
        case .three:
            return Image.feedback3
        case .four:
            return Image.feedback4
        case .five:
            return Image.feedback5
        default:
            return Image.feedback3
        }
    }

    var feedbackLabel: String {
        if selectedRating != nil {
          return "\("feedback.sendFeedback.button".localized(lang))"
        } else {
          return "\("feedback.skipFeedback.button".localized(lang))"
        }
    }

    public var body: some View {
        ScrollView {
            ScrollViewReader { proxy in
                ZStack {
                    VStack(spacing: InsetSpacing.xs) {
                        Text("\("feedback.title.label".localized(lang))")
                            .title3SecondaryDarkestBold()
                            .multilineTextAlignment(.center)
                            .padding(.top, InsetSpacing.tabBar)

                        feedbackImage
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .frame(height: 203)
                            .padding(.bottom, InsetSpacing.xs)

                        HStack {
                            ForEach(Rating.allCases, id: \.self) { rating in

                                let isSelected = Binding(
                                    get: { self.selectedRating == rating },
                                    set: { _ in self.selectedRating = rating }
                                )

                                CustomFeedbackRadioButton(
                                    isSelected: isSelected,
                                    size: 28,
                                    didSelectAction: { self.selectedRating = rating },
                                    bottomText: String(rating.rawValue)
                                )

                                if rating != .five {
                                    Spacer()
                                }
                            }
                        }
                        .frame(maxWidth: .infinity)
                        .padding(.horizontal, InsetSpacing.xxs)
                        .padding(.bottom, InsetSpacing.xxxs)

                        CustomFeedbackTextField(
                            placeholder: "\("feedback.placeholder.textfield".localized(lang))",
                            value: $value
                        )
                        .focused($textFieldIsFocused)
                        .background(textFieldIsFocused ? Color.primaryLight : Color.primaryMedium)
                        .onTapGesture {
                            textFieldIsFocused = true
                            DispatchQueue.main.asyncAfter(deadline: .now() + 0.35) {
                                withAnimation {
                                    proxy.scrollTo(sendButtonId, anchor: .top)
                                }
                            }
                        }


                        PrimaryButtonView(
                            feedbackLabel,
                            showIcon: false
                        ) {

                            if let selectedRating {
                                viewModel.sendFeedback(
                                    rate: selectedRating.rawValue,
                                    description: value
                                )
                            } else {
                                viewModel.onFinish()
                            }

                        }
                        .padding(.bottom, InsetSpacing.xs)
                        .id(sendButtonId)
                    }
                    .padding(.horizontal, InsetSpacing.xs)

                    if viewModel.state == .loading {
                        ProgressView()
                            .frame(maxWidth: .infinity, maxHeight: .infinity)
                            .background(Color.neutralLightest.opacity(0.4))
                            .scaleEffect(1.5, anchor: .center)
                    }
                }
            }
        }
        .background(Color.primaryMedium, ignoresSafeAreaEdges: .all)
        .onTapGesture {
            textFieldIsFocused = false
        }
        .onChange(of: viewModel.state) { newState in
            switch newState {
            case .requestSucceeded:
                viewModel.onFinish()
            case .requestFailed:
                showRequestErrorAlert = true
            default:
                showRequestErrorAlert = false
            }
        }
        .alert(isPresented: $showRequestErrorAlert) {
            Alert(
                title: Text(viewModel.requestError),
                primaryButton: .default(Text("scheduled.error2.state".localized(lang))) {
                    viewModel.sendFeedback(
                        rate: selectedRating?.rawValue ?? 3,
                        description: value
                    )
                },
                secondaryButton: .default(Text("common.cancel.label".localized(lang))) {
                    viewModel.onFinish()
                }
            )
        }
    }
}

#Preview {
    PetFeedbackView(viewModel: PetFeedbackViewModel(claimId: "", onFinish: {}))
}
