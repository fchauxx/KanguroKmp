import SwiftUI

public struct AirvetCardView: View {

    // MARK: - Dependencies
    @Environment(\.appLanguageValue) var language
    var lang: String {
        language.rawValue
    }

    public let onTapCard: SimpleClosure

    public init(onTapCard: @escaping SimpleClosure) {
        self.onTapCard = onTapCard
    }

    public var body: some View {
        Button(
            action: {
                onTapCard()
            },
            label: {
                makeButtonBody()
            }
        )
    }

    @ViewBuilder
    private func makeButtonBody() -> some View {
        VStack(alignment: .center, spacing: 0) {
            ZStack {
                Color.tertiaryLightest
                Image.airvetImageNoBackground
                    .resizable()
                    .scaledToFit()
                
                ComposeContentView()
            }
            .frame(height: 92)

            VStack(alignment: .leading, spacing: 0) {
                Spacer().frame(height: InsetSpacing.nano)

                Text("liveVet.card.title".localized(lang))
                    .fixedSize(horizontal: false, vertical: true)
                    .dynamicTypeSize(.xSmall)
                    .allowsTightening(true)
                    .multilineTextAlignment(.leading)
                    .headlinePrimaryDarkestBold()

                Spacer().frame(height: InsetSpacing.nano)

                Text("liveVet.card.subtitle".localized(lang))
                    .fixedSize(horizontal: false, vertical: true)
                    .multilineTextAlignment(.leading)
                    .captionSecondaryDarkRegular()

                Spacer()

                Text("liveVet.card.link".localized(lang))
                    .footnoteTertiaryExtraDarkBold()
                    .underline()
                    .multilineTextAlignment(.leading)
                    .padding(.bottom, InsetSpacing.quarck)

            }
            .padding(.horizontal, InsetSpacing.xxs)
        }
        .padding(.bottom, InsetSpacing.xxs)
        .frame(width: 160, height: 223)
        .background(Color.white)
        .lineLimit(2)
    }
}

// MARK: - Previews
struct AirvetCardView_Previews: PreviewProvider {
    static var previews: some View {
        ZStack {
            Color.neutralBackground
            AirvetCardView(onTapCard: {})
        }
    }
}
