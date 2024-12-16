import SwiftUI

public struct SelectionButtonData: Hashable {
    
    let text: String
    let textStyle: TextStyle
    var isSelected: Bool
    var leadingIconColor: Color?
    
    public init(text: String,
                textStyle: TextStyle,
                isSelected: Bool,
                leadingIconColor: Color? = nil) {
        self.text = text
        self.textStyle = textStyle
        self.isSelected = isSelected
        self.leadingIconColor = leadingIconColor
    }
}

public struct HorizontalSelectionButtonsView: View {
    
    // MARK: - Stored Properties
    @State var data: [SelectionButtonData]
    
    // MARK: - Actions
    var didTapButton: StringClosure
    
    // MARK: - Initializers
    public init(data: [SelectionButtonData], 
                didTapButton: @escaping StringClosure) {
        self.data = data
        self.didTapButton = didTapButton
    }
    
    public var body: some View {
        HStack(spacing: InsetSpacing.nano) {
            ForEach(data, id: \.self) { buttonData in
                makeButton(data: buttonData)
            }
        }
    }
    
    // MARK: - View Builders
    @ViewBuilder
    private func makeButton(data: SelectionButtonData) -> some View {
        Button(action: {
            if let currentIndex = self.data.firstIndex(of: data) {
                withAnimation {
                    selectIndex(index: currentIndex)
                }
            }
        }, label: {
            HStack(spacing: InsetSpacing.quarck) {
                if let color = data.leadingIconColor,
                   color != Color.clear {
                    VStack {}
                        .frame(width: HeightSize.nano, height: HeightSize.nano)
                        .background(color)
                        .cornerRadius(CornerRadius.quarck)
                }
                
                Text(data.text)
                    .font(data.textStyle.font)
                    .foregroundStyle(data.textStyle.color)
            }
            .padding(InsetSpacing.nano)
            .frame(minWidth: 46, maxHeight: 28)
            .background(Color.white)
            .cornerRadius(CornerRadius.lg)
            .overlay(
                RoundedRectangle(cornerRadius: CornerRadius.lg)
                    .stroke(data.isSelected ? Color.secondaryLightest : .clear,
                            lineWidth: BorderWidth.thin)
            )
        })
        .padding(BorderWidth.thin)
    }
    
    // MARK: - Private Methods
    private func selectIndex(index: Int) {
        self.data[index].isSelected = true
        didTapButton(data[index].text)
        self.data.indices.forEach { indice in
            if indice != index {
                DispatchQueue.main.async {
                    self.data[indice].isSelected = false
                }
            }
        }
    }
}
