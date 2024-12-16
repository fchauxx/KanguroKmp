import SwiftUI

public enum ImageIcons: Equatable {

    case camera
    case gallery
    case files

    var image: Image {
        switch self {

        case .camera:
            return Image.cameraIcon
        case .gallery:
            return Image.galleryIcon
        case .files:
            return Image.fileIcon
        }
    }
}
