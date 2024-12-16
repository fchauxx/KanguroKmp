import SwiftUI
import KanguroRentersDomain
import KanguroDesignSystem

struct AdditionalPartiesSection: View {
    
    // MARK: - Property Wrappers
    @Environment(\.appLanguageValue) var language

    // MARK: - Stored Properties
    public var parties: [AdditionalPartie]

    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }

    // MARK: - Actions
    var didTapEditAdditionalPartiesAction: SimpleClosure

    public init(parties: [AdditionalPartie],
                didTapEditAdditionalPartiesAction: @escaping SimpleClosure) {
        self.parties = parties
        self.didTapEditAdditionalPartiesAction = didTapEditAdditionalPartiesAction
    }
    
    var body: some View {
        ZStack {
            SectionInformationView(
                headerView: SectionHeaderView(icon: Image.additionalPartiesSectionIcon,
                                              title: "coverageDetails.additionalParties.title.section".localized(lang)),
                contentViewList: [
                    SectionContentView(content:AnyView(
                        VStack(alignment: .leading, spacing: 16) {
                            ForEach(parties) { element in
                                VStack(alignment: .leading) {
                                    if let name = element.fullName {
                                        Text(name)
                                            .captionSecondaryDarkBold()
                                    }
                                    if let type = element.type {
                                        Text(type.rawValue)
                                            .captionTertiaryDarkBold()
                                    }
                                }
                            }
                        }
                    )),
                    SectionContentView(
                       content: AnyView(ActionCardButton(title: "renters.policy.additionalParties.edit.label".localized(lang),
                                                         icon: Image.coveredIcon,
                                                         style: .primary, 
                                                         didTapAction: {
                                                             didTapEditAdditionalPartiesAction()
                                                         }))
                    )
                ]
            )
        }
    }
}

struct AdditionalPartiesSection_Previews: PreviewProvider {
    static var previews: some View {
        AdditionalPartiesSection(parties: [AdditionalPartie(id: "1",
                                                            type: .Spouse,
                                                            fullName: "Benjamin Thompson"),
                                           AdditionalPartie(id: "2",
                                                            type: .Child,
                                                            fullName: "Emily Walker Thompson"),
                                           AdditionalPartie(id: "3",
                                                            type: .Child,
                                                            fullName: "Jonathan Walker Thompson"),
                                           AdditionalPartie(id: "4",
                                                            type: .Roommate,
                                                            fullName: "Oliviar Roberts")],
                                 didTapEditAdditionalPartiesAction: {})
    }
}
