import UIKit

class BlotchyLabelView: BaseView, NibOwnerLoadable {

    // MARK: - IB Outlets
    @IBOutlet private var label: CustomLabel!

    // MARK: - Stored Properties
    var data: BlotchyLabelData?

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
extension BlotchyLabelView {

    func setup(data: BlotchyLabelData) {
        self.data = data
        label.set(text: data.text, style: TextStyle(color: data.color, weight: .bold, size: .p16))
    }
}
