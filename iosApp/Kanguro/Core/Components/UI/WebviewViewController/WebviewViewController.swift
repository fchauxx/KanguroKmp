import UIKit
import WebKit

class WebviewViewController: UIViewController {
    
    // MARK: - IB Outlets
    @IBOutlet private var webView: WKWebView!
    
    // MARK: - Stored Properties
    let url: URL
    
    // MARK: - Initializers
    init(url: URL) {
        self.url = url
        super.init(nibName: nil, bundle: nil)
    }
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: - Life Cycle
extension WebviewViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        loadURL()
    }
}

// MARK: - Private Methods
extension WebviewViewController {
    
    private func loadURL() {
        let request = URLRequest.init(url: url)
        webView.load(request)
    }
}
