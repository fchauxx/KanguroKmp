import XCTest
import KanguroChatbotData
import KanguroChatbotDomain

final class SimpleMessageQueueTests: XCTestCase {
    func test3MessagesNoIsTyping() throws {
        let sut = makeSUT()
        let messagePublisher = sut.$message.collectNext(3)
        sut.addMessages(["test1", "test2", "test3"])
        let messages = try awaitPublisher(messagePublisher)
        XCTAssertEqual(messages, ["test1", "test2", "test3"])
    }

    func test3MessagesWithTyping() throws {
        let sut = makeSUT(shouldShowIsTyping: true)
        let messagePublisher = sut.$message.collectNext(6)
        sut.addMessages(["test1", "test2", "test3"])
        let messages = try awaitPublisher(messagePublisher)
        XCTAssertEqual(messages, ["...WRITING...", "test1", "...WRITING...", "test2", "...WRITING...", "test3"])
    }

    // MARK: - Helper methods

    func makeSUT(shouldShowIsTyping: Bool = false) -> SimpleMessageQueue {
        let queue = SimpleMessageQueue(timerInterval: 0.5, shouldAddIsTyping: shouldShowIsTyping)
        return queue
    }
}
