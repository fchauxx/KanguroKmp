import Foundation

public extension Double {
    
    func getCurrencyFormatted(fractionDigits: Int = 0) -> String {
        let currencyFormatter = NumberFormatter()
        currencyFormatter.usesGroupingSeparator = true
        currencyFormatter.numberStyle = .currency
        currencyFormatter.locale = Locale(identifier: "en_US")
        currencyFormatter.maximumFractionDigits = fractionDigits
        return currencyFormatter.string(from: NSNumber(value: self)) ?? ""
    }
}
