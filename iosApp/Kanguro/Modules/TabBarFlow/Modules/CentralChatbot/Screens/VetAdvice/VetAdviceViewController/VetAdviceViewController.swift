import UIKit

class VetAdviceViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: InformerViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet var scrollableInformerBaseView: ScrollableInformerBaseView!
    
    // MARK: Stored Properties
    var type: VetAdviceType?

    // MARK: Actions
    var backAction: SimpleClosure = {}
    var didTapVetInstagramAction: SimpleClosure = {}
    
    // MARK: Initializers
    init(type: VetAdviceType) {
        self.type = type
        super.init(nibName: nil, bundle: nil)
    }
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: - Life Cycle
extension VetAdviceViewController {
    
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
extension VetAdviceViewController {
    
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
extension VetAdviceViewController {
    
    private func setupLayout() {
        setupScrollableInformerBaseView()
        setupImages()
    }
    
    func setupScrollableInformerBaseView() {
        guard let type = type else { return }
        let titleLabelsData = TitleLabelsViewData(topTitle: "vetAdvice.title.label".localized + type.rawValue,
                                                  topTitleStyle: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway),
                                                  topHighlightedTitle: type.rawValue,
                                                  topHighlightedStyle: TextStyle(color: .primaryDarkest, weight: .bold, size: .h32, font: .raleway))
        scrollableInformerBaseView.setupTitleLabelsData(titleLabelsData, isVetCard: true)
        scrollableInformerBaseView.update(backAction: backAction)
    }
    
    func setupImages() {
        guard let dogImage = UIImage(named: "vetAdvice-dog"),
              let catImage = UIImage(named: "vetAdvice-cat"),
              let type = type else { return }
        
        var image: UIImage
        switch type {
        case .dog:
            image = dogImage
        case .cat:
            image = catImage
        }
        scrollableInformerBaseView.update(image: image)
    }
    
    private func setupInformersData() {
        guard let type else { return }
        switch type {
        case .cat:
            scrollableInformerBaseView.setupData(viewModel.vetAdviceCatList)
        case .dog:
            scrollableInformerBaseView.setupData(viewModel.vetAdviceDogList)
        }
    }
}
