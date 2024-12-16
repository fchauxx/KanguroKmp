import SwiftUI

public struct HorizontalListCardView: View {
    
    let data: HorizontalListCardViewData
    
    public init(data: HorizontalListCardViewData) {
        self.data = data
    }
    
    public var body: some View {
        Button(action: {
            data.tapAction()
        }, label: {
            makeButtonBody()
        })
    }
    
    @ViewBuilder
    private func makeButtonBody() -> some View {
        HStack {
            VStack(alignment: .leading, spacing: StackSpacing.quarck) {
                data.icon
                    .renderingMode(.template)
                    .resizable()
                    .frame(width: IconSize.lg, height: IconSize.lg)
                    .foregroundColor(.primaryDarkest)
                
                Text(data.title)
                    .headlinePrimaryDarkestBold()
                
                Text(data.title)
                    .captionSecondaryDarkRegular()
                
                HStack(spacing: 2) {
                    Circle()
                        .frame(width: HeightSize.nano, height: HeightSize.nano)
                        .foregroundStyle(data.status ? Color.positiveDark : Color.negativeDark)
                    Text(data.statusText)
                        .footnoteNeutralMediumRegular()
                }
                
                Text(data.linkText)
                    .paragraphTertiaryExtraDarkBold()
                    .underline()
                    .padding(.top, InsetSpacing.xxxs)
            }
            .padding([.top, .leading, .trailing], InsetSpacing.xxs)
            .padding(.bottom, InsetSpacing.xs)
            
            Spacer()
        }
        .frame(width: 156, height: 184)
        .background(Color.white)
        .cornerRadius(CornerRadius.md)
    }
}

// MARK: - Previews
struct HorizontalListCardView_Previews: PreviewProvider {
    
    static var previews: some View {
        ZStack {
            Color.neutralBackground
            HorizontalListCardView(data: HorizontalListCardViewData(icon: Image.statusStudent,
                                                                    title: "Home",
                                                                    subtitle: "Tampa, FL",
                                                                    statusText: "Active",
                                                                    status: true,
                                                                    linkText: "see details",
                                                                    tapAction: {}))
        }
    }
}
