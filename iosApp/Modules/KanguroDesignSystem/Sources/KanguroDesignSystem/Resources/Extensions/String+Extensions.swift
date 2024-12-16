import Foundation

public extension String {
    func localized(_ lang: String = "en") -> String {

        guard let path = Bundle.module.path(forResource: lang, ofType: "lproj"), let bundle = Bundle(path: path) else {
            return NSLocalizedString(self, bundle: .module, comment: "")
        }
        return NSLocalizedString(self, tableName: nil, bundle: bundle, value: "", comment: "")
    }
    
    var removeStartingNumbersAndPunctuation: String? {
        let pattern = "^[0-9.\\s\\W]+"
        let regex = try? NSRegularExpression(pattern: pattern, options: [])
        
        let range = NSRange(self.startIndex..<self.endIndex, in: self)
        let result = regex?.stringByReplacingMatches(in: self, options: [], range: range, withTemplate: "")
        
        return result
    }
}
