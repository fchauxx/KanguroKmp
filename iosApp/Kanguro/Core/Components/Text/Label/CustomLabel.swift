import UIKit

class CustomLabel: UILabel {
    
    // MARK: - Stored Properties
    var textBuilder = TextBuilder()
}

// MARK: - Public Methods
extension CustomLabel {
    
    func set(text: String, style: TextStyle = TextStyle()) {
        attributedText = textBuilder.buildText(text: text, style: style)
        applyStyle(style: style)
    }
    
    func set(style: TextStyle) {
        guard let text = text else { return }
        attributedText = textBuilder.buildText(text: text, style: style)
        applyStyle(style: style)
    }
    
    func setHighlightedText(text: String, style: TextStyle, highlightedText: String, highlightedStyle: TextStyle? = nil) {
        applyStyle(style: style)
        attributedText = textBuilder.buildHighlightedText(text: text, style: style, highlightedText: highlightedText, highlightedStyle: highlightedStyle)
    }
    
    func setupToFitWidth() {
        self.adjustsFontSizeToFitWidth = true
        self.numberOfLines = 1
    }
    
    func setChangingLabels(list: [String], duration: Double, style: TextStyle) {
        guard let firstName = list.first else { return }
        self.set(text: firstName, style: style)
        let names = list.map { $0 }
        var index = 0
        Timer.scheduledTimer(withTimeInterval: duration, repeats: true, block: { [weak self] _ in
            guard let self = self else { return }
            index == (list.count-1) ? (index = 0) : (index += 1)
            let currentString = names[index]
            self.setTypingText(text: currentString)
        })
    }
    
    func setTypingText(text: String, characterDelay: TimeInterval = 1.0) {
        self.text = ""
        let writingTask = DispatchWorkItem { [weak self] in
            text.forEach { char in
                DispatchQueue.main.async {
                    self?.text?.append(char)
                }
                Thread.sleep(forTimeInterval: characterDelay/30)
            }
        }
        let queue: DispatchQueue = .init(label: "typespeed", qos: .userInteractive)
        queue.asyncAfter(deadline: .now() + 0.05, execute: writingTask)
    }
}

// MARK: - Private Methods
extension CustomLabel {
    
    private func applyStyle(style: TextStyle) {
        self.minimumScaleFactor = style.minimumScaleFactor
        self.numberOfLines = style.lines
    }
}
