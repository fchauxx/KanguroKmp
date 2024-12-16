import SwiftUI

public struct KeyValueLabelData: Hashable, Identifiable {
    
    public let id = UUID()
    public let key: String
    public let subKey: String?
    public let value: String

    public init(key: String,
                subkey: String? = nil,
                value: String) {
        self.key = key
        self.subKey = subkey
        self.value = value
    }
}

public struct KeyValueLabelView: View {
    
    // MARK: - Stored Properties
    let type: ValueLabelViewType
    let data: KeyValueLabelData
    
    // MARK: - Initializers
    public init(type: ValueLabelViewType, data: KeyValueLabelData) {
        self.type = type
        self.data = data
    }
    
    public var body: some View {
        HStack(spacing: 0) {
            Text(data.key)
                .font(type.keyFontStyle.font)
                .foregroundColor(type.keyFontStyle.color)
            if let optionalData = data.subKey {
                Text(optionalData)
                    .footnote2SecondaryMediumRegular()
            }
            Spacer()
            Text(data.value)
                .font(type.valueFontStyle.font)
                .foregroundColor(type.valueFontStyle.color)
        }
        .background(Color.clear)
    }
}

// MARK: - Previews
struct PrimaryValuesLabelView_Previews: PreviewProvider {
    
    static var previews: some View {
        KeyValueLabelView(
            type: .primary, 
            data: KeyValueLabelData(key: "Liability",
                                    value: 10000.getCurrencyFormatted()))
        .previewDevice(.init(rawValue: "iPhone 14"))
        .previewDisplayName("iPhone 14")

        KeyValueLabelView(type: .secondary, data:
                            KeyValueLabelData(key: "Loss of use",
                                              value: 10000.getCurrencyFormatted())
        )
        .previewDevice(.init(rawValue: "iPhone 14Pro"))
        .previewDisplayName("iPhone 14 Pro")
    }
}
