import SwiftUI
import KanguroSharedDomain
import KanguroDesignSystem
import KanguroRentersDomain

struct RenterPoliciesView: View {
    
    // MARK: - Stored Properties
    var renterPolicies: [RenterPolicy]
    
    // MARK: - Wrapped Properties
    @State private var selectedStatus: Status = .none
    var language: Language
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    var rentersInsuranceDataList: [HorizontalListCardViewData] {
        var list: [HorizontalListCardViewData] = []
        for policy in renterPolicies {
            guard let dwellingType = policy.dwellingType,
                  let policyStatus = policy.status else { return [] }
            if selectedStatus.policyStatus.contains(policyStatus) || selectedStatus == .none {
                list.append(HorizontalListCardViewData(icon: dwellingType.icon,
                                                       title: getDwellingTypeText(type: dwellingType),
                                                       subtitle: policy.address?.state ?? "",
                                                       statusText: policy.status?.title ?? "",
                                                       status: policy.status == .ACTIVE,
                                                       linkText: "dashboard.seeDetails.label".localized(lang),
                                                       tapAction: {
                    self.didTapRenterPolicyCard(policy)
                }))
            }
        }
        return list
    }
    var selectedButtonsData: [SelectionButtonData] {
        var list: [SelectionButtonData] = []
        Status.allCases.forEach { status in
            if status != .terminated {
                list.append(
                    SelectionButtonData(
                        text: status.title,
                        textStyle: KanguroDesignSystem.TextStyle(
                            font: .lato(.latoRegular, size: 12),
                            color: .secondaryDarkest),
                        isSelected: status == selectedStatus,
                        leadingIconColor: getSelectedButtonsDataColor(UIColor: status.color)))
            }
        }
        return list
    }
    var shouldShowStatusSelection: Bool {
        let containsActive = renterPolicies.map { $0.status }.contains(where: { $0 == .ACTIVE })
        let containsInactive = renterPolicies.map { $0.status }.contains(where: { $0 != .ACTIVE })
        
        return containsActive && containsInactive
    }
    
    // MARK: - Private Methods
    private func getSelectedButtonsDataColor(UIColor: UIColor) -> Color? {
        return UIColor == .clear ? nil : Color(UIColor)
    }
    
    private func getDwellingTypeText(type: DwellingType) -> String {
        switch type {
        case .SingleFamily:
            return "coverageDetails.dwellingType.singleFamily".localized(lang)
        case .MultiFamily:
            return "coverageDetails.dwellingType.MultiFamily".localized(lang)
        case .Apartment:
            return "coverageDetails.dwellingType.Apartment".localized(lang)
        case .Townhouse:
            return "coverageDetails.dwellingType.Townhouse".localized(lang)
        case .StudentHousing:
            return "coverageDetails.dwellingType.StudentHousing".localized(lang)
        }
    }
    
    // MARK: - Actions
    var didTapRenterPolicyCard: RenterPolicyClosure
    var didTapGetAQuoteAction: SimpleClosure
    
    var body: some View {
        VStack(alignment: .leading, spacing: InsetSpacing.xxxs) {
            Text("upselling.renters.title.label".localized(lang).uppercased())
                .captionSecondaryDarkBold()
                .padding(.leading, InsetSpacing.xs)
            
            if shouldShowStatusSelection {
                HorizontalSelectionButtonsView(data: selectedButtonsData) { selectedText in
                    if let newStatus = Status.allCases.first(where: {
                        $0.title == selectedText
                    }) {
                        DispatchQueue.main.async {
                            selectedStatus = newStatus
                        }
                    }
                }.padding(.leading, InsetSpacing.xs)
            }
            
            HoriontalCardListView(cardsData: rentersInsuranceDataList,
                                  didTapAddItemButton: didTapGetAQuoteAction)
            .frame(minHeight: 184)
        }
    }
}
