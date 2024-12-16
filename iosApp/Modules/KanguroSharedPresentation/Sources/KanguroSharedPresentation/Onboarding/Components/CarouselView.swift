import SwiftUI
import KanguroSharedDomain
import KanguroDesignSystem

public struct CarouselView: View {
    
    // MARK: - Stored Properties
    let items: [CarouselItem]
    let imageHeight: CGFloat
    
    // MARK: - Actions
    var buttonAction: SimpleClosure
    
    public init(items: [CarouselItem],
                buttonAction: @escaping SimpleClosure,
                imageHeight: CGFloat) {
        self.items = items
        self.imageHeight = imageHeight
        self.buttonAction = buttonAction
    }
    
    public var body: some View {
        VStack {
            TabView {
                ForEach(items, id: \.self) { item in
                    makeItemView(item)
                }
            } //: TAB
            .tabViewStyle(PageTabViewStyle())
            .onAppear {
                setupTabViewIndicatorAppearance()
            }
        } //: Main VStack
    }
    
    @ViewBuilder
    private func makeItemView(_ item: CarouselItem) -> some View {
        VStack {
            item.image
                .resizable()
                .aspectRatio(contentMode: .fill)
                .frame(height: imageHeight)
            
            VStack(alignment: .leading) {
                if let boldedText = item.boldedText {
                    Text(boldedText)
                        .font(.lato(.latoBold, size: 24))
                        .foregroundColor(.secondaryDarkest)
                        .minimumScaleFactor(0.6)
                }
                if let regularText = item.regularText {
                    Text(regularText)
                        .font(.lato(.latoBold, size: 21))
                        .foregroundColor(.secondaryDark)
                        .minimumScaleFactor(0.6)
                }
                
                if let buttonTitle = item.buttonTitle,
                   let buttonImage = item.buttonImage {
                    Button {
                        buttonAction()
                    } label: {
                        HStack {
                            Text(buttonTitle)
                                .font(.lato(.latoBold, size: 14))
                                .underline()
                                .foregroundColor(.primaryDark)
                            buttonImage
                                .resizable()
                                .frame(width: 14, height: 14)
                        }
                    }
                }
            } //: Texts VStack
            .padding(32)
            
            Spacer()
        }
    }
    
    private func setupTabViewIndicatorAppearance() {
        UIPageControl.appearance().currentPageIndicatorTintColor = UIColor(.primaryDarkest)
        UIPageControl.appearance().pageIndicatorTintColor = UIColor.black.withAlphaComponent(0.2)
    }
}

// MARK: - Previews
struct CarouselTestView_Previews: PreviewProvider {
    
    static var previews: some View {
        CarouselView(items: [CarouselItem(imageName: "welcome-slide-1",
                                          boldedText: "call us kanguro...",
                                          regularText: "modernizing the insurance experience for Spanish speaking Americans",
                                          buttonTitle: "Cambiar a espanol",
                                          buttonImageName: "ic-switch"),
                             CarouselItem(imageName: "welcome-slide-2",
                                          boldedText: "Our main focus is bringing value to our life with coverages designed for your every day")], buttonAction: {}, imageHeight: 300)
    }
}
