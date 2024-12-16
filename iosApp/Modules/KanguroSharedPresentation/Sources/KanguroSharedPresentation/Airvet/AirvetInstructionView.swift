
import SwiftUI
import KanguroDesignSystem
import KanguroUserDomain

public struct AirvetInstructionView: View {

    // MARK: - Dependencies
    @ObservedObject var viewModel: AirvetInstructionViewModel
    @Environment(\.dismiss) var dismiss
    @Environment(\.appLanguageValue) var language
    var lang: String {
        language.rawValue
    }

    @State var copyText: String = ""

    // MARK: - Stored Properties
    var downloadAppAction : ((AirvetUserDetails) -> (Void))
    var onCopyIdAction: StringClosure

    // MARK: - Initializer
    public init(
        viewModel: AirvetInstructionViewModel,
        downloadAppAction: @escaping ((AirvetUserDetails) -> (Void)),
        onCopyIdAction: @escaping StringClosure
    ) {
        self.viewModel = viewModel
        self.downloadAppAction = downloadAppAction
        self.onCopyIdAction = onCopyIdAction
    }

    public var body: some View {
        ZStack {
            if !viewModel.requestError.isEmpty {
                createErrorDataView()
            } else if viewModel.isLoading {
                LoadingView()
            } else {
                ZStack(alignment: .bottom) {
                    mainContent(
                        onCopyIdAction: onCopyIdAction
                    )

                    PrimaryButtonView(
                        "liveVet.downloadApp".localized(lang),
                        height: HeightSize.lg
                    ) {
                        downloadAppAction(self.viewModel.airvetUserDetails)
                    }
                    .padding(.horizontal, InsetSpacing.xxs)
                    .padding(.bottom, InsetSpacing.xxxs)
                }
            }
        }.onAppear {
            viewModel.getUser()
        }
    }

    @ViewBuilder
    private func mainContent(
        onCopyIdAction: @escaping StringClosure
    ) -> some View {
        VStack(spacing: 0) {
            PresenterNavigationHeaderView(
                closeAction: { self.dismiss() }
            )

            title()

            ScrollView {
                mainContent(
                    onCopyId: onCopyIdAction
                )

                Spacer()

            }
            .padding(.top, InsetSpacing.xxxs)
            .padding(.bottom, 90)
        }
    }

    @ViewBuilder
    private func title() -> some View {
        HStack {
            VStack(alignment: .leading) {
                Text("liveVet.title".localized(lang))
                    .title3SecondaryDarkestBold()
                Text("liveVet.subtitle".localized(lang))
                    .bodySecondaryDarkBold()
            }
            Spacer()
        }
        .padding(.horizontal,InsetSpacing.md)
    }

    @ViewBuilder
    private func mainContent(
        onCopyId: @escaping StringClosure
    ) -> some View {
        VStack {
            Image.airvetImage

            Text(description())
                .padding(.top, StackSpacing.nano)
                .padding(.bottom, StackSpacing.xxxs)
        }
        .padding(.horizontal,InsetSpacing.md)
    }


    @ViewBuilder
    private func createErrorDataView() -> some View {
        DataStatusResponseView(image: Image.errorImage,
                               title: "scheduled.error1.state".localized(lang),
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
            viewModel.getUser()
        }
    }

}

// MARK: - String Constructor Methods
private extension AirvetInstructionView {
    
    func commonDescriptionStyle(with text: String, font: Font, color: Color) -> AttributedString {
        var attributedString = AttributedString(text)
        attributedString.foregroundColor = color
        attributedString.font = font
        return attributedString
    }
    
    func description() -> AttributedString {
        let color = Color.secondaryDark
        let regularFont = Font.lato(.latoRegular, size: 16)
        let boldFont = Font.lato(.latoBold, size: 16)
        
        let prependedText = commonDescriptionStyle(with: "liveVet.description.hours".localized(lang), font: boldFont, color: color)
        
        let title = commonDescriptionStyle(with: "liveVet.policy".localized(lang), font: regularFont, color: color)
        
        let description = commonDescriptionStyle(with: "liveVet.description".localized(lang), font: regularFont, color: color)
        let descriptionTwo = commonDescriptionStyle(with: "liveVet.description.2".localized(lang), font: regularFont, color: color)
        let descriptionThree = commonDescriptionStyle(with: "liveVet.description.3".localized(lang), font: regularFont, color: color)
        let descriptionFour = commonDescriptionStyle(with: "liveVet.description.4".localized(lang), font: regularFont, color: color)
        
        var combinedDescription = AttributedString()
        combinedDescription.append(title)
        combinedDescription.append(AttributedString("\n\n"))
        combinedDescription.append(prependedText)
        combinedDescription.append(AttributedString(" "))
        combinedDescription.append(description)
        combinedDescription.append(AttributedString("\n\n"))
        combinedDescription.append(descriptionTwo)
        combinedDescription.append(AttributedString("\n\n"))
        combinedDescription.append(descriptionThree)
        combinedDescription.append(AttributedString("\n\n"))
        combinedDescription.append(descriptionFour)
        return combinedDescription
    }

    func stepOneInstruction() -> AttributedString {
        var attributedString = AttributedString("liveVet.stepOne.instruction".localized(lang))

        attributedString.foregroundColor = Color.secondaryDark
        attributedString.font = .lato(.latoRegular, size: 16)

        if let download = attributedString.range(of: "liveVet.stepOne.instruction.download".localized(lang)) {
            attributedString[download].font = .lato(.latoBold, size: 16)
        }

        if let here = attributedString.range(of: "liveVet.stepOne.instruction.here".localized(lang)) {
            attributedString[here].underlineStyle = .single
            attributedString[here].foregroundColor = Color.tertiaryDarkest
        }

        return attributedString
    }

    func stepTwoInstruction() -> AttributedString {
        let localCopy = if copyText.isEmpty {
            "liveVet.stepTwo.instruction.copy".localized(lang)
        } else {
            copyText
        }

        var attributedString = AttributedString("liveVet.stepTwo.instruction".localized(lang))

        attributedString.foregroundColor = Color.secondaryDark
        attributedString.font = .lato(.latoRegular, size: 16)

        if let download = attributedString.range(of: "liveVet.stepTwo.instruction.signup".localized(lang)) {
            attributedString[download].font = .lato(.latoBold, size: 16)
        }

        if let here = attributedString.range(of: "\(localCopy)") {
            if localCopy == "liveVet.stepTwo.instruction.copied".localized(lang) {
                attributedString[here].foregroundColor = Color.positiveDark
            } else {
                attributedString[here].foregroundColor = Color.tertiaryDarkest
            }
        }

        return attributedString
    }

    func stepThreeInstruction() -> AttributedString {
        var attributedString = AttributedString("liveVet.stepThree.instruction".localized(lang))

        attributedString.foregroundColor = Color.secondaryDark
        attributedString.font = .lato(.latoRegular, size: 16)

        if let download = attributedString.range(of: "liveVet.stepThree.instruction.talk".localized(lang)) {
            attributedString[download].font = .lato(.latoBold, size: 16)
        }

        return attributedString
    }
}

struct SwiftUIView_Previews: PreviewProvider {
    static var previews: some View {
        AirvetInstructionView(
            viewModel: AirvetInstructionViewModel(),
            downloadAppAction: {_ in },
            onCopyIdAction: {_ in }
        )
    }
}
