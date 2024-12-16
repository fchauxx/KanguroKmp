import Foundation
import Resolver

class ChatbotModuleDependencies {
    
    // MARK: - Stored Properties
    var chatbotService: ChatbotModuleProtocol?
    
    // MARK: - Initializers
    init(chatbotService: ChatbotModuleProtocol? = nil) {
        self.chatbotService = chatbotService
    }
}

// MARK: - ModuleDependencies
extension ChatbotModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        let chatbotService = self.chatbotService ?? ChatbotModule()
        Resolver.register { chatbotService }
    }
}
