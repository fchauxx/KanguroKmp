import SwiftUI
import KanguroDesignSystem
import KanguroSharedDomain

public struct PetUpsellingView: View {
    
    // MARK: - Stored Properties
    public var didTapTellMeMore: SimpleClosure = {}
    @Environment(\.appLanguageValue) var language

    var lang: String {
        language.rawValue
    }
    
    // MARK: - Initializer
    public init(didTapTellMeMore: @escaping SimpleClosure) {
        self.didTapTellMeMore = didTapTellMeMore
    }
    
    public var body: some View {
        ScrollView(showsIndicators: false) {
            VStack {
                Text("pet.upselling.title.label".localized(lang))
                    .titleSecondaryDarkestBold()
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.leading)
                VStack {
                    Image.petUpsellingHeader
                        .resizable()
                        .frame(width: 248, height: 194)
                        .scaledToFit()
                        .padding(.vertical, InsetSpacing.lg)
                    Text("pet.upselling.question.label".localized(lang))
                        .title3SecondaryDarkestBold()
                    Text("pet.upselling.description.label".localized(lang))
                        .bodySecondaryDarkRegular()
                    Text("pet.upselling.description1.label".localized(lang))
                        .bodySecondaryDarkBold()
                    Text("pet.upselling.description2.label".localized(lang))
                        .bodySecondaryDarkRegular()
                        .multilineTextAlignment(.center)
                    Text("product.upselling.getAQuote.label".localized(lang))
                        .title3SecondaryDarkestBold()
                }
                .padding([.horizontal], InsetSpacing.xs)
                PrimaryButtonView("product.upselling.tellMeMore.label".localized(lang), background: .tertiaryExtraDark, showIcon: true) {
                    didTapTellMeMore()
                }
                .padding(.horizontal, InsetSpacing.xs)
            }
        }
    }
}

struct PetUpsellingView_Previews: PreviewProvider {
    static var previews: some View {
        PetUpsellingView(didTapTellMeMore: {})
    }
}
