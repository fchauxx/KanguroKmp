import SwiftUI
import KanguroDesignSystem

public struct AdditionalCoverageSection: View {
    
    // MARK: - Property Wrappers
    @State private var selectedPopUpContent: AdditionalCoveragePopUpData?
    @Environment(\.appLanguageValue) var language
    
    // MARK: - Stored Properties
    let additionalCoveragesDataList: [AdditionalCoverageViewData]
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    
    // MARK: - Actions
    var didTapEditAdditionalCoverageAction: SimpleClosure
    var didChangePopUpContent: ((PopUpData) -> Void)
    
    public init(additionalCoveragesDataList: [AdditionalCoverageViewData],
                didTapEditAdditionalCoverageAction: @escaping SimpleClosure,
                didChangePopUpContent: @escaping ((PopUpData) -> Void)) {
        self.additionalCoveragesDataList = additionalCoveragesDataList
        self.didTapEditAdditionalCoverageAction = didTapEditAdditionalCoverageAction
        self.didChangePopUpContent = didChangePopUpContent
    }
    
    public var body: some View {
        ZStack {
            SectionInformationView(
                headerView: SectionHeaderView(
                    icon: Image.additionalCoverageSectionIcon,
                    title: "renters.policy.coverage.title".localized(
                        lang
                    )
                ),
                contentViewList: {
                    [
                        SectionContentView(
                            content: AnyView(createAdditionalCoverageListView())
                        ),
                        SectionContentView(
                            content: AnyView(
                                ActionCardButton(
                                    title: "renters.policy.additional.edit.label".localized(lang),
                                    icon: Image.editAdditionalCoverageIcon,
                                    style: .primary,
                                    didTapAction: {
                                        didTapEditAdditionalCoverageAction()
                                    }
                                )
                            )
                        )
                    ]
                }()
            )
        }
        .onChange(of: selectedPopUpContent) { data in
            guard let data else { return }
            let popUpData = PopUpData(title: data.title.localized(lang),
                                      description: data.description.localized(lang))
            didChangePopUpContent(popUpData)
            self.selectedPopUpContent = nil
        }
    }
    
    func createAdditionalCoverageListView() -> some View {
        var list: [AdditionalCoverageView] = []
        additionalCoveragesDataList.forEach { data in
            list.append(AdditionalCoverageView(data: data,
                                               popUpData: $selectedPopUpContent))
        }
        return AdditionalCoverageListView(additionalCoverages: list)
    }
}
