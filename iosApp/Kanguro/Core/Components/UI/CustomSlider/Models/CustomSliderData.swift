import Foundation

struct CustomSliderData {
    
    // MARK: - Stored Properties
    var wordsList: [String]?
    
    // MARK: - Computed Properties
    var numberOfSegments: Int {
        return wordsList?.count ?? 0
    }
}
