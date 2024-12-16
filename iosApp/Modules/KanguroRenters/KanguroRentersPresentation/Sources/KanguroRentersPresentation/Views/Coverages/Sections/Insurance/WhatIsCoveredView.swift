import SwiftUI
import KanguroDesignSystem
import KanguroSharedDomain

public struct WhatIsCoveredView: View {
    
    // MARK: - Property Wrappers
    @Environment(\.appLanguageValue) var language
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    
    // MARK: - Actions
    var closeAction: SimpleClosure
    
    public init(closeAction: @escaping SimpleClosure) {
        self.closeAction = closeAction
    }
    
    public var body: some View {
        ScrollView(.vertical) {
            PresenterNavigationHeaderView(closeAction: closeAction)
            
            VStack {
                Text("coverageDetails.insurance.whatsCovered.card".localized(lang))
                    .titleSecondaryDarkestBold()
                    .padding(.bottom, InsetSpacing.xxs)
                
                Text("renters.policy.insurance.label".localized(lang))
                    .bodySecondaryDarkMedium()
                    .textCase(.uppercase)
                    .padding(.bottom, 32)
                
                HighlightedText(text: "whatIsCovered.body.label".localized(lang),
                                highlightedText: "whatIsCovered.body.label.highlighted".localized(lang),
                                baseStyle: TextStyle(font: .lato(.latoRegular, size: 16), color: .secondaryDarkest),
                                highlightedStyle: TextStyle(font: .lato(.latoBold, size: 16), color: .secondaryDarkest))
                .multilineTextAlignment(.center)
                .padding(.bottom, 32)
                
                VerticalList(verticalItems: [
                    VerticalListItem(content: AnyView(
                        VerticalItem(title: "whatIsCovered.vertical.list.item.label.theft".localized(lang), icon: Image.theftIcon, style: .primary))),
                    
                    VerticalListItem(content: AnyView(
                        VerticalItem(title: "whatIsCovered.vertical.list.item.label.fire".localized(lang), icon: Image.fileIcon, style: .primary))),
                    
                    VerticalListItem(content: AnyView(
                        VerticalItem(title: "whatIsCovered.vertical.list.item.label.smoke".localized(lang), icon: Image.smokeIcon, style: .primary))),
                    
                    VerticalListItem(content: AnyView(
                        VerticalItem(title: "whatIsCovered.vertical.list.item.label.hail".localized(lang), icon: Image.hailIcon, style: .primary))),
                    
                    VerticalListItem(content: AnyView(
                        VerticalItem(title: "whatIsCovered.vertical.list.item.label.light".localized(lang), icon: Image.lightningIcon, style: .primary))),
                    
                    VerticalListItem(content: AnyView(
                        VerticalItem(title: "whatIsCovered.vertical.list.item.label.wind".localized(lang), icon: Image.windstormIcon, style: .primary))),
                    
                    VerticalListItem(content: AnyView(
                        VerticalItem(title: "whatIsCovered.vertical.list.item.label.vandalism".localized(lang), icon: Image.vandalismIcon, style: .primary))),
                    
                    VerticalListItem(content: AnyView(
                        VerticalItem(title: "whatIsCovered.vertical.list.item.label.explosion".localized(lang), icon: Image.explosionIcon, style: .primary))),
                ])
            }
            .padding(.horizontal, InsetSpacing.md)
        }
    }
}

struct WhatIsCoveredView_Previews: PreviewProvider {
    static var previews: some View {
        WhatIsCoveredView(closeAction: {})
    }
}
