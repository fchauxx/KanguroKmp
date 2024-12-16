import SwiftUI

public struct PickerButtonView: View {
    
    // MARK: - Stored Properties
    var label: String
    var isDisabled: Bool
    var isAtPopUp: Bool
    var isLoading: Bool
    var isSelected: Bool
    var tagName: String?
    var action: SimpleClosure
    
    public init(_ buttonLabel: String,
                isDisabled: Bool = false,
                isAtPopUp: Bool = false,
                isLoading: Bool = false,
                isSelected: Bool = false,
                tagName: String? = nil,
                _ action: @escaping SimpleClosure) {
        self.label = buttonLabel
        self.isDisabled = isDisabled
        self.isAtPopUp = isAtPopUp
        self.isLoading = isLoading
        self.isSelected = isSelected
        self.tagName = tagName
        self.action = action
    }
    
    public var body: some View {
        VStack(spacing: 0) {
            if let tagName {
                Group {
                    Text(tagName)
                        .calloutWhiteExtraBold()
                        .padding(.horizontal, InlineSpacing.xxs)
                        .padding(.vertical, InlineSpacing.nano)
                }
                .frame(minWidth: 120)
                .background(Color.tertiaryExtraDark)
                .cornerRadius(CornerRadius.sm, corners: [.topLeft, .topRight])
            }
            ZStack {
                Button {
                    action()
                } label: {
                    HStack(spacing: InlineSpacing.nano) {
                        Spacer()
                        if isLoading {
                            ProgressView()
                        } else {
                            createBodyContent(isSelected: isSelected)
                        }
                        Spacer()
                    }
                    .padding(.vertical, InlineSpacing.xxs)
                    .padding(.horizontal, InlineSpacing.xxxs)
                }
                if isDisabled {
                    Color.secondaryLightest
                        .opacity(OpacityLevel.medium)
                }
            }
            .background(isSelected ? Color.secondaryDarkest : .clear)
            .cornerRadius(CornerRadius.sm)
            .overlay( /// apply a rounded border
                RoundedRectangle(cornerRadius: CornerRadius.sm)
                    .stroke(isSelected ? .clear : .gray, lineWidth: 1)
            )
            .disabled(isDisabled)
        }
    }
    
    @ViewBuilder
    private func createBodyContent(isSelected: Bool) -> some View {
        Group {
            if isAtPopUp {
                Text(label)
                    .calloutWhiteExtraBold()
            } else {
                if isSelected {
                    Text(label)
                        .bodyWhiteExtraBold()
                } else {
                    Text(label)
                        .bodySecondaryDarkestExtraBold()
                }
            }
        }
    }
}

struct PickerButtonVieww_Previews: PreviewProvider {
    static var previews: some View {
        PickerButtonView("$1,000,000", isSelected: true, tagName: "Most Popular") {}
    }
}
