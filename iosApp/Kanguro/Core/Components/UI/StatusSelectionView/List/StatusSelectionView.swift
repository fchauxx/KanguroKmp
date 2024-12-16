import UIKit

class StatusSelectionView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var stackView: UIStackView!
    
    // MARK: - Stored Properties
    var status: Status?
    var buttonsList: [StatusSelectionButtonView] = []
    
    // MARK: - Computed Properties
    var statusList: [Status] {
        return [.none, .active, .inactive]
    }
    
    // MARK: - Actions
    var didTapButtonAction: StatusClosure = { _ in }
    
    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
        setupLayout()
    }
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.loadNibContent()
    }
}

// MARK: - Setup
extension StatusSelectionView {
    
    private func setupLayout() {
        setupButtons()
        buttonsList.forEach { stackView.addArrangedSubview($0) }
        stackView.layoutIfNeeded()
    }
    
    private func setupButtons() {
        for status in statusList {
            let button = StatusSelectionButtonView()
            button.setup(status: status)
            button.update(isSelected: (status == .none))
            button.didTapButtonAction = { [weak self] status in
                guard let self else { return }
                self.status = status
                self.selectButton(button)
                self.didTapButtonAction(status)
            }
            buttonsList.append(button)
        }
    }
}

// MARK: - Private Methods
extension StatusSelectionView {
    
    private func selectButton(_ button: StatusSelectionButtonView) {
        button.update(isSelected: true)
        let diselectedButtons = buttonsList.filter { $0.status != status }
        diselectedButtons.forEach { $0.update(isSelected: false) }
    }
}
