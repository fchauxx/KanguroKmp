import SwiftUI
import KanguroRentersDomain
import KanguroSharedDomain
import KanguroDesignSystem

public struct RentersInsuranceSectionView: View {
    
    // MARK: - Property Wrappers
    @Environment(\.appLanguageValue) var language

    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }

    var policyStatusHighlighted: String {
        switch policyStatus {
        case .CANCELED:
            return "coverageDetails.insurance.policyStatus.highlighted.cancelled".localized(lang)
        case .ACTIVE:
            return "coverageDetails.insurance.policyStatus.highlighted.active".localized(lang)
        case .PENDING:
            return "coverageDetails.insurance.policyStatus.highlighted.pending".localized(lang)
        case .TERMINATED:
            return "coverageDetails.insurance.policyStatus.highlighted.terminated".localized(lang)
        }
    }

    var policyStatusText: String {
        return "coverageDetails.insurance.policyStatus".localized(lang)
    }

    // MARK: - Stored Properties
    public let renewDate: Date
    public let startDate: Date
    public let policyStatus: PolicyStatus
    public let planSummary: PlanSummary
    public let documents: [PolicyDocumentData]
    
    // MARK: - Actions
    var didTapEditPolicyDetailsAction: SimpleClosure
    var didTapWhatisCoveredAction: SimpleClosure
    var didTapDocumentAction: AnyClosure = { _ in }

    public init(renewDate: Date,
                startDate: Date,
                policyStatus: PolicyStatus,
                planSummary: PlanSummary,
                documents: [PolicyDocumentData],
                didTapEditPolicyDetailsAction: @escaping SimpleClosure,
                didTapWhatisCoveredAction: @escaping SimpleClosure,
                didTapDocumentAction: @escaping AnyClosure = { _ in }
    ) {
        self.renewDate = renewDate
        self.startDate = startDate
        self.policyStatus = policyStatus
        self.planSummary = planSummary
        self.documents = documents
        self.didTapEditPolicyDetailsAction = didTapEditPolicyDetailsAction
        self.didTapWhatisCoveredAction = didTapWhatisCoveredAction
        self.didTapDocumentAction = didTapDocumentAction
    }
    
    public var body: some View {
        ZStack {
            SectionInformationView(
                headerView: SectionHeaderView(
                    icon: Image.houseSectionIcon,
                    title: "coverageDetails.insurance.title".localized(lang),
                    subtitle: "coverageDetails.insurance.title".localized(lang).uppercased(),
                    renewDate: renewDate,
                    startDate: startDate,
                    formattedDate: "MM/dd/yyyy"
                    ),
                contentViewList: [
                    SectionContentView(
                        content: AnyView(
                            VStack(alignment: .leading, spacing: StackSpacing.xxs) {
                                HighlightedText(
                                    text: policyStatusText.replacingOccurrences(of: "policyStatus", with: policyStatusHighlighted.lowercased()),
                                    highlightedText: policyStatusHighlighted.lowercased(),
                                    baseStyle: TextStyle(font: .museo(.museoSansThin, size: 24), color: .secondaryDark),
                                    highlightedStyle: TextStyle(font: .museo(.museoSansBold, size: 24), color: .tertiaryDarkest))
                                .fixedSize(horizontal: false,
                                           vertical: true)
                                .padding(.trailing, InsetSpacing.lg)
                            }
                        )),
                    SectionContentView(
                        content: AnyView(ActionCardListView(cardButtons: [
                            CardButton(
                                content: AnyView(AccordionButtonView(
                                    title: "coverageDetails.insurance.planSummary.card".localized(lang),
                                    insideView: AnyView(
                                        KeyValueListView(type: .primary, data: getValidPlanSummaryData())
                                            .padding([.bottom, .horizontal], InsetSpacing.xxs))))),
                            CardButton(
                                content: AnyView(ActionCardButton(title: "coverageDetails.insurance.whatsCovered.card".localized(lang),
                                                                  icon: Image.coveredIcon,
                                                                  style: .primary,
                                                                  didTapAction: didTapWhatisCoveredAction))),
                            CardButton(
                                content:  AnyView(AccordionButtonView(title: "coverageDetails.insurance.documentation.card".localized(lang),
                                                                      icon: Image.fileIcon, insideView: AnyView(
                                                                        KeyValueListView(type: .primary, documentsData: documents, didTapDocumentAction:didTapDocumentAction, isDivided: true)
                                                                        .padding([.bottom, .horizontal], InsetSpacing.xxs))))),
                            CardButton(
                                content: AnyView(ActionCardButton(title: "coverageDetails.insurance.editPolicy.card".localized(lang),
                                                                  icon: Image.editIcon,
                                                                  style: .primary,
                                                                  didTapAction: didTapEditPolicyDetailsAction)))
                        ])))
                ]
            )
        }
    }
    
    private func getValidPlanSummaryData() -> [KeyValueLabelData] {
        var data: [KeyValueLabelData] = []
        if let liability = planSummary.liability {
            data.append(KeyValueLabelData(key: "coverageDetails.planSummary.liability".localized(lang),
                                          value: liability.value.getCurrencyFormatted()))
        }
        if let deductible = planSummary.deductible {
            data.append(KeyValueLabelData(key: "coverageDetails.planSummary.deductible".localized(lang),
                                          value: deductible.value.getCurrencyFormatted()))
        }
        if let personalProperty = planSummary.personalProperty {
            data.append(KeyValueLabelData(key: "coverageDetails.planSummary.personalProperty".localized(lang),
                                          value: personalProperty.value.getCurrencyFormatted()))
        }
        if let lossOfUse = planSummary.lossOfUse {
            data.append(KeyValueLabelData(key: "coverageDetails.planSummary.lossOfUse".localized(lang),
                                          value: lossOfUse.value.getCurrencyFormatted()))
        }
        return data
    }
    
    private func getDocumentsData() -> [KeyValueLabelData] {
        var data: [KeyValueLabelData] = []
        for document in documents {
            print("document \(document)")
               data.append(KeyValueLabelData(
                key: document.name ?? "",
                   value: ""
               ))
           }
        return data
    }
}

struct RentersInsuranceSectionView_Previews: PreviewProvider {
    static var previews: some View {
        RentersInsuranceSectionView(
            renewDate: Date(),
            startDate: Date(),
            policyStatus: .ACTIVE,
            planSummary: PlanSummary(
                liability: PlanSummaryItemData(
                    id: 1,
                    value: 200
                ),
                deductible: PlanSummaryItemData(
                    id: 1,
                    value: 300
                ),
                personalProperty: PlanSummaryItemData(
                    id: 1,
                    value: 300
                ),
                lossOfUse: nil
            ),
            documents: [PolicyDocumentData()],
            didTapEditPolicyDetailsAction: { },
            didTapWhatisCoveredAction: { }
        )
    }
}
