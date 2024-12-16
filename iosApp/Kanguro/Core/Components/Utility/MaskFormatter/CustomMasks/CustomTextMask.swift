import Foundation

struct CustomTextMask {
    
    // MARK: - Stored Properties
    let formatter = MaskFormatter()
    let mask: String
}

// MARK: - TextMask
extension CustomTextMask: TextMask {

    func apply(text: String) -> String {
        return formatter.format(text: text, mask: mask)
    }
}
