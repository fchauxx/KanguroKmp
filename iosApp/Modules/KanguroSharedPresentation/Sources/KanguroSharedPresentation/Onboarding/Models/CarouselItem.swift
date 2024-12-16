import SwiftUI

public struct CarouselItem: Hashable {
    
    // MARK: - Stored Properties
    let imageName: String
    var boldedText: String?
    var regularText: String?
    var buttonTitle: String?
    var buttonImageName: String?
    
    // MARK: - Initializers
    public init(imageName: String,
                boldedText: String? = nil,
                regularText: String? = nil,
                buttonTitle: String? = nil,
                buttonImageName: String? = nil) {
        self.imageName = imageName
        self.boldedText = boldedText
        self.regularText = regularText
        self.buttonTitle = buttonTitle
        self.buttonImageName = buttonImageName
    }
    
    // MARK: - Computed Properties
    var image: Image {
        Image(imageName)
    }
    var buttonImage: Image? {
        guard let buttonImageName else { return nil }
        return Image(buttonImageName)
    }
}
