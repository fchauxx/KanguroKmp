import SwiftUI
import KanguroSharedDomain
import KanguroDesignSystem

public struct OnboardingView: View {
    
    // MARK: - Property Wrappers
    @ObservedObject var viewModel: OnboardingViewModel
    
    // MARK: - Computed Properties
    var lang: String {
        viewModel.publishedLanguage.rawValue
    }
    
    // MARK: - Actions
    var signInAction: SimpleClosure
    var getAQuoteAction: LanguageClosure
    
    var screenHeight: CGFloat {
        UIScreen.main.bounds.size.height
    }
    
    public init(viewModel: OnboardingViewModel,
                signInAction: @escaping SimpleClosure,
                getAQuoteAction: @escaping LanguageClosure) {
        self.viewModel = viewModel
        self.signInAction = signInAction
        self.getAQuoteAction = getAQuoteAction
    }
    
    public var body: some View {
        ZStack(alignment: .top) {
            VStack {
                CarouselView(items: [
                    CarouselItem(imageName: "welcome-slide-1",
                                 boldedText: "onboarding.boldedTitle.label".localized(lang),
                                 regularText: "onboarding.description.label".localized(lang),
                                 buttonTitle: "onboarding.switchLanguage.button".localized(lang),
                                 buttonImageName: "ic-switch"),
                    CarouselItem(imageName: "welcome-slide-2",
                                 boldedText: "onboarding.description2.label".localized(lang))],
                             buttonAction: {
                    self.viewModel.switchLanguage()
                }, imageHeight: screenHeight * 0.45)
                
                PrimaryButtonView("onboarding.signIn.button".localized(lang),
                                  height: HeightSize.lg,
                                  showIcon: true) {
                    signInAction()
                }.frame(maxWidth: 160).padding(.bottom, InsetSpacing.md)
                
                VStack(spacing: InsetSpacing.nano) {
                    Text("onboarding.dontHaveAccount.label".localized(lang))
                        .captionNeutralDarkRegular()
                    
                    Button {
                        getAQuoteAction(viewModel.publishedLanguage)
                    } label: {
                        HStack {
                            Text("onboarding.getQuote.button".localized(lang))
                                .calloutSecondaryDarkestUnderlinedBold()
                        }
                    }
                }.padding(.bottom, InsetSpacing.md)
            } //: Main VStack
        } //: Main ZStack
        .ignoresSafeArea(.keyboard)
    }
}

// MARK: - Previews
struct OnboardingView_Previews: PreviewProvider {
    
    static var previews: some View {
        OnboardingView(viewModel: OnboardingViewModel(), signInAction: {}, getAQuoteAction: {_ in})
    }
}
