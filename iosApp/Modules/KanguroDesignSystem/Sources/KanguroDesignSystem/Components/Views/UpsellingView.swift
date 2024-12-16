import SwiftUI
import KanguroSharedDomain

public struct UpsellingView: View {
    
    // MARK: - Stored Properties
    public var type: PolicyType
    public var didTapTellMeMore: SimpleClosure = {}
    @Environment(\.appLanguageValue) var language
    
    var lang: String {
        language.rawValue
    }
    
    // MARK: - Initializer
    public init(type: PolicyType,
                didTapTellMeMore: @escaping SimpleClosure) {
        self.type = type
        self.didTapTellMeMore = didTapTellMeMore
    }
    
    public var body: some View {
        ZStack(alignment: .bottom) {
            ScrollView(showsIndicators: false) {
                VStack(spacing: StackSpacing.xs) {
                    Text("upselling.\(type.rawValue).title.label".localized(lang))
                        .titleSecondaryDarkestBold()
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .padding(.leading)
                    Image.rentersUpsellingHeader
                        .resizable()
                        .frame(width: 272, height: 194)
                        .scaledToFit()

                    VStack(spacing: StackSpacing.xxs) {
                        Text("upselling.\(type.rawValue).question.label".localized(lang))
                            .title3SecondaryDarkestBold()
                        VStack(spacing: StackSpacing.semiquarck) {
                            Text("upselling.\(type.rawValue).description1.label".localized(lang))
                                .bodySecondaryDarkRegular()
                            Text("upselling.\(type.rawValue).description1Bolded.label".localized(lang))
                                .bodySecondaryDarkBold()
                        }
                        Text("upselling.\(type.rawValue).description2.label".localized(lang))
                            .bodySecondaryDarkRegular()
                            .multilineTextAlignment(.center)
                        Text("product.upselling.getAQuote.label".localized(lang))
                            .title3SecondaryDarkestBold()
                    } //: Texts VStack

                } //: Main VStack
                .padding(.horizontal, InsetSpacing.xs)
            }
            .padding(.bottom, ActionButtonSize.md)

            PrimaryButtonView("product.upselling.tellMeMore.label".localized(lang), background: .tertiaryExtraDark, showIcon: true) {
                didTapTellMeMore()
            }
            .padding(.horizontal, InsetSpacing.xs)
        }
        .padding(.bottom, ActionButtonSize.lg)
    }
}

struct UpsellingView_Previews: PreviewProvider {
    static var previews: some View {
        UpsellingView(type: .renters, didTapTellMeMore: {})
    }
}
