import SwiftUI
import KanguroDesignSystem
import KanguroChatbotDomain

public struct OnboardingChatView<Content: View>: View {
    
    // MARK: - Wrapped Properties
    @ObservedObject var viewModel: OnboardingChatViewModel
    @Environment(\.appLanguageValue) var language
    
    // MARK: - Stored Properties
    public var content: () -> Content
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    
    // MARK: - Initializers
    public init(
        viewModel: OnboardingChatViewModel,
        content: @escaping () -> Content
    ) {
        self.viewModel = viewModel
        self.content = content
    }
    
    public var body: some View {
        ZStack(alignment: .top) {
            Color.white
            VStack(spacing: 0) {
                Rectangle()
                    .foregroundStyle(Color.secondaryLightest)
                    .frame(maxWidth: .infinity, maxHeight: 50)
                
                VStack(spacing: InsetSpacing.xs) {
                    HorizontalAvatarHeader(title: "onboarding.chatbot.title".localized(lang),
                                 subtitle: "onboarding.chatbot.subtitle".localized(lang),
                                 bgColor: .secondaryLightest)
                    content()
                } //: VStack
            }
        } //: ZStack
        .ignoresSafeArea(edges: [.top])
        .onAppear {
            hideNavigationTabBar()
        }
        .onDisappear {
            showNavigationTabBar()
        }
    }
}
