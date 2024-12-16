import SwiftUI
import KanguroDesignSystem
import KanguroChatbotDomain

public struct ClaimsChatView<Content: View>: View {
    
    // MARK: - Wrapped Properties
    @ObservedObject var viewModel: ClaimsChatViewModel
    @Environment(\.appLanguageValue) var language
    
    // MARK: - Stored Properties
    public var content: () -> Content
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    
    // MARK: - Actions
    var backAction: SimpleClosure
    
    // MARK: - Initializers
    public init(
        viewModel: ClaimsChatViewModel,
        backAction: @escaping SimpleClosure,
        content: @escaping () -> Content
    ) {
        self.viewModel = viewModel
        self.backAction = backAction
        self.content = content
    }
    
    public var body: some View {
        ZStack(alignment: .top) {
            Color.white
            VStack(spacing: 0) {
                Rectangle()
                    .foregroundStyle(Color.neutralBackground)
                    .frame(maxWidth: .infinity, maxHeight: 75)
                
                NavigationBackButtonView(title: "rentersClaim.title".localized(lang),
                                         action: backAction)
                
                VStack(spacing: InsetSpacing.xs) {
                    VerticalAvatarHeader(title: "onboarding.chatbot.title".localized(lang),
                                         subtitle: "onboarding.chatbot.subtitle".localized(lang))
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
