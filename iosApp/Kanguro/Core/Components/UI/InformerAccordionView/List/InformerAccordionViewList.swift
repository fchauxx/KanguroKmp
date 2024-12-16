import UIKit
import KanguroSharedDomain

class InformerAccordionViewList: BaseView, NibOwnerLoadable {

    // MARK: - IB Outlets
    @IBOutlet private var stackView: UIStackView!
    
    // MARK: - Computed Properties
    var stackViewSubviews: [InformerAccordionView] {
        return stackView.subviews as? [InformerAccordionView] ?? []
    }

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

// MARK: - Public Methods
extension InformerAccordionViewList {

    func setup(with informerViewsData: [InformerData]) {
        for data in informerViewsData {
            let informerView = InformerAccordionView()
            informerView.setupInformerData(data)
            stackView.addArrangedSubview(informerView)
        }
        layoutIfNeeded()
    }
}
