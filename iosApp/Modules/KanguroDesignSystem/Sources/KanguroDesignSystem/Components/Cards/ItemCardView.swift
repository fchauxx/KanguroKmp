import SwiftUI

public struct ItemCardView: View {

    // MARK: - Stored Properties
    let id: String
    let title: String
    let category: String
    let subtitle: String
    let isConfirmed: Bool

    // MARK: - Actions
    var didTapEditButtonAction: SimpleClosure
    var didTapDeleteButtonAction: StringClosure

    public init(id: String,
                title: String,
                category: String,
                subtitle: String,
                isConfirmed: Bool,
                didTapEditButtonAction: @escaping SimpleClosure,
                didTapDeleteButtonAction: @escaping StringClosure) {
        self.id = id
        self.title = title
        self.category = category
        self.subtitle = subtitle
        self.isConfirmed = isConfirmed
        self.didTapEditButtonAction = didTapEditButtonAction
        self.didTapDeleteButtonAction = didTapDeleteButtonAction
    }

    public var body: some View {
        ZStack {
            VStack {
                HStack {
                    if isConfirmed {
                        Image.checkIcon
                            .resizable()
                            .frame(width: IconSize.sm, height: IconSize.sm)
                    }
                    VStack(alignment: .leading) {
                        Text(title)
                            .bodySecondaryDarkestBold()
                        Text(category)
                            .captionSecondaryDarkRegularBig()
                        Text(subtitle)
                            .captionSecondaryDarkRegularBig()
                    }
                    Spacer()
//                    Button {
//                        didTapEditButtonAction()
//                    } label: {
//                        if isConfirmed {
//                            Image.editIcon
//                                .resizable()
//                                .frame(width: IconSize.xs, height: IconSize.xs)
//                        } else {
//                            Image.uploadIcon
//                                .resizable()
//                                .frame(width: IconSize.xs, height: IconSize.xs)
//                        }
//                    }
                
                }
                .padding(InsetSpacing.xxs)
                .frame(maxWidth: .infinity, minHeight: ActionButtonSize.lg_2)
                .background(Color.white)
                .cornerRadius(CornerRadius.sm)
                .overlay(
                    RoundedRectangle(cornerRadius: CornerRadius.sm)
                        .stroke(isConfirmed ? Color.positiveDarkest : Color.secondaryLightest, lineWidth: 1)
                )
            }
            .padding(.leading, InsetSpacing.quarck)
        }
        .frame(maxWidth: .infinity, minHeight: ActionButtonSize.lg_2)
        .background(isConfirmed ? Color.positiveDarkest : .clear)
        .cornerRadius(CornerRadius.sm)
    }
}

struct ItemCardView_Previews: PreviewProvider {

    static var previews: some View {
        ItemCardView(id: "1",
                     title: "Macbook",
                     category: "Electronics",
                     subtitle: "$ 4000",
                     isConfirmed: false,
                     didTapEditButtonAction: { },
                     didTapDeleteButtonAction: { id in })
    }
}
