import SwiftUI

public struct ItemCategoryCardView<ItemCategory: Equatable>: View {

    // MARK: - Stored Properties
    public typealias ScheduleItemCategoryClosure = (ItemCategory) -> Void
    let image: Image
    let name: String
    let itemCategory: ItemCategory

    // MARK: - Actions
    var didTapCategory: ScheduleItemCategoryClosure

    public init(image: Image,
                name: String,
                itemCategory: ItemCategory,
                didTapCategory: @escaping ScheduleItemCategoryClosure
    ) {
        self.image = image
        self.name = name
        self.itemCategory = itemCategory
        self.didTapCategory = didTapCategory
    }

    public var body: some View {
        Button {
            didTapCategory(itemCategory)
        } label: {
            HStack(spacing: InlineSpacing.nano) {
                image
                    .resizable()
                    .frame(width: IconSize.md_2, height: IconSize.md_2)
                Text(name)
                    .captionSecondaryDarkestRegular()
                Spacer()
                Image.addCircleIcon
                    .resizable()
                    .frame(width: IconSize.sm, height: IconSize.sm)
            }
        }
        .frame(height: ActionButtonSize.xl)
        .padding(.horizontal, InsetSpacing.xxs)
        .padding(.vertical, InsetSpacing.xxxs)
        .overlay(
            RoundedRectangle(cornerRadius: CornerRadius.sm)
                .stroke(Color.secondaryLightest, lineWidth: 1)
        )
    }
}

struct ItemCategoryCardView_Previews: PreviewProvider {
    static var previews: some View {
        ItemCategoryCardView<String>(image: Image(systemName: "clock"), name: "clock", itemCategory: "") { _ in
            print("tapped")
        }
    }
}
