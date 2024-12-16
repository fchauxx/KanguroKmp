import SwiftUI

public struct CheckboxView: View {
    
    // MARK: - Stored Properties
    @State private var isSelected: Bool = false
    let size: CGFloat
    let borderColor: Color
    let selectedCircleColor: Color
    
    var didSelectAction: SimpleClosure
    var didDiselectAction: SimpleClosure
    
    // MARK: - Initializers
    public init(isSelected: Bool = false,
                size: CGFloat,
                borderColor: Color = Color.secondaryDark,
                selectedCircleColor: Color = Color.tertiaryDarkest,
                didSelectAction: @escaping SimpleClosure,
                didDiselectAction: @escaping SimpleClosure) {
        self.isSelected = isSelected
        self.size = size
        self.borderColor = borderColor
        self.selectedCircleColor = selectedCircleColor
        self.didSelectAction = didSelectAction
        self.didDiselectAction = didDiselectAction
    }
    
    public var body: some View {
        Button {
            isSelected.toggle()
            isSelected ? didSelectAction() : didDiselectAction()
        } label: {
            ZStack {
                Circle()
                    .foregroundColor(Color.clear)
                if isSelected {
                    Circle()
                        .foregroundColor(selectedCircleColor)
                        .padding(InsetSpacing.quarck)
                }
            }
        }
        .frame(width: size, height: size)
        .overlay(RoundedRectangle(cornerRadius: InsetSpacing.quarck)
            .stroke(borderColor, lineWidth: 3))
    }
}

#Preview {
    VStack {
        CheckboxView(isSelected: true, 
                     size: 40,
                     didSelectAction: {}, didDiselectAction: {})
    }
}
