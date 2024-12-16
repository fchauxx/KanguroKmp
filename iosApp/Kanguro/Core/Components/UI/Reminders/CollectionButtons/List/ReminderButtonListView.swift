import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class ReminderButtonListView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var stackView: UIStackView!
    
    // MARK: - Stored Properties
    var currentPet: Pet?
    var pets: [Pet?] = []
    var buttonsList: [ReminderButtonView] = []
    
    // MARK: - Actions
    var didTapButtonAction: OptionalPetClosure = { _ in }
    
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
extension ReminderButtonListView {
    
    private func setupLayout() {
        setupButtons()
        buttonsList.forEach { stackView.addArrangedSubview($0) }
        stackView.layoutIfNeeded()
    }
    
    private func setupButtons() {
        for pet in pets {
            let button = ReminderButtonView()
            button.setup(pet: pet)
            button.update(isSelected: (pet == nil))
            button.didTapButtonAction = { [weak self] pet in
                guard let self else { return }
                self.currentPet = pet
                self.selectButton(button)
                self.didTapButtonAction(pet)
            }
            buttonsList.append(button)
        }
    }
}

// MARK: - Private Methods
extension ReminderButtonListView {
    
    private func selectButton(_ button: ReminderButtonView) {
        button.update(isSelected: true)
        let diselectedButtons = buttonsList.filter { $0.pet?.id != currentPet?.id }
        diselectedButtons.forEach { $0.update(isSelected: false) }
    }
}

// MARK: - Public Methods
extension ReminderButtonListView {
    
    func setup(pets: [Pet]) {
        self.pets.append(nil)
        self.pets.append(contentsOf: pets)
        setupLayout()
    }
}
