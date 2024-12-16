import UIKit

// MARK: - Register
extension UICollectionView {
    
    func register(_ `class`: AnyClass) {
        let className = String(describing: `class`.self)
        register(UINib(nibName: className, bundle: Bundle.main),
                 forCellWithReuseIdentifier: className)
    }
    
    func register(_ `class`: AnyClass, kind: String) {
        let className = String(describing: `class`.self)
        register(UINib(nibName: className, bundle: Bundle.main),
                 forSupplementaryViewOfKind: kind,
                 withReuseIdentifier: className)
    }
    
    func register(identifiers: [String]) {
        for identifier in identifiers {
            register(UINib(nibName: identifier, bundle: Bundle.main),
                     forCellWithReuseIdentifier: identifier)
        }
    }
}

// MARK: - Layout
extension UICollectionView {
    
    func scrollToLeft(_ animated: Bool = true) {
        let indexPath = IndexPath(row: 0, section: 0)
        self.scrollToItem(at: indexPath, at: .left, animated: true)
    }
}
