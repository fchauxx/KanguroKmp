import UIKit
import Combine

class CheckboxViewModel {
    
    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    var isSelected: Bool?
    var isEditable: Bool = true
}

// MARK: - Public Methods
extension CheckboxViewModel {
    
    func update(isSelected: Bool) {
        self.isSelected = isSelected
        state = .dataChanged
    }
}
