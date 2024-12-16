import SwiftUI

public struct DataStatusResponseView: View {
    
    // MARK: - Stored Properties
    public var image: Image
    public var title: String?
    public var titleStyle: TextStyle?
    public var subtitle: String?
    public var subtitleStyle: TextStyle?
    
    public init(image: Image,
                title: String? = nil,
                titleStyle: TextStyle? = nil,
                subtitle: String? = nil,
                subtitleStyle: TextStyle? = nil) {
        self.image = image
        self.title = title
        self.titleStyle = titleStyle
        self.subtitle = subtitle
        self.subtitleStyle = subtitleStyle
    }
    
    public var body: some View {
        VStack(spacing: InsetSpacing.xs) {
            image
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(maxWidth: 220, maxHeight: 220)
            
            if let title {
                VStack(spacing: InsetSpacing.nano) {
                    Text(title)
                        .font(titleStyle?.font)
                        .multilineTextAlignment(.center)
                        .foregroundColor(titleStyle?.color)
                        .underline(titleStyle?.underlined ?? false)
                    Text(subtitle ?? "")
                        .font(subtitleStyle?.font)
                        .multilineTextAlignment(.center)
                        .foregroundColor(subtitleStyle?.color)
                        .underline(subtitleStyle?.underlined ?? false)
                }
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .padding(InsetSpacing.xxs)
    }
}

struct DataStatusResponseView_Previews: PreviewProvider {
    
    static var previews: some View {
        DataStatusResponseView(image: Image.guitarImage,
                               title: "You don't have any scheduled items yet",
                               titleStyle: TextStyle(font: .museo(.museoSansBold, size: 21),
                                                     color: .secondaryDarkest),
                               subtitle: "Keep your prized belongings safe adding them to this list",
                               subtitleStyle: TextStyle(font: .lato(.latoRegular, size: 14),
                                                        color: .secondaryDarkest))
    }
}
