import SwiftUI

public enum LoadingSpinnerSize {
    case small
    case medium
    case normal

    var size: CGFloat {
        switch self {

        case .small:
            return 50
        case .medium:
            return 80
        case .normal:
            return 150
        }
    }
}

public struct LoadingView: View {
    
    // MARK: - Property Wrappers
    @State private var isRotating = 0.0
    
    // MARK: - Stored Properties
    var backgroundColor: Color
    var spinningSize: LoadingSpinnerSize
    var spinningColor: Color?
    var renderingMode: Image.TemplateRenderingMode?

    // MARK: - Initializer
    public init(backgroundColor: Color = .white,
                spinningSize: LoadingSpinnerSize = .normal,
                spinningColor: Color? = nil,
                renderingMode: Image.TemplateRenderingMode? = nil) {
        self.backgroundColor = backgroundColor
        self.spinningSize = spinningSize
        self.spinningColor = spinningColor
    }
    
    public var body: some View {
        ZStack {
            backgroundColor
                .edgesIgnoringSafeArea(.all)
            loadingImage(color: spinningColor)
        }
        .frame(maxWidth: .infinity, 
               maxHeight: .infinity)
        .onAppear {
            withAnimation(.linear(duration: 2)
                .repeatForever(autoreverses: false)) {
                    isRotating = 360.0
                }
        }
    }

    @ViewBuilder
    func loadingImage(color: Color?) -> some View {
        Image.loadingImage
            .resizable()
            .renderingMode(renderingMode ?? .template)
            .foregroundColor(color ?? Color.neutralBackground)
            .rotationEffect(.degrees(isRotating))
            .frame(width: spinningSize.size,
                   height: spinningSize.size)

    }
}

struct LoadingView_Previews: PreviewProvider {
    static var previews: some View {
        LoadingView()
        LoadingView(
            backgroundColor: Color.neutralBackground,
            spinningColor: Color.white
        )
    }
}
