import Foundation

public protocol ChatbotLifeCycleDelegate: AnyObject {
    
    func didFinished()
    func didAskExternalFlow(_ flow: ChatbotExternalFlow)
}
