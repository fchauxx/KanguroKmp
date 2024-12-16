import UIKit

class TopicLabelListView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var stackView: UIStackView!
    
    // MARK: - Stored Properties
    var dataList: [TopicLabelViewData]?
    
    // MARK: - Computed Properties
    var viewsList: [TopicLabelView] {
        guard let dataList else { return [] }
        var list: [TopicLabelView] = []
        for data in dataList {
            let labelView = TopicLabelView()
            labelView.setup(data: data)
            list.append(labelView)
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
extension TopicLabelListView {
    
    private func setupLayout() {
        viewsList.forEach { item in
            stackView.addArrangedSubview(item)
        }
        stackView.layoutIfNeeded()
    }
}

// MARK: - Public Methods
extension TopicLabelListView {
    
    func setup(dataList: [TopicLabelViewData]) {
        self.dataList = dataList
        setupLayout()
    }
}
