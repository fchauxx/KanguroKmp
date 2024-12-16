import UIKit
import WebKit

class PaymentMethodViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: PaymentMethodViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet private var webView: WKWebView!
    @IBOutlet private var loaderView: UIActivityIndicatorView!
    
    // MARK: Actions
    var backAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension PaymentMethodViewController {
    
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
extension PaymentMethodViewController {
    
    private func changed(state: DefaultViewState) {
        
        loaderView.isHidden = true
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            viewModel.getKeycloak()
        case .loading:
            loaderView.isHidden = false
        case .requestFailed:
            showActionAlert(message: viewModel.requestError,
                            action: backAction)
        case .requestSucceeded:
            loadURL()
        default:
            break
        }
    }
}

// MARK: - Setup
extension PaymentMethodViewController {
    
    private func setupLayout() {
        setupLoaderView()
    }
    
    private func setupLoaderView() {
        loaderView.setScaleSize(1.5)
    }
}

// MARK: - Private Methods
extension PaymentMethodViewController {
    
    private func loadURL() {
        guard let html = viewModel.htmlString else { return }
        webView.loadHTMLString(html, baseURL: nil)
    }
}

// MARK: - Setup
extension PaymentMethodViewController {
    
    @IBAction private func closeButton(_ sender: UIButton) {
        backAction()
    }
}
