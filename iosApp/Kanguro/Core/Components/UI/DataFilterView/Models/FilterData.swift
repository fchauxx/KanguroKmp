import Foundation

struct FilterData {
    
    // MARK: - Stored Properties
    let id: Int
    var title: String
    
    // MARK: - Computed Properties
    var isCustomOption: Bool {
        id == -1
    }
}
