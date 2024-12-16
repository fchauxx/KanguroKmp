import UIKit

// MARK: - MaskChar
private struct MaskChar {
    var char: String
    var position: Int
}

// MARK: - Public Methods
struct MaskFormatter {
    
    func format(text: String, mask: String) -> String {
        let charsToInsert: [MaskChar] = getCharsToInsert(mask: mask)
        var formattedText = Array(text)
        for char in charsToInsert {
            if char.position >= formattedText.count {
                return String(formattedText)
            }
            formattedText.insert(contentsOf: char.char, at: char.position)
        }
        if formattedText.count > mask.count {
            let index = formattedText.index(formattedText.startIndex, offsetBy: mask.count)
            return String(formattedText[..<index])
        }
        return String(formattedText)
    }
}

// MARK: - Private Methods
extension MaskFormatter {
    
    private func getCharsToInsert(mask: String) -> [MaskChar] {
        var charsToInsert: [MaskChar] = []
        let maskChars = Array(mask)
        for index in 0..<maskChars.count {
            let char = maskChars[index]
            if char != "#" {
                let charToInsert = MaskChar(char: String(char), position: index)
                charsToInsert.append(charToInsert)
            }
        }
        return charsToInsert
    }
}
