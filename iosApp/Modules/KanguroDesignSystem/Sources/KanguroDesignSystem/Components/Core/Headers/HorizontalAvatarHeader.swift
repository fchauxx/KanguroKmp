import SwiftUI

public struct HorizontalAvatarHeader: View {
    
    // MARK: - Stored Properties
    var avatarImage: Image
    var title: String
    var subtitle: String?
    var titleStyle: TextStyle?
    var subtitleStyle: TextStyle?
    var bgColor: Color?
    
    // MARK: - Initializers
    public init(
        avatar: Image = Image.javierRentersIcon,
        title: String,
        subtitle: String? = nil,
        titleStyle: TextStyle? = TextStyle(font: .museo(.museoSansBold, size: 21),
                                           color: Color.secondaryDarkest),
        subtitleStyle: TextStyle? = TextStyle(font: .raleway(.ralewayBold, size: 12),
                                              color: Color.secondaryDarkest),
        bgColor: Color? = .clear
    ) {
        self.avatarImage = avatar
        self.title = title
        self.subtitle = subtitle
        self.titleStyle = titleStyle
        self.subtitleStyle = subtitleStyle
        self.bgColor = bgColor
    }
    
    public var body: some View {
        VStack {
            HStack(spacing: InsetSpacing.nano) {
                avatarImage
                    .resizable()
                    .frame(width: IconSize.lg, height: IconSize.lg)
                
                VStack(alignment: .leading, spacing: InsetSpacing.quarck) {
                    Text(title)
                        .font(titleStyle?.font)
                        .foregroundColor(titleStyle?.color)
                        .minimumScaleFactor(0.3)
                        .padding(.top, InsetSpacing.nano)
                    if let subtitle {
                        Text(subtitle)
                            .font(subtitleStyle?.font)
                            .foregroundColor(subtitleStyle?.color)
                            .multilineTextAlignment(.center)
                            .foregroundColor(Color.secondaryDarkest)
                            .minimumScaleFactor(0.3)
                    }
                }
                .frame(minHeight: IconSize.lg, alignment: .topLeading)
                
                Spacer()
            } //: HStack
            .padding(.leading, InsetSpacing.xs)
            .padding(.vertical, InsetSpacing.xxs)
        } //: VStack
        .accessibilityElement(children: .combine)
        .frame(maxWidth: .infinity, minHeight: IconSize.lg)
        .accessibilityAddTraits([.isHeader])
        .background(bgColor)
    }
}

struct AvatarHeader_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            HorizontalAvatarHeader(title: "Here's the list of your scheduled items")
            HorizontalAvatarHeader(title: "hello, I'm Javier!",
                         subtitle: "YOUR I.A. PERSONAL ASSISTENT",
                         bgColor: .secondaryLightest)
        }
    }
}
