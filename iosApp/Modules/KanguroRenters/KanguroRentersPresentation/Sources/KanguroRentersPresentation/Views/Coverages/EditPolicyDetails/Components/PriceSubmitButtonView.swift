import SwiftUI
import KanguroDesignSystem

public struct PriceSubmitButtonView: View {
    
    // MARK: - Property Wrappers
    @Environment(\.appLanguageValue) var language

    // MARK: - Stored Properties
    let buttonPrice: Double
    let bottomLabelPrice: Double
    let isLoading: Bool?
    let action: SimpleClosure

    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }

    // MARK: - Initializers
    public init(buttonPrice: Double,
                bottomLabelPrice: Double,
                isLoading: Bool? = false,
                action: @escaping SimpleClosure) {
        self.buttonPrice = buttonPrice
        self.bottomLabelPrice = bottomLabelPrice
        self.isLoading = isLoading
        self.action = action
    }
    
    public var body: some View {
        VStack(spacing: InsetSpacing.xxxs) {
            PrimaryButtonView("editPolicyDetails.submit.button"
                .localized(lang)
                .replacingOccurrences(of: "value", with: "\(buttonPrice)"),
                              height: 64,
                              isLoading: isLoading ?? false,
                              action)
            Text("editPolicyDetails.totalPrice.label"
                .localized(lang)
                .replacingOccurrences(of: "value", with: "\(bottomLabelPrice)"))
            .captionSecondaryDarkRegular()
        }
    }
}

// MARK: - Previews
struct PriceSubmitButtonView_Preview: PreviewProvider {
    
    static var previews: some View {
        PriceSubmitButtonView(buttonPrice: 40.2,
                              bottomLabelPrice: 98.4,
                              action: {})
        .padding()
    }
}
