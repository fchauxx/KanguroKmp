import Foundation
import Resolver
import KanguroStorageDomain
import KanguroSharedDomain

// MARK: - Dependencies
extension String {
    
    var preferredLanguage: Language {
        @LazyInjected var userDefaults: Storage
        let stringLang: String = userDefaults.get(key: "preferredLanguage") ?? "en"
        return Language(rawValue: stringLang) ?? .English
    }
}

// MARK: - Computed Properties
extension String {
    
    var date: Date? {
        var date: Date?
        let types: [DateFormatTypes] = DateFormatTypes.allCases
        let dateFormatter = DateFormatter()
        dateFormatter.timeZone = TimeZone(abbreviation: "UTC")
        
        for type in types {
            dateFormatter.dateFormat = type.description
            date = dateFormatter.date(from: self)
            if let date {
                return date
            }
        }
        return nil
    }
    
    var length: Int {
        return count
    }
    
    var localized: String {
        guard let path = Bundle.main.path(forResource: preferredLanguage.rawValue, ofType: "lproj"),
              let bundle = Bundle(path: path) else { return "" }
        
        return NSLocalizedString(self, bundle: bundle, comment: "")
    }
    
    var escaped: String? {
        return self.addingPercentEncoding(withAllowedCharacters: .afURLQueryAllowed)
    }
    
    var isValidEmail: Bool {
        let regex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}"
        return range(of: regex, options: .regularExpression, range: nil, locale: nil) != nil
    }
    
    var isValidPassword: Bool {
        let regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*?[0-9]).{6,}$"
        return range(of: regex, options: .regularExpression, range: nil, locale: nil) != nil
    }
    
    var isValidPhone: Bool {
        let phoneRegex = "^\\d{3}-\\d{3}-\\d{4}$"
        let phoneTest = NSPredicate(format: "SELF MATCHES %@", phoneRegex)
        return phoneTest.evaluate(with: self)
    }
    
    var isValidCreatedPassword: Bool {
        let regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[d$@$!%*?&#])[A-Za-z\\dd$@$!%*?&#]{6,}"
        return range(of: regex, options: .regularExpression, range: nil, locale: nil) != nil
    }
    
    var isValidBankRountingNumber: Bool {
        let regex = "^[0-9]{9}$"
        let regexValidation = range(of: regex, options: .regularExpression, range: nil, locale: nil) != nil
        return regexValidation && checkRoutingNumberChecksum()
    }
    
    var isValidBankAccount: Bool {
        let regex = "^[0-9]{5,20}$"
        return range(of: regex, options: .regularExpression, range: nil, locale: nil) != nil
    }
    
    var onlyNumbers: String {
        let okayChars = Set("1234567890")
        return self.filter { okayChars.contains($0) }
    }
    
    var onlyNumbersAndPuntuaction: String {
        let okayChars = Set("1234567890.,")
        return self.filter { okayChars.contains($0) }
    }
    
    var onlyNumbersAndLetters: String {
        let okayChars = Set("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLKMNOPQRSTUVWXYZ1234567890ãÃõÕâÂôÔéÉàÀ")
        return self.filter { okayChars.contains($0) }
    }
    
    var urlWithoutQuery: String {
        var components = URLComponents(string: self)
        components?.query = nil
        return components?.url?.absoluteString ?? ""
    }
    
    var getBankAccountFormatted: String {
        return String(repeating: "•", count: Swift.max(0, count-4)) + suffix(4)
    }
    
    var getOnlyFirstCharacterCapitalized: String {
        return prefix(1).capitalized + dropFirst().lowercased()
    }
    
    var getPrefixBeforeDot: String? {
        return self.components(separatedBy: ".").first
    }
    
    var getFileExtension: String? {
        return self.components(separatedBy: ".").last
    }
    
    var truncateLongString: String {
        if self.count > 50 {
            return "\(self.substring(toIndex: 25))...\(self.substring(fromIndex: 45))"
        } else {
            return self
        }
    }
    var convertToFloat: CGFloat? {
        var stringNumber = self.onlyNumbersAndPuntuaction
        stringNumber = stringNumber.replacingOccurrences(of: ",", with: "")
        if let doubleValue = Double(stringNumber) {
            return CGFloat(doubleValue)
        }
        return nil
    }
    var parseJSONAndReturnElementCount: Int {
        let jsonString = self
        let jsonObjects = jsonString.components(separatedBy: "|")
        var count = 0
        for jsonObjectString in jsonObjects {
            if let jsonData = jsonObjectString.data(using: .utf8) {
                do {
                    if let _ = try JSONSerialization.jsonObject(with: jsonData, options: []) as? [String: String] {
                        count += 1
                    }
                } catch {
                    print("Error parsing JSON: \(error.localizedDescription)")
                }
            }
        }
        return count
    }
}

// MARK: - Public Methods
extension String {
    
    func insertTextAfterIndex(text: String, character: Character) -> String {
        var newText = self
        if let textIndex = self.firstIndex(of: character) {
            let indexAfterCharacter = newText.index(after: textIndex)
            newText.insert(contentsOf: text, at: indexAfterCharacter)
        }
        return newText
    }
    
    func getPhoneFormatted(maskFormat: String) -> String {
        let cleanPhoneNumber = self.components(separatedBy: CharacterSet.decimalDigits.inverted).joined()
        var result = ""
        var index = cleanPhoneNumber.startIndex
        for char in maskFormat where index < cleanPhoneNumber.endIndex {
            if char == "#" {
                result.append(cleanPhoneNumber[index])
                index = cleanPhoneNumber.index(after: index)
            } else {
                result.append(char)
            }
        }
        return result
    }
    
    func replaceFirstOccurrence(of pattern: String, with replacement: String) -> String {
        guard let range = self.range(of: pattern, options: .regularExpression) else {
            return self
        }
        return replacingCharacters(in: range, with: replacement)
    }
}

// MARK: - Bank Routing Validation
extension String {
    
    func checkRoutingNumberChecksum() -> Bool {
        if self.count != 9 {
            return false
        }
        
        let bankRoutingNumber: [Int] = self.compactMap{$0.wholeNumberValue}
        let sum = (3 * (bankRoutingNumber[0] + bankRoutingNumber[3] + bankRoutingNumber[6]))
        + (7 * (bankRoutingNumber[1] + bankRoutingNumber[4] + bankRoutingNumber[7])
           + (bankRoutingNumber[2] + bankRoutingNumber[5] + bankRoutingNumber[8]))
        
        let mod = sum % 10
        if mod == 0 {
            return true
        }
        return false
    }
}

// MARK: - Substring
extension String {
    
    func substring(fromIndex: Int) -> String {
        return self[min(fromIndex, length) ..< length]
    }
    
    func substring(toIndex: Int) -> String {
        return self[0 ..< max(0, toIndex)]
    }
}

// MARK: - Subscripts
extension String {
    
    subscript(range: Range<Int>) -> String {
        let newRange = Range(uncheckedBounds: (lower: max(0, min(length, range.lowerBound)),
                                               upper: min(length, max(0, range.upperBound))))
        let start = index(startIndex, offsetBy: newRange.lowerBound)
        let end = index(start, offsetBy: newRange.upperBound - newRange.lowerBound)
        return String(self[start ..< end])
    }
    
    subscript(index: Int) -> String {
        return self[index ..< index + 1]
    }
}
