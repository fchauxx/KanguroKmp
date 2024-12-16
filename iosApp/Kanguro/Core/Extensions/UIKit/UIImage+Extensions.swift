import UIKit

extension UIImage {

    public func tinted(withColor: UIColor?, biggerSize: CGSize = .zero) -> UIImage {
        guard let withColor = withColor else { return self }
        
        let size = biggerSize == .zero ? self.size : biggerSize
        let img2 = UIImage.createWithColor(size, color: withColor)
        let rect = CGRect(x: 0, y: 0, width: size.width, height: size.height)
        let renderer = UIGraphicsImageRenderer(size: size)
        let result = renderer.image { _ in
            img2.draw(in: rect, blendMode: .normal, alpha: 1)
            draw(in: rect, blendMode: .destinationIn, alpha: 1)
        }
        return result
    }

    public static func createWithColor(_ size: CGSize, color: UIColor) -> UIImage {
        UIGraphicsBeginImageContextWithOptions(size, false, 0.0)
        let context = UIGraphicsGetCurrentContext()
        let rect = CGRect(origin: .zero, size: size)
        color.setFill()
        context!.fill(rect)
        let image = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return image!
    }
}
