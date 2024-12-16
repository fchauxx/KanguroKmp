import Foundation

struct SelectionViewData {
    
    // MARK: - Stored Properties
    let title: String?
    var isSelected: Bool = false
    var didTapButtonAction: IntClosure?
}
