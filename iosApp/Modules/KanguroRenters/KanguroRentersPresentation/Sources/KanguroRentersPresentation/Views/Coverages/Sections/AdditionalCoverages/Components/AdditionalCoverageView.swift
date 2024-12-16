import SwiftUI
import KanguroDesignSystem

public struct AdditionalCoverageViewData {
    
    let icon: Image
    let title: String
    let coverageLimit: Double
    let deductibleLimit: Double
    let footerText: String?
    let popupType: AdditionalCoveragePopUpData

    public init(icon: Image,
                title: String,
                coverageLimit: Double,
                deductibleLimit: Double,
                footerText: String? = nil,
                popupType: AdditionalCoveragePopUpData) {
        self.icon = icon
        self.title = title
        self.coverageLimit = coverageLimit
        self.deductibleLimit = deductibleLimit
        self.footerText = footerText
        self.popupType = popupType
    }
}

public struct AdditionalCoverageView: View, Identifiable {
    
    // MARK: - Property Wrappers
    @Binding var popUpData: AdditionalCoveragePopUpData?
    @Environment(\.appLanguageValue) var language
    
    // MARK: - Stored Properties
    public let id = UUID()
    var data: AdditionalCoverageViewData
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    
    public init(data: AdditionalCoverageViewData,
                popUpData: Binding<AdditionalCoveragePopUpData?>
    ) {
        self.data = data
        self._popUpData = popUpData
    }
    
    public var body: some View {
        ZStack {
            VStack(alignment: .leading, spacing: StackSpacing.nano) {
                HStack(spacing: InlineSpacing.nano) {
                    data.icon
                        .resizable()
                        .frame(width: IconSize.md, height: IconSize.md)
                        .foregroundColor(.secondaryLight)
                    Text(data.title)
                        .bodySecondaryDarkBold()
                    Spacer()
                    Button {
                        popUpData = data.popupType
                    } label: {
                        Image.informationIcon
                            .resizable()
                            .frame(width: IconSize.xs, height: IconSize.xs)
                    }
                }
                
                KeyValueLabelView(type: .primary,
                                  data: KeyValueLabelData(key: "coverageDetails.additionalCoverage.coverageLimit".localized(lang),
                                                          value: data.coverageLimit.getCurrencyFormatted()))
                KeyValueLabelView(type: .primary,
                                  data: KeyValueLabelData(key: "coverageDetails.additionalCoverage.deductibleLimit".localized(lang),
                                                          value: data.deductibleLimit.getCurrencyFormatted()))
                
                if let footerText = data.footerText {
                    Text(footerText)
                        .paragraphSecondaryLightRegularItalic()
                }
            }
            .padding(.vertical, InsetSpacing.xxs)
        }
    }
}

struct AdditionalCoverageView_Previews: PreviewProvider {
    static var previews: some View {
        AdditionalCoverageView(
            data: AdditionalCoverageViewData(
                icon: Image.cameraIcon,
                title: "Water Sewer Backup",
                coverageLimit: 2500,
                deductibleLimit: 250,
                footerText: "renters.policy.additional.hasCoverage.label".localized("en"),
                popupType: .fraudProtection),
            popUpData: .constant(nil)
        )
        .previewDevice(.init(rawValue: "iPhone 8"))
        .previewDisplayName("iPhone 8")
    }
}
