import UIKit

protocol DraggingViewProtocol {
    
    var topLimit: CGFloat { get set }
    var bottomLimit: CGFloat { get set }
    
    var draggingView: MapLocationsDraggingView { get set }
    
    var panGesture: UIPanGestureRecognizer { get set }
    
    func draggedView(_ sender:UIPanGestureRecognizer)
}
