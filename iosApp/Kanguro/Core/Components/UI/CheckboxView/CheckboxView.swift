import UIKit

class CheckboxView: BaseView, NibOwnerLoadable, FormViewProtocol {
    
    // MARK: - Dependencies
    var viewModel: CheckboxViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet private var bgView: UIView!
    @IBOutlet private var selectionView: UIView!
    
    // MARK: - Actions
    var didTapAction: BoolClosure = { _ in }
    
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

// MARK: - View State
extension CheckboxView {
    
    private func changed(state: DefaultViewState) {
        
        switch state {
        case .dataChanged:
            animateSelection()
        default:
            break
        }
    }
}

// MARK: - Setup
extension CheckboxView {
    
    private func setupObserver() {
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
    
    private func setupLayout() {
        viewModel.isEditable ? setupViews() : (bgView.backgroundColor = .secondaryLightest)
    }
    
    private func setupViews() {
        guard let isSelected = viewModel.isSelected else { return }
        bgView.borderColor = isSelected ? .secondaryDarkest : .secondaryMedium
        selectionView.isHidden = !isSelected
        selectionView.setAsCircle()
    }
    
    private func animateSelection() {
        UIView.animate(withDuration: 0.2) { [weak self] in
            guard let self = self else { return }
            self.setupViews()
        }
    }
    
    private func updateSelection() {
        guard let isSelected = viewModel.isSelected else { return }
        var currentIsSelected = isSelected
        currentIsSelected.toggle()
        viewModel.update(isSelected: currentIsSelected)
    }
}

// MARK: - Public Methods
extension CheckboxView {
    
    func setup(isEditable: Bool = true, isSelected: Bool = false) {
        viewModel.isEditable = isEditable
        viewModel.isSelected = isSelected
        setupObserver()
        setupLayout()
    }
    
    func update(didTapAction: @escaping BoolClosure) {
        self.didTapAction = didTapAction
    }

    func update(isSingleChoice: Bool) {
        self.bgView.setAsCircle()
    }
}

// MARK: - IB Actions
extension CheckboxView {
    
    @IBAction private func buttonTouchUpInside(_ sender: UIButton) {
        
        if viewModel.isEditable {
            guard let isSelected = viewModel.isSelected else { return }
            updateSelection()
            didTapAction(isSelected)
        }
    }
}
