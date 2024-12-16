import SwiftUI

public struct UploadPictureSectionView<Content: View>: View {

    var content: Content
    var uploadSectionTitle: String
    var didTapAddAction: SimpleClosure
    @Environment(\.appLanguageValue) var language

    var lang: String {
        language.rawValue
    }

    public init(content: Content,
                uploadSectionTitle: String,
                didTapAddAction: @escaping SimpleClosure) {
        self.content = content
        self.uploadSectionTitle = uploadSectionTitle
        self.didTapAddAction = didTapAddAction
    }


    public var body: some View {
        VStack(alignment: .leading) {
            HStack {
                Text(uploadSectionTitle)
                    .bodySecondaryDarkestBold()
                    .minimumScaleFactor(0.3)
                Spacer()
                AddButtonView("common.add.label".localized(lang)) {
                    didTapAddAction()
                }
            }
            ScrollView(.horizontal, showsIndicators: false) {
                content
            }
        }
    }
}

struct UploadPictureSectionView_Previews: PreviewProvider {
    static var previews: some View {
        UploadPictureSectionView(
            content: EmptyView(),
            uploadSectionTitle: "scheduled.picturePlacedOnReceipt.label".localized("en"),
            didTapAddAction: {}
        )
    }
}
