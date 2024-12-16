import SwiftUI

public struct TextStyle: Hashable {
    
    let font: Font
    let color: Color
    let underlined: Bool?
    
    public init(font: Font, color: Color, underlined: Bool? = false) {
        self.font = font
        self.color = color
        self.underlined = underlined
    }
}

public struct HighlightedText: View {
    
    // MARK: - Stored Properties
    public let text: String
    public let highlightedText: String?
    public let baseStyle: TextStyle
    public let highlightedStyle: TextStyle?
    
    // MARK: - Computed Properties
    private var attributedString: AttributedString {
        guard let highlightedText, let highlightedStyle else { return "" }
        var attributedString = AttributedString(text)
        
        attributedString.foregroundColor = baseStyle.color
        attributedString.font = baseStyle.font
        attributedString.underlineStyle = (baseStyle.underlined ?? false) ? .single : nil
        
        if let range = attributedString.range(of: highlightedText) {
            attributedString[range].foregroundColor = highlightedStyle.color
            attributedString[range].font = highlightedStyle.font
            attributedString[range].underlineStyle = (highlightedStyle.underlined ?? false) ? .single : nil
        }
        
        return attributedString
    }
    
    // MARK: - Initializers
    public init(text: String,
                highlightedText: String? = nil,
                baseStyle: TextStyle,
                highlightedStyle: TextStyle? = nil) {
        self.text = text
        self.highlightedText = highlightedText
        self.baseStyle = baseStyle
        self.highlightedStyle = highlightedStyle
    }
    
    public var body: some View {
        VStack {
            Text(attributedString)
        }
    }
}

// MARK: - Previews
struct HighlightedText_Previews: PreviewProvider {
    
    static var previews: some View {
        HighlightedText(text: "This is a swiftUI example",
                        highlightedText: "swiftUI",
                        baseStyle: TextStyle(font: .lato(.latoRegularItalic, size: 30),
                                             color: .secondaryDark),
                        highlightedStyle: TextStyle(font: .lato(.latoBold, size: 40),
                                                    color: .green))
    }
}
