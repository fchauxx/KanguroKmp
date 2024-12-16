import SwiftUI

public struct PrimaryButtonView: View {
    
    var buttonLabel: String
    var height: CGFloat
    var background: Color
    var cornerRadius: CGFloat
    var showIcon: Bool
    var isDisabled: Bool
    var isAtPopUp: Bool
    var isLoading: Bool
    var action: SimpleClosure
    
    public init(_ buttonLabel: String,
                height: CGFloat = HeightSize.md,
                background: Color = .secondaryDarkest,
                cornerRadius: CGFloat = CornerRadius.sm,
                showIcon: Bool = false,
                isDisabled: Bool = false,
                isAtPopUp: Bool = false,
                isLoading: Bool = false,
                _ action: @escaping SimpleClosure) {
        self.buttonLabel = buttonLabel
        self.height = height
        self.background = background
        self.cornerRadius = cornerRadius
        self.showIcon = showIcon
        self.isDisabled = isDisabled
        self.isAtPopUp = isAtPopUp
        self.isLoading = isLoading
        self.action = action
    }
    
    public var body: some View {
        
        ZStack {
            Button {
                action()
            } label: {
                HStack(spacing: InlineSpacing.nano) {
                    Spacer()
                    if isLoading {
                        LoadingView(backgroundColor:  Color.secondaryLightest,
                                    spinningSize: .small,
                                    spinningColor: .white,
                                    renderingMode: .template)
                    } else {
                        createBodyContent()
                    }
                    Spacer()
                }
            }
        }
        .frame(maxWidth: .infinity)
        .frame(height: height)
        .background(isDisabled ? Color.secondaryLightest : background)
        .cornerRadius(cornerRadius)
        .disabled(isDisabled)
    }
    
    @ViewBuilder
    private func createBodyContent() -> some View {
        if isAtPopUp {
            Text(buttonLabel)
                .calloutWhiteExtraBold()
        } else {
            Text(buttonLabel)
                .bodyWhiteExtraBold()
        }
        if showIcon {
            Image.starDefaultIcon
                .resizable()
                .frame(width: IconSize.sm, height: IconSize.sm)
        }
    }
}

struct ButtonView_Previews: PreviewProvider {
    static var previews: some View {
        PrimaryButtonView("Tell me more!", showIcon: true) {}
        PrimaryButtonView("Tell me more!", showIcon: true, isDisabled: true) {}
        PrimaryButtonView("Tell me more!",
                          showIcon: true,
                          isDisabled: true,
                          isLoading: true) {}
    }
}
