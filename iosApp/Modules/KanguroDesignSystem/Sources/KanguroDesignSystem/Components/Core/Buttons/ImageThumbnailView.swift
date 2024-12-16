import SwiftUI
import Kingfisher

public struct ImageThumbnailView: View, Identifiable {

    // MARK: - Property Wrappers
    var isSelected: Bool

    // MARK: - Stored Properties
    public let id: Int?
    var image: KFImage?

    // MARK: - Actions
    var deleteAction: SimpleClosure
    var didSelectAction: IntClosure

    public init(isSelected: Bool,
                id: Int?,
                image: KFImage,
                deleteAction: @escaping SimpleClosure,
                didSelectAction: @escaping IntClosure) {
        self.isSelected = isSelected
        self.id = id
        self.image = image
        self.deleteAction = deleteAction
        self.didSelectAction = didSelectAction
    }

    public var body: some View {
        Button {
            if isSelected {
                deleteAction()
            } else {
                guard let id else { return }
                didSelectAction(id)
            }
        } label: {
            ZStack {
                if let image {
                    image
                        .placeholder({
                            setImagePlaceholder()
                        })
                        .downsampling(size: .init(width: 600, height: 600))
                        .resizable()
                        .frame(width: IconSize.xl, height: IconSize.xl)
                        .scaledToFit()
                } else {
                    Image.imageThumbDefault
                        .resizable()
                        .frame(width: IconSize.xl, height: IconSize.xl)
                        .scaledToFit()
                }
                if isSelected {
                    Color.black
                        .frame(width: IconSize.xl, height: IconSize.xl)
                        .opacity(OpacityLevel.medium)
                    Image.deleteIcon
                        .resizable()
                        .frame(width: IconSize.sm, height: IconSize.sm)
                        .foregroundColor(.white)
                }
            }
            .cornerRadius(CornerRadius.sm)
        }
    }

    @ViewBuilder
    private func setImagePlaceholder() -> some View {
        VStack {
            ProgressView()
        }
        .frame(width: IconSize.xl, height: IconSize.xl)
        .overlay(
            RoundedRectangle(cornerRadius: CornerRadius.sm)
                .stroke(Color.neutralMedium, lineWidth: 1)
        )
    }
}

struct ImageThumbnailView_Previews: PreviewProvider {
    static var previews: some View {
        ImageThumbnailView(isSelected: false,
                           id: 0,
                           image: KFImage(nil)
                                    .placeholder {
                                        Image(systemName: "globe")
                                            .resizable()
                                            .aspectRatio(contentMode: .fit)
                                            .frame(width: 50, height: 50)
                                    },
                           deleteAction: {}, didSelectAction: { id in })
    }
}
