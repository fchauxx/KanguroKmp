import Foundation

enum PopUpState: Equatable {
    case animatingIn
    case idle
    case panning
    case animatingOut
    case physicsOut(PhysicsState)
    
    static func == (lhs: PopUpState, rhs: PopUpState) -> Bool {
        
        switch (lhs, rhs) {
        case (.animatingIn, .animatingIn):
            return true
        case (.idle, .idle):
            return true
        case (.panning, .panning):
            return true
        case (.physicsOut, .physicsOut):
            return true
        default:
            return false
        }
    }
}
struct PhysicsState {
    let acceleration = CGFloat(9999)
    var velocity = CGFloat(0)
}
