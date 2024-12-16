import UIKit
import CoreGraphics
import Accelerate

enum JPEGQuality: CGFloat {
    
    case low = 0.25
    case medium = 0.5
    case high = 0.75
    case highest = 1
}

// MARK: - Public Methods
extension UIImage {
    
    func getQualifiedImage(_ jpegQuality: JPEGQuality) -> UIImage? {
        guard let data = jpegData(compressionQuality: jpegQuality.rawValue) else { return nil }
        return UIImage(data: data)
    }
    
    func scalePreservingAspectRatio(targetSize: CGSize) -> UIImage {
        
        let widthRatio = targetSize.width / size.width
        let heightRatio = targetSize.height / size.height
        let scaleFactor = min(widthRatio, heightRatio)
        
        let scaledImageSize = CGSize(
            width: size.width * scaleFactor,
            height: size.height * scaleFactor
        )
        
        let renderer = UIGraphicsImageRenderer(size: scaledImageSize)
        
        let scaledImage = renderer.image { [weak self] _ in
            guard let self = self else { return }
            self.draw(in: CGRect(
                origin: .zero,
                size: scaledImageSize
            ))
        }
        return scaledImage
    }
}
