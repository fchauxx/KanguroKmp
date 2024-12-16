import Combine
import UIKit

open class BaseCollectionViewCell: UICollectionViewCell {
    
    // MARK: - Properties
    private var cancellables: [Cancellable] = []
    
    // MARK: - Life Cycle
    deinit {
        removeObservers()
    }
    
    // MARK: - Observation
    public func observe<Type>(_ publisher: Published<Type>.Publisher, action: @escaping (Type) -> Void) {
        let cancellable = publisher
            .receive(on: DispatchQueue.main)
            .sink { value in
                action(value)
            }
        cancellables.append(cancellable)
    }
    
    public func removeObservers() {
        for cancellable in cancellables {
            cancellable.cancel()
        }
        cancellables.removeAll()
    }
}
