import SwiftUI
import KanguroSharedDomain

public struct KeyValueListView: View, Identifiable {
    
    // MARK: - Stored Properties
    public let id = UUID()
    let type: ValueLabelViewType
    let data: [KeyValueLabelData]?
    var documentsData: [PolicyDocumentData]? = nil
    var didTapDocumentAction: AnyClosure = { _ in }
    let isDivided: Bool
    
    // MARK: - Initializers
    public init(type: ValueLabelViewType,
                data: [KeyValueLabelData] = [KeyValueLabelData(key: "", value: "")],
                documentsData:  [PolicyDocumentData]? =  nil,
                didTapDocumentAction: @escaping AnyClosure = { _ in },
                isDivided: Bool = false) {
        self.type = type
        self.data = data
        self.documentsData = documentsData
        self.didTapDocumentAction = didTapDocumentAction
        self.isDivided = isDivided
    }
    
    public var body: some View {
        VStack(spacing: isDivided ? StackSpacing.nano : StackSpacing.xxxs) {
            if let documents = documentsData {
                ForEach(documents, id: \.name) { document in
                    makeDocumentDataView(KeyValueLabelData(key: document.name ?? "", value: ""),documentData: document)
                }
            }
            else if let data = data{
                ForEach(data, id: \.self) { singleData in
                    makeDataView(singleData)
                }
            }
        }
        .background(Color.clear)
    }
    
    @ViewBuilder
    private func makeDataView(_ singleData: KeyValueLabelData) -> some View {
        VStack(spacing: StackSpacing.nano) {
            if isDivided { Divider() }
            
            KeyValueLabelView(type: type, data: singleData)
                .padding(.horizontal, isDivided ? InsetSpacing.xxxs : 0)
            
            if isDivided && singleData.id == data?.last?.id {
                Divider()
            }
        }
    }
    
    @ViewBuilder
    private func makeDocumentDataView(_ singleData: KeyValueLabelData, documentData: PolicyDocumentData? = nil) -> some View {
        VStack(spacing: StackSpacing.nano) {
            if isDivided { Divider() }
            
            KeyValueLabelView(type: type, data: singleData)
                .padding(.horizontal, isDivided ? InsetSpacing.xxxs : 0)
                .onTapGesture {
                    if let document = documentData{
                        didTapDocumentAction(document)
                    }
                }
            
            if isDivided && singleData.id == data?.last?.id {
                Divider()
            }
        }
    }
}

// MARK: - Previews
struct PrimaryValuesListView_Previews: PreviewProvider {
    static var previews: some View {
        KeyValueListView(type: .primary, data: [
            KeyValueLabelData(key: "Liability",
                              value: 10000.getCurrencyFormatted()),
            KeyValueLabelData(key: "Deductible", 
                              value: 500.getCurrencyFormatted()),
            KeyValueLabelData(key: "Loss of use",
                              value: 10000.getCurrencyFormatted())
        ], isDivided: true)
        .previewDevice(.init(rawValue: "iPhone 14Pro"))
        .previewDisplayName("iPhone 14 Pro")

        KeyValueListView(type: .secondary, data: [
            KeyValueLabelData(key: "Liability", 
                              value: 10000.getCurrencyFormatted()),
            KeyValueLabelData(key: "Deductible",
                              value: 500.getCurrencyFormatted()),
            KeyValueLabelData(key: "Loss of use",
                              value: 10000.getCurrencyFormatted())
        ])
        .previewDevice(.init(rawValue: "iPhone 14"))
        .previewDisplayName("iPhone 14")
    }
}
