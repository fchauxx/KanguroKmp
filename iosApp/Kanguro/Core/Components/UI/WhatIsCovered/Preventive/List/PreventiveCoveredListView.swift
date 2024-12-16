import UIKit
import KanguroSharedDomain

class PreventiveCoveredListView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet var stackView: UIStackView!
    @IBOutlet private var loaderView: UIActivityIndicatorView!
    
    // MARK: - Computed Properties
    var preventiveCoveredCardListData: [PreventiveCoveredCardViewProtocol] {
        return stackView.arrangedSubviews as? [PreventiveCoveredCardViewProtocol] ?? []
    }
    var hasCheckboxSelected: Bool {
        return preventiveCoveredCardListData.contains { $0.isCheckboxSelected == true }
    }
    var concatenatedNames: String {
        var finalString = ""
        for item in preventiveCoveredCardListData {
            if let name = item.data?.name,
               item.isCheckboxSelected {
                finalString.isEmpty ? finalString.append(name) : finalString.append(", \(name)")
            }
        }
        return finalString
    }
    
    // MARK: - Actions
    var didTapCheckboxAction: SimpleClosure = {}
    
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
extension PreventiveCoveredListView {
    
    func setupLayout() {
        setLoadingViewHidden(false)
    }
    
    func setLoadingViewHidden(_ isHidden: Bool) {
        loaderView.setScaleSize(1.5)
        loaderView.isHidden = isHidden
    }
    
    func setupData(_ data: [KanguroSharedDomain.CoverageData], isEditable: Bool) {
        for item in data {
            let card = PreventiveCoveredCardView()
            card.setupData(item, isEditable: isEditable)
            card.didTapCheckboxAction = { self.didTapCheckboxAction() }
            stackView.addArrangedSubview(card)
        }
        stackView.layoutIfNeeded()
        stackView.isHidden = false
        setLoadingViewHidden(true)
    }
}
