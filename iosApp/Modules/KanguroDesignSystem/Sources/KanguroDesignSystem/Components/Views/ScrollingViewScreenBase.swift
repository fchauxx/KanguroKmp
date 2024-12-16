import SwiftUI

public struct ScrollingViewScreenBase: View {
    
    // MARK: - Wrapped Properties
    @State private var largeHeader = true
    
    // MARK: - Stored Properties
    private let contentView: AnyView
    private let headerImage: Image?
    private let headerHeight: CGFloat = 240
    private let compactHeaderHeight: CGFloat = 96
    private let scrollViewOffset: CGFloat = 152
    private let compactScrollViewOffset: CGFloat = 56
    
    // MARK: - Actions
    private var backAction: SimpleClosure
    
    // MARK: - Initializers
    public init(contentView: AnyView,
                headerImage: Image?,
                backAction: @escaping SimpleClosure) {
        self.contentView = contentView
        self.headerImage = headerImage
        self.backAction = backAction
    }
    
    var defaultImage: Image {
        headerImage ?? Image.statusSingleFamily
    }
    
    public var body: some View {
        ZStack(alignment: .top) {
            defaultImage
                .resizable()
                .aspectRatio(contentMode: .fill)
                .frame(maxHeight: largeHeader ? headerHeight : compactHeaderHeight)
                .frame(maxWidth: UIScreen.main.bounds.size.width)
                .scaleEffect(largeHeader ? 1.2 : 1.0)
                .edgesIgnoringSafeArea(.all)
            
            ScrollView(showsIndicators: false) {
                contentView
                    .padding(.top, InsetSpacing.xs)
                    .padding(.bottom, 160)
                    .frame(maxWidth: .infinity)
                    .background(
                        GeometryReader { geometryProxy -> Color in
                            DispatchQueue.main.async {
                                withAnimation(.easeIn(duration: 0.25)) {
                                    largeHeader = geometryProxy.frame(in: .named("scrollView")).minY >= 0
                                }
                            }
                            return Color.white
                        }
                    )
                    .roundedCorner(CornerRadius.md, corners: [.topLeft, .topRight])
            } //: ScrollView
            .roundedCorner(CornerRadius.md, corners: [.topLeft, .topRight])
            .coordinateSpace(name: "scrollView")
            .offset(y: largeHeader ? scrollViewOffset : compactScrollViewOffset)
            
            PresenterNavigationHeaderView(
                style: .backgrounded,
                backAction: backAction
            )
            .padding(.horizontal, InsetSpacing.xs)
            
        } //: ZStack
        .toolbar(.hidden, for: .navigationBar)
    }
}

// MARK: - Previews
struct ScrollingViewScreenBase_Previews: PreviewProvider {
    
    struct PreviewStruct: View {
        var body: some View {
            VStack {}
                .frame(width: 400, height: 1080)
                .background(Color.blue)
        }
    }
    
    static var previews: some View {
        ScrollingViewScreenBase(
            contentView: AnyView(PreviewStruct()),
            headerImage: Image.statusSingleFamily,
            backAction: {}
        )
        
        ScrollingViewScreenBase(
            contentView: AnyView(PreviewStruct()),
            headerImage: Image.renterFaqImage,
            backAction: {}
        )
    }
}
