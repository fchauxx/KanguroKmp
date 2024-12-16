import SwiftUI

public struct BannerCardView: View {
    
    let data: BannerCardViewData
    
    public init(data: BannerCardViewData) {
        self.data = data
    }
    
    public var body: some View {
        ZStack {
            HStack {
                VStack(alignment: .leading, spacing: InsetSpacing.xxs) {
                    HStack {
                        if data.style == .horizontalRight {
                            createTexts()
                            Spacer()
                        }
                        
                        data.leadingImage
                            .resizable()
                            .frame(width: data.style.iconSize.width,
                                   height: data.style.iconSize.height)
                            .padding(.top, data.style == .vertical ? InsetSpacing.xxs : 0)
                        
                        if data.style == .vertical {
                            createTexts()
                            Spacer()
                        }
                        
                        if data.style == .horizontalLeft {
                            Spacer()
                            createTexts()
                            Spacer()
                        }
                    } //: HStack
                    .padding(InsetSpacing.xxs)
                    
                    if data.style == .vertical {
                        makeSubtitle()
                            .padding([.leading, .bottom], InsetSpacing.xxs)
                    }
                } //: VStack
                if data.style == .vertical {
                    Spacer()
                }
            }
        } //: ZStack
        .frame(maxWidth: .infinity)
        .background(data.backgroundColor)
        .cornerRadius(data.style.cornerRadius)
        .onTapGesture {
            data.tapAction()
        }
    }
    
    @ViewBuilder
    private func makeSubtitle() -> some View {
        Text(data.subtitle)
            .font(data.style.subtitleStyle.font)
            .foregroundStyle(data.style.subtitleStyle.color)
    }
    
    @ViewBuilder
    private func createTexts() -> some View {
        VStack(alignment: .leading, spacing: InsetSpacing.xxxs) {
            Text(data.title)
                .font(data.style.titleStyle.font)
                .foregroundStyle(data.style.titleStyle.color)
            
            if data.style == .horizontalLeft || data.style == .horizontalRight {
                makeSubtitle()
            }
        }
    }
}


struct BannerCardView_Previews: PreviewProvider {
    
    static var previews: some View {
        ZStack {
            Color.gray
            VStack {
                BannerCardView(data: BannerCardViewData(style: .horizontalRight,
                                                        backgroundColor: .primaryLight,
                                                        leadingImage: Image.petUpsellingBackgrounded,
                                                        title: "Do you have\n a pet?",
                                                        subtitle: "Save money with our\nKanguro Pet Insurance!",
                                                        tapAction: {}))
                .padding()
                
                BannerCardView(data: BannerCardViewData(style: .horizontalLeft,
                                                        backgroundColor: .tertiaryDark,
                                                        leadingImage: Image.donationImage,
                                                        title: "Let’s help make the world better!",
                                                        subtitle: "Join Kanguro in donating for great causes.",
                                                        tapAction: {}))
                .padding()
                
                BannerCardView(data: BannerCardViewData(style: .vertical,
                                                        backgroundColor: .white,
                                                        leadingImage: Image.javierPinkRentersIcon,
                                                        title: "Talk to Javier",
                                                        subtitle: "He is our Kanguro Specialist, available 24/7. \nEasily file your claims and get paid fast!",
                                                        tapAction: {}))
                .padding()
            }
        }
    }
}
