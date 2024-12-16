import UIKit

// MARK: - Computed Properties
extension CGFloat {
    
    var formatted: String {
        return String(format: "%.2f", self)
    }
}

// MARK: - Public Methods
extension CGFloat {
    
    func getCurrencyFormatted(fractionDigits: Int = 0) -> String {
        let currencyFormatter = NumberFormatter()
        currencyFormatter.usesGroupingSeparator = true
        currencyFormatter.numberStyle = .currency
        currencyFormatter.locale = Locale(identifier: "en_US")
        currencyFormatter.maximumFractionDigits = fractionDigits
        return currencyFormatter.string(from: NSNumber(value: self)) ?? ""
    }
}
