import UIKit

class VerticalStepListView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var stackView: UIStackView!
    
    // MARK: - Stored Properties
    var dataList: [VerticalStepViewData]?
    
    // MARK: - Computed Properties
    var viewsList: [VerticalStepView] {
        guard let dataList else { return [] }
        var list: [VerticalStepView] = []
        for data in dataList {
            let stepView = VerticalStepView()
            stepView.setup(data: data)
            list.append(stepView)
        }
        return list
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

// MARK: - Setup
extension VerticalStepListView {
    
    private func setupLayout() {
        viewsList.forEach { item in
            stackView.addArrangedSubview(item)
        }
        stackView.layoutIfNeeded()
    }
}

// MARK: - Public Methods
extension VerticalStepListView {
    
    func setup(dataList: [VerticalStepViewData]) {
        self.dataList = dataList
        setupLayout()
    }
}
