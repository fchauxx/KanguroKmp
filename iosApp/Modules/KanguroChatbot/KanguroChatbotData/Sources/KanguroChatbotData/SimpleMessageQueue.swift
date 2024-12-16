import Foundation
import Combine
import KanguroSharedData
import KanguroChatbotDomain

public class SimpleMessageQueue: ChatMessageQueue {

    // MARK: - Wrapped Properties
    @Published public var message: String = ""

    // MARK: - Stored Properties
    public var timer: Timer?
    public let shouldAddIsTyping: Bool
    public var messages: SimpleQueue<String> = SimpleQueue()
    public var messagePublished: Published<String> { _message }
    public var messagePublisher: Published<String>.Publisher { $message }

    // MARK: - Computed Properties
    public var isEmpty: Bool {
        messages.isEmpty
    }

    // MARK: - Initializer
    public init(
        timerInterval: Double = 0.8,
        shouldAddIsTyping: Bool = false
    ) {
        self.shouldAddIsTyping = shouldAddIsTyping
        setupTimer(interval: timerInterval)
    }

    deinit {
        timer?.invalidate()
    }

    private func setupTimer(interval: Double) {
        timer = Timer.scheduledTimer(
            timeInterval: interval,
            target: self,
            selector: #selector(fireTimer),
            userInfo: nil,
            repeats: true
        )
    }

    @objc func fireTimer() {
        guard let newMessage = messages.pop() else { return }
        message = newMessage
    }

    public func addMessages(_ newMessages: [String]) {
        for msg in newMessages {
            if shouldAddIsTyping {
                messages.push("...WRITING...")
            }
            messages.push(msg)
        }
    }
}
