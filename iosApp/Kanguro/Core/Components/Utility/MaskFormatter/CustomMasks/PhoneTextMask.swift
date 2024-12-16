import Foundation

struct PhoneTextMask {
    
    // MARK: - Stored Properties
    let formatter = MaskFormatter()
    
    // MARK: - Computed Properties
    var mask: String {
        return "###-###-####"
    }
}

// MARK: - TextMask
extension PhoneTextMask: TextMask {
    
    func apply(text: String) -> String {
        return formatter.format(text: text, mask: mask)
    }
}
