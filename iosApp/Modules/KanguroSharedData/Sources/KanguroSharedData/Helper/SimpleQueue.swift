import Foundation

public class SimpleQueue<T> {

    // MARK: - Stored Properties
    private var queue: [T] = []
    public var currentIndex: Int = 0

    // MARK: - Computed Properties
    public var isEmpty: Bool {
        return currentIndex >= queue.count
    }

    public init(){}

    // MARK: - Public Methods
    public func push(_ value: T) {
        queue.append(value)
    }

    public func pop() -> T? {
        guard !isEmpty else { return nil }
        defer {
            currentIndex += 1
        }
        return queue[currentIndex]
    }
}
