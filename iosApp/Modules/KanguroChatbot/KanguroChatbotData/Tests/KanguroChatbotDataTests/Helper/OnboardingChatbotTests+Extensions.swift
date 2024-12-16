import Foundation
import KanguroChatbotDomain
import KanguroChatbotData

extension OnboardingChatbotTests {

    func makeSUT() -> (ChatbotMockedNetwork, RemoteChatbotRepository) {
        let network = ChatbotMockedNetwork()
        let repo = RemoteChatbotRepository(network: network)
        return (network, repo)
    }
}
