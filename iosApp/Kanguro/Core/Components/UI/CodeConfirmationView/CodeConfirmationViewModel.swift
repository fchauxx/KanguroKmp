import Foundation
import Combine

enum CodeConfirmationViewState {
    
    case none
    case loading
    case succeeded
    case failed
    case requestFailed
}

class CodeConfirmationViewModel {
    
    // MARK: - Published Properties
    @Published var state: CodeConfirmationViewState = .none
}

// MARK: - Public Methods
extension CodeConfirmationViewModel {
    
    func updateState(_ state: CodeConfirmationViewState) {
        self.state = state
    }
}
