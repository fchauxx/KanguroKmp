import UIKit

public enum AcceptedImageFormats: String, CaseIterable {
    case jpg
    case jpeg
    case png
    case bmp

    public static func contains(_ imageFormat: String) -> Bool {
        return Self.allCases.contains { $0.rawValue == imageFormat }
    }
}

extension UIImage {

    public func isImageFormatSupported(imagePath: NSURL) -> Bool {
        guard let imageFormat = imagePath.absoluteString?.components(separatedBy:".").last else {
            return false
        }
        return AcceptedImageFormats.contains(imageFormat)
    }
}
