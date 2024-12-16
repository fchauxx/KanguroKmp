

import SwiftUI
import KanguroDesignSystem

public enum VideoFileComponentType {
    case regular
    case error
}

public struct VideoFileComponent: View {
    
    // MARK: - Stored Properties
    private let type: VideoFileComponentType
    private let label: String
    private var icon: Image
    
    // MARK: - Actions
    var removeVideoAction: SimpleClosure
    
    // MARK: - Initializers
    public init(
        type: VideoFileComponentType,
        label: String,
        icon: Image,
        removeVideoAction: @escaping SimpleClosure
    ) {
        self.type = type
        self.label = label
        self.icon = icon
        self.removeVideoAction = removeVideoAction
    }
    
    public var body: some View {
        HStack(alignment: .center, spacing: InlineSpacing.nano) {
            icon
                .accessibilityHidden(true)
                .frame(width: IconSize.sm, height: IconSize.sm)
                .foregroundColor(Color.secondaryDarkest)

            Spacer()
            
            Text(label)
                .foregroundStyle(Color.neutralMedium)
                .bodySecondaryDarkestRegular()
                .padding(InsetSpacing.nano)
                .frame(alignment: .topLeading)
            
            
            Spacer()
            
            Image.closeIcon
                .accessibilityHidden(true)
                .frame(width: IconSize.sm, height: IconSize.sm)
                .foregroundColor(Color.neutralMedium)
                .onTapGesture {
                    removeVideoAction()
                }
        }
        .padding(.horizontal, InsetSpacing.nano)
        .frame(height: HeightSize.md)
        .frame(minWidth: 300)
        .background(Color.neutralBackground)
        .cornerRadius(CornerRadius.sm)
        .accessibilityValue(label)
    }
}

// MARK: - Preview
struct VideoFileComponentType_Previews: PreviewProvider {
    static var previews: some View {
        VideoFileComponent(
            type: .regular,
            label: "ASD3IEQD.MP4",
            icon: Image.cameraIcon, removeVideoAction: {}
        )
        .previewDevice(.init(rawValue: "iPhone 14 Pro Max"))
        .previewDisplayName("iPhone 14 Pro Max")
    }
}
