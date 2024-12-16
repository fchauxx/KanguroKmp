import SwiftUI

public struct AccordionButtonView: View {
    
    // MARK: - Wrapped Properties
    @State private var isOpen: Bool = false
    
    // MARK: - Stored Properties
    let title: String
    var icon: Image?
    var backgroundColor: Color
    var insideView: AnyView?
    
    // MARK: - Initializers
    public init(title: String,
                icon: Image? = nil,
                backgroundColor: Color = Color.neutralBackground,
                insideView: AnyView? = nil) {
        self.title = title
        self.icon = icon
        self.backgroundColor = backgroundColor
        self.insideView = insideView
    }
    
    // MARK: - Computed Properties
    var iconImage: Image {
        isOpen ? Image.arrowUpIcon : Image.arrowDownIcon
    }
    
    public var body: some View {
        VStack {
            HStack {
                HStack(spacing: StackSpacing.nano) {
                    if let icon {
                        icon
                            .resizable()
                            .frame(width: InsetSpacing.xs,
                                   height: InsetSpacing.xs)
                    }
                    Text(title).bodySecondaryDarkestBold()
                }
                Spacer()
                iconImage
                    .resizable()
                    .frame(width: InsetSpacing.xs,
                           height: InsetSpacing.xs)
                    .onTapGesture {
                        withAnimation {
                            isOpen.toggle()
                        }
                    }
            }
            .padding(InsetSpacing.xxs)
            
            if isOpen,
               let insideView {
                insideView
            }
        } //: VStack
        .background(backgroundColor)
        .cornerRadius(CornerRadius.sm)
        .overlay(RoundedRectangle(cornerRadius: CornerRadius.sm)
            .stroke(Color.primaryMedium, lineWidth: isOpen ? BorderWidth.thin : 0))
    }
}

// MARK: - Previews
struct AccordionButtonView_Previews: PreviewProvider {
    
    static var previews: some View {
        AccordionButtonView(title: "Documentation",
                            icon: Image.uploadIcon)
    }
}
