import UIKit

class CustomPickerSelectionListView: BaseView, NibOwnerLoadable {

    // MARK: - IB Outlets
    @IBOutlet private var stackView: UIStackView!
    @IBOutlet var alphaView: UIView!
    @IBOutlet var listView: UIView!

    // MARK: - Stored Properties
    var data: [CustomPickerData]

    // MARK: - Actions
    var didTapCustomPickerCardAction: TextFieldCustomPickerDataClosure = { _ in }

    // MARK: - Initializers
    required init?(coder: NSCoder) {
        data = []
        super.init(coder: coder)
        self.loadNibContent()
    }

    override init(frame: CGRect) {
        data = []
        super.init(frame: frame)
        self.loadNibContent()
    }
}

// MARK: - Public Methods
extension CustomPickerSelectionListView {

    func setup(data: [CustomPickerData]) {
        self.data = data
        setupLayout()
    }

    func setupLayout() {
        clearAttachmentList()
        setupViews()
        setupPickerSelectionView()
    }

    func setupViews() {
        listView.roundCorners(corners: [.topRight, .topLeft], radius: 16)
    }

    func setupPickerSelectionView() {
        for item in data {
            let customPickerView = CustomPickerSelectionView()
            customPickerView.setup(data: item)
            customPickerView.didTapCustomPickerCardAction = { [weak self] pickedItem in
                guard let self else { return }
                self.didTapCustomPickerCardAction(pickedItem)
            }
            stackView.addArrangedSubview(customPickerView)
        }
        layoutIfNeeded()
    }

    func clearAttachmentList() {
        stackView.removellArrangedSubviews()
    }
}
