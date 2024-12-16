import UIKit

class VetAdviceBaseViewController: BaseViewController {
    
    // MARK: - IB Outlets
    @IBOutlet private var navigationBackButton: NavigationBackButton!
    @IBOutlet private var veterinaryView: VeterinaryCardView!
    @IBOutlet private var dogVetAdviceCardView: VetAdviceCardView!
    @IBOutlet private var catVetAdviceCardView: VetAdviceCardView!
    
    // MARK: Computed Properties
    var vetAdviceCardViews: [VetAdviceCardView] {
        return [dogVetAdviceCardView, catVetAdviceCardView]
    }
    
    // MARK: Actions
    var backAction: SimpleClosure = {}
    var didTapVetAdviceCardAction: VetAdviceTypeClosure = { _ in }
}

// MARK: - Life Cycle
extension VetAdviceBaseViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
    }
}

// MARK: - Setup
extension VetAdviceBaseViewController {
    
    private func setupLayout() {
        setupNavigationBackButton()
        setupVetAdviceCardViews()
    }
    
    private func setupNavigationBackButton() {
        navigationBackButton.update(action: backAction)
        navigationBackButton.update(title: "common.back".localized)
    }
    
    private func setupVetAdviceCardViews() {
        dogVetAdviceCardView.setupType(.dog)
        catVetAdviceCardView.setupType(.cat)
        vetAdviceCardViews.forEach { $0.update(didTapVetAdviceCardAction) }
    }
}
