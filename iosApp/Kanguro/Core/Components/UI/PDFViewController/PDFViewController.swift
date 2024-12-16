import UIKit
import PDFKit

class PDFViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: PDFViewModelProtocol!
    
    // MARK: - IB Outlets
    @IBOutlet private var pdfView: PDFView!
    @IBOutlet private var loaderView: UIActivityIndicatorView!
    
    // MARK: - Actions
    var backAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension PDFViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        observe(viewModel.statePublisher) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
}

// MARK: - View State
extension PDFViewController {
    
    private func changed(state: DefaultViewState) {
        
        pdfView.isHidden = true
        setLoadingViewHidden(true)
        
        switch state {
        case .loading:
            setLoadingViewHidden(false)
        case .requestFailed:
            showActionAlert(message: viewModel.requestError, action: backAction)
        case .requestSucceeded:
            setupPDF()
            pdfView.isHidden = false
        default:
            break
        }
    }
}

// MARK: - PDF Setup
extension PDFViewController {
    
    private func setLoadingViewHidden(_ isHidden: Bool) {
        loaderView.setScaleSize(1.5)
        loaderView.isHidden = isHidden
    }
    
    private func setupPDF() {
        guard let data = viewModel.data else { return }
        if let document = PDFDocument(data: data) {
            pdfView.document = document
            pdfView.autoScales = true
        }
    }
}
