import SwiftUI
import KanguroDesignSystem

public struct AdditionalCoverageListView: View {
    
    // MARK: - Wrapped Properties
    @Environment(\.appLanguageValue) var language
    
    // MARK: - Stored Properties
    let additionalCoverages: [AdditionalCoverageView]
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    
    public init(additionalCoverages: [AdditionalCoverageView]) {
        self.additionalCoverages = additionalCoverages
    }
    
    public var body: some View {
        ZStack {
            VStack {
                ForEach(additionalCoverages) { coverage in
                    coverage
                }
            }
        }
    }
}

struct AdditionalCoverageListView_Previews: PreviewProvider {
    static var previews: some View {
        AdditionalCoverageListView(additionalCoverages: [
            AdditionalCoverageView(
                data: AdditionalCoverageViewData(icon: Image.waterSewerBackupIcon,
                                                 title: "Sewer",
                                                 coverageLimit: 10,
                                                 deductibleLimit: 10,
                                                 popupType: .fraudProtection),
                popUpData: .constant(.fraudProtection))
        ])
    }
}
