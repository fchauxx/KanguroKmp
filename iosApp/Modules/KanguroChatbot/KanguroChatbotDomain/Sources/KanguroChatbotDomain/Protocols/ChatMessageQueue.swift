import Combine

public protocol ChatMessageQueue {
    var message: String { get }
    var messagePublished: Published<String> { get }
    var messagePublisher: Published<String>.Publisher { get }
    var isEmpty: Bool { get }
    func addMessages(_ messages: [String])
}
