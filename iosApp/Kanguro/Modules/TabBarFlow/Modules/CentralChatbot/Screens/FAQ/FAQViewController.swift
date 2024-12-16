import UIKit

class FAQViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: InformerViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet var scrollableInformerBaseView: ScrollableInformerBaseView!
    
    // MARK: Actions
    var backAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension FAQViewController {
    
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
extension FAQViewController {
    
    func changed(state: DefaultViewState) {
        
        switch state {
        case .started:
            viewModel.getInformerDataList()
        case .requestSucceeded:
            setupInformersData()
        case .requestFailed:
            showActionAlert(message: viewModel.requestError,
                            action: backAction)
        default:
            break
        }
    }
}

// MARK: - Setup
extension FAQViewController {
    
    private func setupLayout() {
        setupScrollableInformerBaseView()
        setupImages()
    }
    
    func setupScrollableInformerBaseView() {
        let titleLabelsData = TitleLabelsViewData(topTitle: "faq.title.label".localized,
                                                  topTitleStyle: TextStyle(color: .primaryDarkest, weight: .bold, size: .h32, font: .raleway),
                                                  subtitle: "faq.subtitle.label".localized)
        scrollableInformerBaseView.setupTitleLabelsData(titleLabelsData, isVetCard: false)
        scrollableInformerBaseView.update(backAction: backAction)
    }
    
    private func setupInformersData() {
        scrollableInformerBaseView.setupData(viewModel.informerDataList)
    }
    
    private func setupImages() {
        guard let faqImage = viewModel.typeImage else { return }
        scrollableInformerBaseView.update(image: faqImage)
    }
}
