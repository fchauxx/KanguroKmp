import SwiftUI
import KanguroDesignSystem

public struct PriceItem: Hashable, Identifiable {
    
    let value: Double
    let tagName: String?
    
    public let id: Int
    
    public init(value: Double,
                tagName: String? = nil,
                id: Int) {
        self.value = value
        self.tagName = tagName
        self.id = id
    }
}

struct PriceItemsListView: View {
    
    // MARK: - Stored Properties
    let numberOfColumns: Int
    
    // MARK: - Wrapped Properties
    @State var items: [PriceItem]
    @State private var selectedIndex: Int?
    
    // MARK: - Actions
    var didUpdateSelectedIndex: IntClosure?
    
    // MARK: - Computed Properties
    var pairs: [[PriceItem]] {
        items.chunked(into: 2)
    }
    
    init(numberOfColumns: Int = 2, 
         items: [PriceItem],
         selectedIndex: Int? = nil,
         didUpdateSelectedIndex: IntClosure? = nil) {
        self.numberOfColumns = numberOfColumns
        self.items = items
        self.selectedIndex = selectedIndex
        self.didUpdateSelectedIndex = didUpdateSelectedIndex
    }
    
    var body: some View {
        VStack {
            ForEach(0..<pairs.count, id: \.self) { rowIndex in
                HStack(alignment: .bottom, spacing: InsetSpacing.xxs) {
                    ForEach(0..<numberOfColumns, id: \.self) { columnIndex in
                        let index = rowIndex * 2 + columnIndex
                        if index < items.count {
                            PickerButtonView(
                                items[index].value.getCurrencyFormatted(),
                                isSelected: isItemSelected(index: index),
                                tagName: items[index].tagName, {
                                    updateItemSelection(with: items[index].id)
                                }
                            )
                            .frame(width: 160)
                        }
                    }
                }
            }
        }
    }
    
    private func isItemSelected(index: Int) -> Bool {
        guard let selectedIndex,
              let selectedItem = items.first(where: { $0.id == selectedIndex }) else { return false }
        return items[index] == selectedItem
    }
    private func updateItemSelection(with id: Int) {
        selectedIndex = id
        didUpdateSelectedIndex?(id)
    }
}

struct LiabilityListView_Preview: PreviewProvider {
    static var previews: some View {
        PriceItemsListView(items: [
            PriceItem(value: 25.000, id: 0),
            PriceItem(value: 55.000, id: 1),
            PriceItem(value: 85.000, id: 2),
            PriceItem(value: 105.000, tagName: "Most Popular", id: 3)
        ])
        .padding(32)
    }
}
