import SwiftUI

public struct VerticalAvatarHeader: View {
    
    // MARK: - Stored Properties
    var avatarImage: Image
    var title: String
    var subtitle: String?
    var titleStyle: TextStyle?
    var subtitleStyle: TextStyle?
    var bgColor: Color?
    
    // MARK: - Initializers
    public init(
        avatar: Image = Image.javierPinkRentersIcon,
        title: String,
        subtitle: String? = nil,
        titleStyle: TextStyle? = TextStyle(font: .museo(.museoSansBold, size: 24),
                                           color: Color.secondaryDarkest),
        subtitleStyle: TextStyle? = TextStyle(font: .raleway(.ralewayBold, size: 12),
                                              color: Color.primaryDarkest),
        bgColor: Color? = .neutralBackground
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
            VStack {
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
                } //: Texts VStack
            } //: VStack
            .padding(.vertical, InsetSpacing.xxs)
        } //: Main VStack
        .accessibilityElement(children: .combine)
        .frame(maxWidth: .infinity, minHeight: IconSize.lg)
        .accessibilityAddTraits([.isHeader])
        .background(bgColor)
    }
}

struct VerticalAvatarHeader_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            VerticalAvatarHeader(title: "hello, I'm Javier!",
                                 subtitle: "YOUR I.A. PERSONAL ASSISTENT")
        }
    }
}
