import SwiftUI
import KanguroRentersDomain
import KanguroDesignSystem
import Kingfisher

public struct ImageThumbnailListView: View {

    // MARK: - Stored Properties
    var items: [ScheduledItemImage]
    @State var selectedId: Int?

    // MARK: - Actions
    var didDeletedImage: IntClosure

    public init(items: [ScheduledItemImage], didDeletedImage: @escaping IntClosure) {
        self.items = items
        self.didDeletedImage = didDeletedImage
    }

    public var body: some View {
        HStack {
            if items.count == 0 {
                Image.imageThumbDefault
                    .resizable()
                    .frame(width: IconSize.xl, height: IconSize.xl)
            } else {
                ForEach(items) { image in
                    if let imageUrl = image.url {
                        ImageThumbnailView(isSelected: selectedId == image.id,
                                           id: image.id,
                                           image: KFImage(URL(string: imageUrl)),
                                           deleteAction: {
                            if let id = image.id {
                                didDeletedImage(id)
                            }
                        }, didSelectAction: { id in
                            selectedId = id
                        })
                    }
                }
            }
        }
    }
}

struct ImageThumbnailListView_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            ImageThumbnailListView(items: [
                    ScheduledItemImage(id: 99, fileName: "", type: .Item),
                    ScheduledItemImage(id: 99, fileName: "", type: .Item),
                    ScheduledItemImage(id: 99, fileName: "", type: .Item)
            ], didDeletedImage: { _ in })
        }
    }
}
