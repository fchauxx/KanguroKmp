import Foundation

struct CurrencyTextMask {
    
    // MARK: - Stored Properties
    let formatter = MaskFormatter()
}

// MARK: - TextMask
extension CurrencyTextMask: TextMask {
    
    func apply(text: String) -> String {
        let formatter = NumberFormatter()
        formatter.numberStyle = .currencyAccounting
        formatter.locale = Locale(identifier: "en_US")
        formatter.maximumFractionDigits = 2
        formatter.minimumFractionDigits = 2
        
        let amountWithPrefix = text.onlyNumbers
        let double = (amountWithPrefix as NSString).doubleValue
        
        var number: NSNumber!
        number = NSNumber(value: (double / 100))
        
        guard number != 0 as NSNumber else { return "$0.00" }
        return formatter.string(from: number) ?? ""
    }
}
