import Foundation

public protocol PopUpContentProtocol: AnyObject {
    
    var popupViewController: PopUpViewController? { get set }
    var allowsTapToDismissPopupCard: Bool { get }
    var allowsSwipeToDismissPopupCard: Bool { get }
}
