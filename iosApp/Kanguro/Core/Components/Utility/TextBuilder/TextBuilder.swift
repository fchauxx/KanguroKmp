import UIKit

// MARK: - Public Methods
class TextBuilder {
    
    func buildText(text: String, style: TextStyle) -> NSAttributedString {
        
        let paragraphStyle = NSMutableParagraphStyle()
        paragraphStyle.lineSpacing = style.lineSpacing
        paragraphStyle.alignment = style.alignment
        paragraphStyle.lineHeightMultiple = CGFloat(style.lineHeightMultiple)
        
        return NSMutableAttributedString(string: text,
                                         attributes: [NSAttributedString.Key.font: getFont(style: style),
                                                      NSAttributedString.Key.foregroundColor: style.color,
                                                      NSAttributedString.Key.underlineStyle: style.underlined ? 1 : 0,
                                                      NSAttributedString.Key.paragraphStyle: paragraphStyle])
    }
    
    func buildHighlightedText(text: String,
                              style: TextStyle,
                              highlightedText: String,
                              highlightedStyle: TextStyle? = nil) -> NSMutableAttributedString {
        
        let regularFont = getFont(style: style)
        
        var currentHighlightedStyle = highlightedStyle ?? style
        let highlightedWeight = highlightedStyle?.weight ?? .bold
        currentHighlightedStyle.weight = highlightedWeight
        
        let highlightedFont = getFont(style: currentHighlightedStyle)
        let paragraphStyle = NSMutableParagraphStyle()
        
        paragraphStyle.lineSpacing = style.lineSpacing
        paragraphStyle.alignment = style.alignment
        paragraphStyle.lineHeightMultiple = CGFloat(style.lineHeightMultiple)
        
        let NSUnderlined = (highlightedStyle?.underlined ?? false) ? 1 : 0
        
        let attributedString = NSMutableAttributedString(string: text, attributes: [NSAttributedString.Key.font: regularFont,
                                                                                    NSAttributedString.Key.foregroundColor: style.color,
                                                                                    NSAttributedString.Key.paragraphStyle: paragraphStyle])
        
        let highlightedFontAttribute: [NSAttributedString.Key: Any] = [NSAttributedString.Key.font: highlightedFont,
                                                                       NSAttributedString.Key.foregroundColor: highlightedStyle?.color ?? style.color,
                                                                       NSAttributedString.Key.underlineStyle: NSUnderlined]
        
        guard let regex = try? NSRegularExpression(pattern: highlightedText) else {
            return attributedString
        }
        
        let regexRange = NSRange(location: 0, length: text.count)
        let allMatches = regex.matches(in: text, options: [], range: regexRange)
        
        for match in allMatches {
            attributedString.addAttributes(highlightedFontAttribute, range: match.range)
        }
        
        return attributedString
    }
    
    func buildAttributedStyle(style: TextStyle = TextStyle()) -> [NSAttributedString.Key : Any]? {
        let font = getFont(style: style)
        return [NSAttributedString.Key.foregroundColor: style.color,
                NSAttributedString.Key.font: font]
    }
}

// MARK: - Private Methods
extension TextBuilder {
    
    private func getFont(style: TextStyle) -> UIFont {
        switch style.font {
        case .raleway:
            return UIFont.raleway(withSize: style.size.rawValue, withWeight: style.weight)
        case .lato:
            return UIFont.lato(withSize: style.size.rawValue, withWeight: style.weight)
        }
    }
}
