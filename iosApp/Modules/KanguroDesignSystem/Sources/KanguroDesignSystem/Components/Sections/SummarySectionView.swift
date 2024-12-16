import SwiftUI

public struct SummarySectionView<Content: View>: View {
    
    // MARK: - Stored Properties
    let title: String
    let subtitle: String?
    let content: Content
    
    // MARK: - Initializers
    public init(title: String,
                subtitle: String?,
                content: Content) {
        self.title = title
        self.subtitle = subtitle
        self.content = content
    }
    
    public var body: some View {
        VStack(alignment: .leading, spacing: InsetSpacing.xxs) {
            Text(title)
                .paragraphTertiaryExtraDarkBold()
            
            if let subtitle {
                Text(subtitle)
                    .footnoteNeutralMediumBold()
            }
            
            content
        }
    }
}

#Preview {
    SummarySectionView(
        title: "Claim Summary",
        subtitle: "Incident occured on June 24",
        content: VStack {
            KeyValueListView(type: .primary, data: [
                KeyValueLabelData(key: "Claim Type",
                                  value: "Injury Property"),
                KeyValueLabelData(key: "Policy",
                                  value: "Renters insurance"),
                KeyValueLabelData(key: "Policy Start date",
                                  value: "August 25, 2021"),
                KeyValueLabelData(key: "Deductible",
                                  value: 250.getCurrencyFormatted()),
                KeyValueLabelData(key: "Contact phone",
                                  value: "555 555 555")
            ], isDivided: true)
        })
    .padding()
}
