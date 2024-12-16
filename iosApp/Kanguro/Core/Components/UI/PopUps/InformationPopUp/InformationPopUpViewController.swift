import UIKit

enum InformationPopUpViewControllerStyle {
    case regular
    case detail
}

struct InformationPopUpData {
    
    var title: String
    var description: String
    var style: InformationPopUpViewControllerStyle = .regular
    var highlighted: (text: String, style: TextStyle)?
}

class InformationPopUpViewController: UIViewController {
    
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var descritionLabel: CustomLabel!
    @IBOutlet weak var detailView: UIView!
    
    // MARK: - Stored Properties
    var popupViewController: PopUpViewController?
    var allowsTapToDismissPopupCard: Bool = true
    var allowsSwipeToDismissPopupCard: Bool = true
    var data: InformationPopUpData?
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
}

// MARK: - Setup
extension InformationPopUpViewController {
    
    func setup(data: InformationPopUpData) {
        titleLabel.set(text: data.title,
                       style: TextStyle(color: .secondaryDarkest,
                                        weight: .bold,
                                        size: .p21, font: .raleway))
        
        descritionLabel.setHighlightedText(text: data.description,
                                           style: TextStyle(color: .secondaryDarkest,
                                                            size: .p16),
                                           highlightedText: data.highlighted?.text ?? "",
                                           highlightedStyle: data.highlighted?.style)
        
        detailView.isHidden = data.style != .detail
    }
}

// MARK: - PopUpContentProtocol
extension InformationPopUpViewController: PopUpContentProtocol {
    
    static func create() -> UIViewController {
        guard let viewController = InformationPopUpViewController.instantiate() else { return UIViewController() }
        return viewController
    }
}

// MARK: - PopUpContentProtocol
extension InformationPopUpViewController {
    
    @IBAction private func closeTouchUpInside(_ sender: UIButton) {
        self.dismiss(animated: true)
    }
}
