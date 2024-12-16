import UIKit

class TagView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet weak var labelView: CustomLabel!
    
    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
    }
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.loadNibContent()
    }
}

// MARK: - Setup
extension TagView {
    
    func setup(bgColor: UIColor, text: String, textStyle: TextStyle) {
        self.backgroundColor = bgColor
        cornerRadius = 4
        labelView.set(text: text, style: textStyle)
    }
}
