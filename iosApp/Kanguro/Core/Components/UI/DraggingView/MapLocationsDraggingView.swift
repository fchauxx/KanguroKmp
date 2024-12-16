import UIKit

class MapLocationsDraggingView: UIView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var draggingButtonView: UIView!
    @IBOutlet private var draggingButtonImageView: UIImageView!
    @IBOutlet private var stackView: UIStackView!
    
    // MARK: - Stored Property
    var mapData: [VetLocationData] = []
    var isExpanded = false
    
    // MARK: - Computed Property
    var mapDataSortedByDistance: [VetLocationData] {
        let sortedList: [VetLocationData]? = mapData.sorted(by: {
            guard let distance1 = $0.distance,
                  let distance2 = $1.distance else { return false }
            return (distance2) > (distance1)
        })
        return sortedList ?? mapData
    }
    
    // MARK: - Actions
    var didDragAction: SimpleClosure = {}
    var didTapLocationAction: LocationClosure = { _ in }
    
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
extension MapLocationsDraggingView {
    
    private func setupLayout() {
        stackView.removellArrangedSubviews()
        for locationData in mapDataSortedByDistance {
            let card = VetLocationCardView()
            card.setup(data: locationData)
            card.didTapLocationAction = didTapLocationAction
            stackView.addArrangedSubview(card)
        }
        stackView.layoutIfNeeded()
    }
}

// MARK: - Private Methods
extension MapLocationsDraggingView {
    
    private func updateImage() {
        let image = isExpanded ? UIImage(named: "ic-down-arrow") : UIImage(named: "ic-up-arrow")
        draggingButtonImageView.image = image
    }
}

// MARK: - Public Methods
extension MapLocationsDraggingView {
    
    func setup(mapData: [VetLocationData]) {
        self.mapData = mapData
        setupLayout()
    }
}

// MARK: - IB Actions
extension MapLocationsDraggingView {
    
    @IBAction private func draggingButtonTouchUpInside(_ sender: UIButton) {
        isExpanded.toggle()
        updateImage()
        didDragAction()
    }
}
