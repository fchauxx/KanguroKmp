import SwiftUI

public struct SectionHeaderView: View {
    
    // MARK: - Wrapped Properties
    @Environment(\.appLanguageValue) var language

    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }

    // MARK: - Stored Properties
    let icon: Image
    let title: String
    var subtitle: String?
    var renewDate: Date?
    var startDate: Date?
    var formattedDate: String?
    
    // MARK: - Initializers
    public init(icon: Image,
                title: String,
                subtitle: String? = nil,
                renewDate: Date? = nil,
                startDate: Date? = nil,
                formattedDate: String? = nil) {
        self.icon = icon
        self.title = title
        self.subtitle = subtitle
        self.renewDate = renewDate
        self.startDate = startDate
        self.formattedDate = formattedDate
    }
    
    public var body: some View {
        VStack(alignment: .leading, spacing: StackSpacing.nano) {
            VStack(alignment: .leading, spacing: StackSpacing.quarck) {
                HStack {
                    icon
                        .resizable()
                        .frame(
                            width: InsetSpacing.xs,
                            height: InsetSpacing.xs
                        )
                    
                    Spacer()
                    
                    if let startDate, let renewDate {
                        policyPeriodLabel(
                            formattedDate: formattedDate,
                            startDate: startDate,
                            renewDate: renewDate
                        )
                    }
                    
                } //: HStack
                
                HStack(alignment: .top) {
                    Text(title).bodySecondaryDarkestBold()
                    
                    Spacer()
                    
                    if let renewDate {
                        renewDateLabel(
                            formattedDate: formattedDate,
                            renewDate: renewDate
                        )
                    }
                } //: HStack
                
            }
            
            if let subtitle {
                Text(subtitle)
                    .textCase(.uppercase)
                    .footnoteSecondaryBlack()
                    .padding(.bottom, InsetSpacing.nano)
            }
            
            Divider()
        } //: Main VStack
    }
    
    @ViewBuilder
    private func renewDateLabel(formattedDate: String?, renewDate: Date) -> some View {
        if let formattedDate {
            Text(
                "sectionHeader.label".localized(lang) + renewDate.getFormatted(format:formattedDate)
            )
            .footnoteNeutralMediumRegular()
        } else {
            Text(
                "sectionHeader.label".localized(lang) + renewDate.accessibleText
            )
            .footnoteNeutralMediumRegular()
        }
    }
    
    @ViewBuilder
    private func policyPeriodLabel(formattedDate: String?, startDate: Date, renewDate: Date) -> some View {
        if let formattedDate {
            Text(
                "\("sectionHeader.label.period".localized(lang))\(startDate.getFormatted(format:formattedDate)) - \(renewDate.getFormatted(format:formattedDate))"
            )
            .footnoteNeutralMediumRegular()
        } else {
            Text(
                "\("sectionHeader.label.period".localized(lang))\(startDate.accessibleText) - \(renewDate.accessibleText)"
            )
            .footnoteNeutralMediumRegular()
        }
    }

}

// MARK: - Previews
struct SectionHeaderView_Previews: PreviewProvider {
    static var previews: some View {
        SectionHeaderView(
            icon: Image.cameraIcon,
            title: "Renters Insurance",
            subtitle: "Renters insurance",
            renewDate: Date().advanced(by: 365 * 24 * 60 * 60),
            startDate: Date(),
            formattedDate: "MM/dd/yyy"
        )
    }
}
