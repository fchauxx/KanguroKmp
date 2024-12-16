import UIKit

class PetParentsViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: InformerViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet var scrollableInformerBaseView: ScrollableInformerBaseView!
    
    // MARK: Actions
    var backAction: SimpleClosure = {}
    var didTapVetInstagramAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension PetParentsViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
}

// MARK: - View State
extension PetParentsViewController {
    
    func changed(state: DefaultViewState) {
        
        switch state {
        case .started:
            viewModel.getInformerDataList()
        case .requestSucceeded:
            setupInformersData()
        case .requestFailed:
            showSimpleAlert(message: viewModel.requestError)
        default:
            break
        }
    }
}

// MARK: - Setup
extension PetParentsViewController {
    
    private func setupLayout() {
        setupScrollableInformerBaseView()
        setupImages()
    }
    
    func setupScrollableInformerBaseView() {
        let titleLabelsData = TitleLabelsViewData(topTitle: "petParents.title.label".localized,
                                                  topTitleStyle: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway),
                                                  topHighlightedTitle: "petParents.highlightedTitle.label".localized,
                                                  topHighlightedStyle: TextStyle(color: .primaryDarkest, weight: .bold, size: .h32, font: .raleway))
        scrollableInformerBaseView.setupTitleLabelsData(titleLabelsData, isVetCard: true)
        scrollableInformerBaseView.update(backAction: backAction)
    }
    
    private func setupInformersData() {
        scrollableInformerBaseView.setupData(viewModel.informerDataList)
    }
    
    private func setupImages() {
        guard let image = UIImage(named: "petParents") else { return }
        scrollableInformerBaseView.update(image: image)
    }
}
