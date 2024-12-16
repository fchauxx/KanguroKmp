import UIKit

protocol ChatbotiewModelProtocol {
    
    // MARK: - Published Properties
    var statePublisher: Published<DefaultViewState>.Publisher { get }
    
    // MARK: - Stored Properties
    var sessionType: SessionType { get }
    var currentPetId: Int? { get }
    var sessionId: String? { get }
    var defaultStep: ChatInteractionStep? { get }
}
