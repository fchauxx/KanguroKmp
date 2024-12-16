import SwiftUI

public struct ItemSelectionCardViewData: Hashable, Identifiable {
    
    public var id = UUID()
    let iconName: String
    let title: String
    let toggleTitle: String
    let description: String
    var isActive: Bool
    var activeLabel: String?
    
    public init(iconName: String,
                title: String,
                toggleTitle: String,
                description: String,
                isActive: Bool,
                activeLabel: String? = nil) {
        self.iconName = iconName
        self.title = title
        self.toggleTitle = toggleTitle
        self.description = description
        self.isActive = isActive
        self.activeLabel = activeLabel
    }
}

public struct ItemSelectionCardView: View {
    
    // MARK: - Wrapped Properties
    @Binding var data: ItemSelectionCardViewData
    
    // MARK: - Stored Properties
    private var lineWidth: CGFloat = 1
    
    // MARK: - Computed Properties
    private var icon: Image {
        Image(data.iconName, bundle: .module)
    }
    
    public init(data: Binding<ItemSelectionCardViewData>) {
        self._data = data
    }
    
    public var body: some View {
        VStack {
            VStack(alignment: .leading, spacing: InsetSpacing.xxs) {
                makeTopHorizontalView()
                
                Text(data.description)
                    .font(.lato(.latoRegular, size: 13))
                    .foregroundColor(.secondaryLight)
                
                if let activeLabel = data.activeLabel,
                   data.isActive {
                    Text(activeLabel)
                        .font(.lato(.latoRegularItalic, size: 13))
                        .foregroundColor(.secondaryLight)
                }
            }
            .padding(InsetSpacing.xxs)
        }
        .background(data.isActive ? Color.tertiaryLightest.opacity(0.1) : Color.white)
        .overlay(
            RoundedRectangle(cornerRadius: CornerRadius.sm)
                .stroke(data.isActive ? Color.tertiaryDarkest : Color.neutralLightest,
                        lineWidth: lineWidth)
        )
        .padding(lineWidth)
    }
    
    @ViewBuilder
    private func makeTopHorizontalView() -> some View {
        HStack(spacing: InsetSpacing.xxxs) {
            icon
                .resizable()
                .foregroundStyle(Color.secondaryMedium)
                .frame(width: IconSize.md_2, height: IconSize.md_2)
            
            Text(data.title)
                .bodySecondaryDarkBold()
            
            VStack(alignment: .trailing, spacing: InsetSpacing.nano) {
                Text(data.toggleTitle)
                    .font(.lato(.latoBold, size: 11))
                    .foregroundStyle(data.isActive ? Color.tertiaryDarkest : Color.neutralLightest)
                
                Toggle("", isOn: $data.isActive)
                    .tint(Color.tertiaryDarkest)
            }
        }
    }
}

// MARK: - Previews
struct AdditionalCoverageSelectionView_Previews: PreviewProvider {
    
    static var previews: some View {
        ItemSelectionCardView(
            data: .constant(ItemSelectionCardViewData(
                iconName: "ic-replacement-cost",
                title: "Replacement cost",
                toggleTitle: "$ 3,99/mo",
                description: "Should you need to use your insurance, we'll cover the genuine costs of repairing or replacing items.",
                isActive: true,
                activeLabel: "This additional coverage is active")))
        .padding()
    }
}
