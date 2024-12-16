import Foundation

// MARK: - Computed Properties
extension Double {
    
    var formatted: String {
        return String(format: "%.0f", self)
    }
}

// MARK: - Public Methods
extension Double {
    
    func getCurrencyFormatted(fractionDigits: Int = 0) -> String {
        let currencyFormatter = NumberFormatter()
        currencyFormatter.usesGroupingSeparator = true
        currencyFormatter.numberStyle = .currency
        currencyFormatter.locale = Locale(identifier: "en_US")
        currencyFormatter.maximumFractionDigits = fractionDigits
        return currencyFormatter.string(from: NSNumber(value: self)) ?? ""
    }
    
    func getFormatted(digits: Int) -> String {
        return String(format: "%.\(digits)f", self)
    }
    
    func getMeterToMilesFormatted() -> String {
        let mile = 0.000621371
        let value = self * mile
        return value.getFormatted(digits: 2)
    }
}
