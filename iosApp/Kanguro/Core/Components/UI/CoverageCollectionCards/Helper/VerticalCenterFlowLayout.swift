import UIKit

class VerticalCenterFlowLayout: UICollectionViewFlowLayout {

    override func layoutAttributesForElements(in rect: CGRect) -> [UICollectionViewLayoutAttributes]? {
        let attributes = super.layoutAttributesForElements(in: rect)

        return attributes?.map { attribute in
            if attribute.representedElementKind == nil {
                let indexPath = attribute.indexPath
                if let newAttribute = self.layoutAttributesForItem(at: indexPath) {
                    return newAttribute
                }
            }
            return attribute
        }
    }

    override func layoutAttributesForItem(at indexPath: IndexPath) -> UICollectionViewLayoutAttributes? {
        guard let currentItemAttributes = super.layoutAttributesForItem(at: indexPath)?.copy() as? UICollectionViewLayoutAttributes else { return nil }

        let collectionViewHeight = collectionView?.frame.size.height ?? 0
        let cellHeight: CGFloat = currentItemAttributes.frame.height
        currentItemAttributes.frame.origin.y = (collectionViewHeight - cellHeight) / 2.0

        return currentItemAttributes
    }
}
