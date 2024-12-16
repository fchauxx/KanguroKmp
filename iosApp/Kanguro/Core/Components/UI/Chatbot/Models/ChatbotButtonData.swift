import Foundation

struct ChatbotButtonData {
    
    let title: String
    var nextStepData: NextStepParameters? = nil
    var action: SimpleClosure? = nil
    var indexAction: IntClosure? = nil
    let isMainAction: Bool
}
